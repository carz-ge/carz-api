package ge.carapp.carappapi.controller.ws;

import io.netty.util.internal.logging.Slf4JLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class WsUtils {
    private final static Logger log = LoggerFactory.getLogger(WsUtils.class);

    // warning: modifies webSocketSessions list
    public static void clearClosedConnections(List<WebSocketSession> webSocketSessions, int maxConnections) throws IOException {
        List<WebSocketSession> webSocketSessionListCopy = new ArrayList<>(webSocketSessions);
//        synchronized (webSocketSessions) {
        for (int i = webSocketSessionListCopy.size() - 1; i >= 0; i--) {
            if (!webSocketSessionListCopy.get(i).isOpen()) {
                WebSocketSession removed = webSocketSessions.remove(i);
                removed.close();
            }
        }

        if (webSocketSessions.size() == maxConnections) {
            WebSocketSession removed = webSocketSessions.remove(0);
            removed.close();
        }
//        }
    }


    public static boolean sendMessage(Map<UUID, List<WebSocketSession>> connections, UUID id, String message) {
        List<WebSocketSession> webSocketSessions = connections.get(id);
        if (webSocketSessions == null || webSocketSessions.isEmpty()) {
            return false;
        }

        boolean sentSuccessfully = false;

        for (WebSocketSession webSocketSession : webSocketSessions) {
            try {
                webSocketSession.sendMessage(new TextMessage(message));
                sentSuccessfully = true;
            } catch (IOException e) {
                log.error("error sending message to $s".formatted(id), e);
            }
        }
        return sentSuccessfully;
    }

}
