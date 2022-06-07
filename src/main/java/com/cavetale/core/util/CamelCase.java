package com.cavetale.core.util;

import java.util.List;

public final class CamelCase {
    public static String toCamelCase(String glue, Enum e) {
        String[] toks = e.name().split("_");
        for (int i = 0; i < toks.length; i += 1) {
            toks[i] = toks[i].substring(0, 1).toUpperCase() + toks[i].substring(1).toLowerCase();
        }
        return String.join(glue, toks);
    }

    public static String toCamelCase(String glue, List<String> list) {
        String[] toks = list.toArray(new String[0]);
        for (int i = 0; i < toks.length; i += 1) {
            toks[i] = toks[i].substring(0, 1).toUpperCase() + toks[i].substring(1).toLowerCase();
        }
        return String.join(glue, toks);
    }

    private CamelCase() { }
}
