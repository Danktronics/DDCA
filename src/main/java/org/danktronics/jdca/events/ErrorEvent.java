package org.danktronics.jdca.events;

import org.danktronics.jdca.JDCA;

public class ErrorEvent extends Event {
    private Exception exception;

    public ErrorEvent(JDCA jdca, Exception exception) {
        super(jdca);

        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }
}
