package org.danktronics.jdca.utils;

import java.util.Collection;

public class Checks {
    public static void notNull(final Object args, final String name) {
        if (args == null) throw new IllegalArgumentException(name + " may not be null");
    }

    public static void noneNull(final Collection<?> args, final String name) {
        notNull(args, name);
        args.forEach(arg -> notNull(arg, name));
    }

    public static void noneNull(final Object[] args, final String name) {
        notNull(args, name);
        for (Object object : args) {
            notNull(object, name);
        }
    }
}
