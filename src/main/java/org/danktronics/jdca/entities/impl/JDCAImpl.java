package org.danktronics.jdca.entities.impl;

import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.entities.EventListener;

public class JDCAImpl implements JDCA {
    protected final OkHttpClient okHttpClient;
    protected final WebSocketFactory wsFactory;

    protected final String token;
    protected Status status = Status.DISCONNECTED;

    public JDCAImpl(String token, WebSocketFactory wsFactory, OkHttpClient okHttpClient) {
        this.token = token;
        this.wsFactory = wsFactory;
        this.okHttpClient = okHttpClient;
    }

    public Status getStatus() {
        return this.status;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    public String getToken() {
        return this.token;
    }

    public void addEventListener(Object eventListener) throws Exception {
        if (eventListener instanceof EventListener) {
            
        } else throw new Exception("Provided incorrect listener");
    }
}
