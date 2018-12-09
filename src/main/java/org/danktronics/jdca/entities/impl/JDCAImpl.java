package org.danktronics.jdca.entities.impl;

import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.entities.EventListener;
import org.danktronics.jdca.entities.exceptions.LoginException;
import org.danktronics.jdca.events.Event;
import org.danktronics.jdca.requests.RequestHandler;
import org.danktronics.jdca.requests.WebSocketClient;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JDCAImpl implements JDCA {
    protected final OkHttpClient okHttpClient;
    protected final WebSocketFactory wsFactory;
    protected final WebSocketClient wsClient;

    protected final String token;
    protected Status status = Status.DISCONNECTED;
    protected final List<EventListener> listeners;
    protected final RequestHandler requestHandler;

    public JDCAImpl(String token, WebSocketFactory wsFactory, OkHttpClient okHttpClient) {
        this.token = token;
        this.wsFactory = wsFactory;
        this.okHttpClient = okHttpClient;
        this.requestHandler = new RequestHandler(this);
        this.listeners = new LinkedList<>();
        this.wsClient = new WebSocketClient(this);
    }

    public void login() throws LoginException {
        try {
            this.wsClient.connect(getGateway());
        } catch(Exception error) {
            throw new LoginException(error);
        }
    }

    public void broadcastEvent(Event event) {
        listeners.forEach(eventListener -> eventListener.onEvent(event));
    }

    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getToken() {
        return this.token;
    }

    public void addEventListener(EventListener eventListener) {
        this.listeners.add(eventListener);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public WebSocketFactory getWSFactory() {
        return wsFactory;
    }

    public WebSocketClient getWSClient() {
        if (wsClient == null) throw new IllegalStateException("Called getWSClient before logging in");
        return wsClient;
    }

    public String getGateway() throws Exception {
        Response response = this.requestHandler.request("https://chat.danktronics.org/api/v1/gateway/endpoint");
        if (!response.isSuccessful()) throw new Exception("Failed request");
        return new JSONObject(response.body().string()).getString("url");
    }
}
