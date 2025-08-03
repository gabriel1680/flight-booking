package org.gbl.kernel.application;

@FunctionalInterface
public interface EventDispatcher {
    void dispatch(Event event);
}
