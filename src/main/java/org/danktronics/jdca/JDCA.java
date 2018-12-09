package org.danktronics.jdca;

public interface JDCA {
    String getToken();

    Status getStatus();

    enum Status {
        CONNECTING,
        DISCONNECTED,
        CONNECTED
    }
}
