package org.gbl.kernel.application;

@FunctionalInterface
public interface EventHandler<T extends Event> {
    void handle(T event);
}
