package org.danktronics.jdca.events;

import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.entities.User;

public class PresenceUpdateEvent extends Event {
    private User user;

    public PresenceUpdateEvent(JDCA jdca, User user) {
        super(jdca);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
