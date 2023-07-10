package ge.carapp.carappapi.controller;


import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ScheduledTimeSlotSchema;
import ge.carapp.carappapi.schema.graphql.ScheduleCarForServiceInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.time.Instant;


@Controller
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @QueryMapping
    public List<ScheduledTimeSlotSchema> listQueue(@Argument UUID productId) {
        return queueService.listQueueByProviderId(productId);
    }


    @MutationMapping
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public Boolean scheduleCarForService(@Argument ScheduleCarForServiceInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return queueService.scheduleCarByManager(authenticatedUser, input);
    }


    @SubscriptionMapping
    public Flux<List<String>> subscribeToQueue(@Argument String providerId) {

        // A flux is the publisher of data
        return Flux.fromStream(
            Stream.generate(() -> {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                return List.of(Instant.now().toString(),  providerId);
            }));

    }
}


