package com.cavetale.core.font;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public final class UnicodeTest {
    @Test
    public void test() {
        Unicode[] array = Unicode.values();
        Arrays.sort(array, (a, b) -> {
                if (a.category != b.category) {
                    return Integer.compare(a.category.ordinal(), b.category.ordinal());
                }
                switch (a.category) {
                case ALPHABET:
                    return a.name().compareTo(b.name());
                default:
                    return Integer.compare((int) a.character, (int) b.character);
                }
            });
        Set<Character> charSet = new TreeSet<>();
        for (int i = 0; i < array.length; i += 1) {
            Unicode u = array[i];
            String show = Integer.toHexString((int) u.character).toUpperCase();
            // Duplicate detection
            if (charSet.contains(u.character)) {
                throw new IllegalStateException("Duplicate: 0x" + show);
            }
            charSet.add(u.character);
            // Print
            while (show.length() < 4) show = "0" + show;
            System.out.print(u.name() + "((char) 0x" + show + "'");
            System.out.print(u.category != Unicode.Category.DEFAULT
                             ? "Category." + u.category
                             : "");
            System.out.print(")");
            System.out.print(i < array.length - 1 ? "," : ";");
            System.out.println(" // " + u.character);
        }
    }
}
