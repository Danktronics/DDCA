package org.danktronics.jdca;

import org.danktronics.jdca.entities.impl.JDCAImpl;
import org.danktronics.jdca.utils.Checks;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class JDCABuilder {
    protected final List<Object> listeners;

    public JDCABuilder() {
        this.listeners = new LinkedList<>();
    }

    public JDCABuilder addListener(Object... listeners) {
        Checks.notNull(listeners, "listeners");

        Collections.addAll(this.listeners, listeners);
        return this;
    }

    public JDCABuilder removeListener(Object... listeners) {
        Checks.noneNull(listeners, "listeners");

        this.listeners.removeAll(Arrays.asList(listeners));
        return this;
    }

    public JDCA build() {
        return new JDCAImpl();
    }
}
