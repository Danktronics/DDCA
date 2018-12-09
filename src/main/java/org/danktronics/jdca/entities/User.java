package org.danktronics.jdca.entities;

public interface User {
    String getId();

    String getUsername();

    String getAvatar();

    boolean isBot();

    boolean isAdmin();
}
