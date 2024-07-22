package com.cavetale.core.util;

import java.util.ArrayList;
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

    public static String snakeToCamelCase(String glue, String snake) {
        String[] toks = snake.split("_");
        for (int i = 0; i < toks.length; i += 1) {
            toks[i] = toks[i].substring(0, 1).toUpperCase() + toks[i].substring(1).toLowerCase();
        }
        return String.join(glue, toks);
    }

    /**
     * Split a name in camel case into its component. Examples:
     *
     * - CamelCase => ['Camel', 'Case'];
     * - SQLArrayList => ['SQL', 'Array', 'List'];
     */
    public static List<String> splitCamelCase(String src) {
        final List<String> result = new ArrayList<>();
        int wordStart = 0;
        char c = src.charAt(0);
        int capsCount = Character.isUpperCase(c) ? 1 : 0;
        for (int i = 1; i < src.length(); ++i) {
            c = src.charAt(i);
            if (Character.isUpperCase(c)) {
                switch (capsCount) {
                case 0:
                    result.add(src.substring(wordStart, i));
                    wordStart = i;
                    break;
                default:
                    break;
                }
                capsCount += 1;
            } else {
                switch (capsCount) {
                case 0:
                case 1:
                    break;
                default:
                    result.add(src.substring(wordStart, i - 1));
                    wordStart = i - 1;
                }
                capsCount = 0;
            }
        }
        result.add(src.substring(wordStart, src.length()));
        return result;
    }

    private CamelCase() { }
}
