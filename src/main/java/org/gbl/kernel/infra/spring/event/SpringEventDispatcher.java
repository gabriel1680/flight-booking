package org.gbl.kernel.infra.spring.event;

import org.gbl.kernel.application.Event;
import org.gbl.kernel.application.EventDispatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

public class SpringEventDispatcher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringEventDispatcher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void dispatch(Event event) {
        eventPublisher.publishEvent(event);
    }
}
