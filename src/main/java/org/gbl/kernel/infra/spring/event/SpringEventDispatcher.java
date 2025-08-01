package org.gbl.kernel.infra.spring.event;

import org.gbl.admin.app.service.EventDispatcher;
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
