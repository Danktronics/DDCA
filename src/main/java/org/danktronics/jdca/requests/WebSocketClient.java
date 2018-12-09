package org.danktronics.jdca.requests;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import org.danktronics.jdca.Constants;
import org.danktronics.jdca.JDCA;
import org.danktronics.jdca.entities.impl.JDCAImpl;
import org.danktronics.jdca.entities.impl.UserImpl;
import org.danktronics.jdca.events.PresenceUpdateEvent;
import org.danktronics.jdca.events.ReadyEvent;
import org.danktronics.jdca.events.UnknownEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class WebSocketClient {
    private JDCAImpl jdca;
    private WebSocket webSocket;

    public WebSocketClient(JDCAImpl jdca) {
        this.jdca = jdca;
    }

    public JDCA getJDCA() {
        return jdca;
    }

    public void connect(String url) {
        try {
            this.webSocket = this.jdca.getWSFactory().createSocket(url);
            this.registerListeners();
            webSocket.connect();
            webSocket.sendText(new JSONObject().put("op", Constants.IDENTIFY).put("token", jdca.getToken()).toString());
        } catch(IOException | WebSocketException error) {
            throw new IllegalStateException(error);
        }
    }

    private void registerListeners() {
        this.webSocket.addListener(new WebSocketAdapter() {
            @Override
            public void onTextMessage(WebSocket webSocket, String message) {
                try {
                    JSONObject payload = new JSONObject(message);
                    System.out.println(payload);
                    switch (payload.getInt("op")) {
                        case Constants.HELLO:
                            jdca.broadcastEvent(new ReadyEvent(jdca));
                            break;
                        case Constants.EVENT:
                            handleEvent(payload);
                            break;
                        default:
                            jdca.broadcastEvent(new UnknownEvent(jdca, payload));
                    }
                } catch(JSONException error) {
                    error.printStackTrace();
                }
            }
        });
    }

    private void handleEvent(JSONObject payload) {
        switch (payload.getString("event")) {
            case "PRESENCE_UPDATE":
                jdca.broadcastEvent(new PresenceUpdateEvent(jdca, new UserImpl(payload.getJSONObject("user"))));
                break;
            default:
                jdca.broadcastEvent(new UnknownEvent(jdca, payload));
        }
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }
}
