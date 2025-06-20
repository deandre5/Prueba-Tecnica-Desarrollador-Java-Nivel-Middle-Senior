package co.com.bancolombia.events;

import co.com.bancolombia.model.events.gateways.EventsGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.reactivecommons.async.impl.config.annotations.EnableDomainEventBus;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.logging.Level;

import static reactor.core.publisher.Mono.from;

@Log
@RequiredArgsConstructor
@EnableDomainEventBus
public class ReactiveEventsGateway implements EventsGateway {
    public static final String STATS_VALIDATED_EVENT_NAME = "event.stats.validated";
    private final DomainEventBus domainEventBus;

    @Override
    public Mono<Void> emit(Object event) {
        log.log(Level.INFO, "Sending domain event: {0}: {1}", new String[]{STATS_VALIDATED_EVENT_NAME, event.toString()});
         DomainEvent<Object> domainEvent = new DomainEvent<>(
                 STATS_VALIDATED_EVENT_NAME,
                 UUID.randomUUID().toString(),
                 event
         );
         return from(domainEventBus.emit(domainEvent));
    }
}
