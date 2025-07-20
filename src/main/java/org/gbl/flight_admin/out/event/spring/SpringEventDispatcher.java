package org.gbl.flight_admin.out.event.spring;

import org.gbl.flight_admin.app.service.EventDispatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringEventDispatcher implements EventDispatcher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventDispatcher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void dispatch(Object event) {
        eventPublisher.publishEvent(event);
    }
}
