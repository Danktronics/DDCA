package org.danktronics.jdca.entities.impl;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.danktronics.jdca.Constants;
import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.entities.EventListener;
import org.danktronics.jdca.entities.exceptions.LoginException;
import org.danktronics.jdca.events.Event;
import org.danktronics.jdca.events.PresenceUpdateEvent;
import org.danktronics.jdca.events.ReadyEvent;
import org.danktronics.jdca.rest.RequestHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class JDCAImpl implements JDCA {
    protected final OkHttpClient okHttpClient;
    protected final WebSocketFactory wsFactory;

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
    }

    public void connect() throws LoginException {
        JDCA jdca = this;
        try {
            String gatewayEndpoint = getGateway();
            WebSocket ws = this.wsFactory.createSocket(gatewayEndpoint);
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket webSocket, String message) {
                    try {
                        JSONObject payload = new JSONObject(message);
                        if (payload.getInt("op") == Constants.HELLO) {
                            broadcastEvent(new ReadyEvent(jdca));
                        } else if (payload.getInt("op") == Constants.EVENT) {
                            if (payload.getString("event").equals("PRESENCE_UPDATE")) {
                                broadcastEvent(new PresenceUpdateEvent(jdca, new UserImpl(payload.getJSONObject("user"))));
                            }
                        }
                    } catch(JSONException error) {
                        error.printStackTrace();
                    }
                }
            });
            ws.connect();
            ws.sendText(new JSONObject().put("op", Constants.IDENTIFY).put("token", this.token).toString());
        } catch(Exception error) {
            throw new LoginException(error.toString());
        }
    }

    private void broadcastEvent(Event event) {
        listeners.forEach(eventListener -> eventListener.onEvent(event));
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

    public void addEventListener(EventListener eventListener) {
        this.listeners.add(eventListener);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public String getGateway() throws Exception {
        Response response = this.requestHandler.request("https://chat.danktronics.org/api/v1/gateway/endpoint");
        if (!response.isSuccessful()) throw new Exception("Failed request");
        return new JSONObject(response.body().string()).getString("url");
    }
}
