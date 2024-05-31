package com.cavetale.core.font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import org.junit.Test;

public final class VanillaItemsTest {
    @Test
    public void test() {
        // No duplicates allowed!
        char maxc = (char) 0;
        HashSet<Character> set = new HashSet<>();
        for (VanillaItems it : VanillaItems.values()) {
            if (it.character > maxc) maxc = it.character;
            if (set.contains(it.character)) {
                throw new IllegalStateException("Duplicate character: " + it + ": " + Integer.toHexString(it.character));
            }
            set.add(it.character);
        }
        System.out.println("Max VanillaItems: " + Integer.toHexString(maxc));
        // All characters must be consecutive!
        ArrayList<Character> list = new ArrayList<>(set);
        Collections.sort(list);
        char prevc = list.get(0);
        for (int i = 1; i < list.size(); i += 1) {
            char nextc = list.get(i);
            if (prevc + 1 != nextc) {
                throw new IllegalArgumentException("Not consecutive: "
                                                   + Integer.toHexString(prevc)
                                                   + ", "
                                                   + Integer.toHexString(nextc));
            }
            prevc = nextc;
        }
    }
}
