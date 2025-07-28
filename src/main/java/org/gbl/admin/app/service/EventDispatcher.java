package org.gbl.admin.app.service;

@FunctionalInterface
public interface EventDispatcher {
    void dispatch(Object event);
}
