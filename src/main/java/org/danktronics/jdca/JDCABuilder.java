package org.danktronics.jdca;

import com.neovisionaries.ws.client.WebSocketFactory;
import okhttp3.OkHttpClient;
import org.danktronics.jdca.entities.EventListener;
import org.danktronics.jdca.entities.impl.JDCAImpl;
import org.danktronics.jdca.utils.Checks;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class JDCABuilder {
    private final String token;
    private final List<Object> listeners;

    public JDCABuilder(String token) {
        this.token = token;
        this.listeners = new LinkedList<>();
    }

    public JDCABuilder addListener(EventListener... listeners) {
        Checks.notNull(listeners, "listeners");

        Collections.addAll(this.listeners, listeners);
        return this;
    }

    public JDCABuilder removeListener(EventListener... listeners) {
        Checks.noneNull(listeners, "listeners");

        this.listeners.removeAll(Arrays.asList(listeners));
        return this;
    }

    public JDCA build() {
        JDCAImpl jdca = new JDCAImpl(token, new WebSocketFactory(), new OkHttpClient.Builder().build());
        listeners.forEach(jdca::addEventListener);
        return jdca;
    }
}
