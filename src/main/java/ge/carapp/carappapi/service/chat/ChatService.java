package ge.carapp.carappapi.service.chat;

import com.github.f4b6a3.ulid.UlidCreator;
import ge.carapp.carappapi.entity.ChatMessageEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.models.openai.AuthorRole;
import ge.carapp.carappapi.models.openai.ChatMessage;
import ge.carapp.carappapi.repository.ChatRepository;
import ge.carapp.carappapi.schema.ChatMessageSchema;
import ge.carapp.carappapi.schema.ChatMessageStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    // TODO make reactive
    private final ChatRepository chatRepository;


    private final OpenAIService openAIService;

    private static final String CHAT_PROMPT_TEMPLATE = """
        You are an AI car assistant system designed to provide technical and
         scientific assistance with car-related questions.
         Your expertise lies in car diagnostics and troubleshooting.
         You use a tone that is knowledgeable and precise.
        """;

    public Flux<String> askQuestion(UserEntity user, String question) {
        log.info("Ask question {}, {}", user.getId(), question);
        ChatMessageEntity userMessage = ChatMessageEntity.builder()
            .id(UlidCreator.getMonotonicUlid().toString())
            .statusChatMessageStatus(ChatMessageStatus.SUCCESS)
            .text(question)
            .user(user)
            .isAnswer(false)
            .build();
        ChatMessageEntity answerMessage = ChatMessageEntity.builder()
            .id(UlidCreator.getMonotonicUlid().toString())
            .statusChatMessageStatus(ChatMessageStatus.IN_PROGRESS)
            .text(question)
            .isAnswer(true)
            .user(user)
            .build();

        chatRepository.save(userMessage); // batch save ?
        final ChatMessageEntity answerMessageRef = chatRepository.save(answerMessage);


        final String answerId = answerMessage.getId();
        List<ChatMessageEntity> messages = chatRepository.findTop10ByUserIdOrderByIdDesc(user.getId());

        final StringBuilder sb = new StringBuilder();

        AtomicReference<ChatMessageStatus> statusRef = new AtomicReference<>(ChatMessageStatus.SENT);

        try {
            Stream<ChatMessage> chatMessageStream = messages.stream().map(ChatMessage::convert);
            ChatMessage chatMessage = getSystemMessage();
            List<ChatMessage> chatMessages = Stream.concat(Stream.of(chatMessage), chatMessageStream).toList();

            return openAIService.gptChat(chatMessages)
                .doOnEach(a -> sb.append(a.get()))
                .doOnEach(a -> log.info("->>>>>>>>>>> {}", a.get()))
                .doOnError(e -> statusRef.set(ChatMessageStatus.FAIL))
                .doOnComplete(() -> {
                    statusRef.set(ChatMessageStatus.SUCCESS);
                    answerMessageRef.setFinishedAt(LocalDateTime.now());
                    log.info("answer -> {}", sb);
                    answerMessageRef.setStatusChatMessageStatus(statusRef.get());
                    answerMessageRef.setText(sb.toString());
                    chatRepository.save(answerMessageRef);
                });


        } catch (Exception e) {
            e.printStackTrace();
            statusRef.set(ChatMessageStatus.FAIL);
            return Flux.just(e.getMessage());
        }
    }

    private static ChatMessage getSystemMessage() {
        return ChatMessage.builder()
            .role(AuthorRole.SYSTEM.toString())
            .content(CHAT_PROMPT_TEMPLATE)
            .build();
    }

    public List<ChatMessageSchema> listMessages(UserEntity user) {
        return chatRepository.findAllByUserId(user.getId())
            .stream().map(ChatMessageSchema::convert)
            .toList()
            ;
    }
}
