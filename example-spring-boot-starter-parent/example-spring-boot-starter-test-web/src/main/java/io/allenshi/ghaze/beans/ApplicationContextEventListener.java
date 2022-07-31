package io.allenshi.ghaze.beans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationContextEventListener {

    @Autowired
    private ApplicationAvailability applicationAvailability;

    @EventListener
    public void onContextClosed(ContextClosedEvent contextClosedEvent) {
        log.info("received context closed event -- " + contextClosedEvent.toString());

        ApplicationContext context = contextClosedEvent.getApplicationContext();

        ReadinessState readinessState = applicationAvailability.getReadinessState();
        LivenessState livenessState = applicationAvailability.getLivenessState();
        log.info("received context closed event, readinessState --" +  readinessState.name());
        log.info("received context closed event, livenessState --" +  livenessState.name());

        AvailabilityChangeEvent.publish(context, LivenessState.BROKEN);
    }

}
