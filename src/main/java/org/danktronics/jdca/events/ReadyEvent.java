package org.danktronics.jdca.events;

import org.danktronics.jdca.JDCA;

public class ReadyEvent extends Event {
    public ReadyEvent(JDCA jdca) {
        super(jdca);
    }
}
