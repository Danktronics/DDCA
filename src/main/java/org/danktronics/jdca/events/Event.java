package org.danktronics.jdca.events;

import org.danktronics.jdca.JDCA;

public abstract class Event {
    protected final JDCA jdca;

    public Event(JDCA jdca) {
        this.jdca = jdca;
    }
}
