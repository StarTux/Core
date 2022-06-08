package com.cavetale.core.font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Test;

public final class FontTest {
    private Set<Integer> usedCharacters = new TreeSet<>();
    private int totalMin = Integer.MAX_VALUE;
    private int totalMax = Integer.MIN_VALUE;

    @Test
    public void test() {
        test(DefaultFont.class);
        test(VanillaEffects.class);
        test(VanillaPaintings.class);
        test(VanillaItems.class);
        System.out.println(hex(totalMin) + "..." + hex(totalMax) + "\tTotal");
    }

    public <E extends Enum<E> & Font> void test(Class<E> clazz) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        ArrayList<Integer> sortedCharacters = new ArrayList<>();
        for (E font : clazz.getEnumConstants()) {
            int value = (int) font.getCharacter();
            if (usedCharacters.contains(value)) {
                throw new IllegalStateException("Duplicate character: " + clazz.getSimpleName() + "/" + font.name());
            }
            usedCharacters.add(value);
            sortedCharacters.add(value);
            if (value < min) min = value;
            if (value > max) max = value;
        }
        Collections.sort(sortedCharacters);
        for (int i = 1; i < sortedCharacters.size(); i += 1) {
            int prev = sortedCharacters.get(i - 1);
            int value = sortedCharacters.get(i);
            if (value != prev + 1) {
                int gap = value - prev;
                System.err.println("Gap: " + clazz.getSimpleName()
                                   + ": " + hex(prev) + " => " + hex(value)
                                   + " (" + gap + ", " + hex(gap) + ")");
            }
        }
        System.out.println(hex(min) + "..." + hex(max) + "\t" + clazz.getSimpleName());
        totalMin = Math.min(min, totalMin);
        totalMax = Math.max(max, totalMax);
    }

    private static String hex(int in) {
        return Integer.toHexString(in).toUpperCase();
    }
}
