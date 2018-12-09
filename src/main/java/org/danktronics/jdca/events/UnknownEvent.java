package org.danktronics.jdca.events;

import org.danktronics.jdca.entities.impl.JDCAImpl;
import org.json.JSONObject;

public class UnknownEvent extends Event {
    public JSONObject payload;

    public UnknownEvent(JDCAImpl jdca, JSONObject payload) {
        super(jdca);
        this.payload = payload;
    }
}
