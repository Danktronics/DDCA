package org.danktronics.jdca.entities;

import org.danktronics.jdca.events.Event;

public interface EventListener {
    void onEvent(Event event);
}
