package org.gbl.flight_admin.app.service;

@FunctionalInterface
public interface EventDispatcher {
    void dispatch(Object event);
}
