package com.cavetale.core.font;

import org.bukkit.Art;
import org.junit.Test;

public final class VanillaPaintingsTest {
    @Test
    public void test() {
        int nextHex = 0xe1b4;
        for (Art art : Art.values()) {
            VanillaPaintings painting = VanillaPaintings.of(art);
            if (painting == null) {
                System.err.println("No VanillaPaintings: " + art);
                //System.err.println("    " + art + "(Art." + art + ", 0x" + Integer.toHexString(nextHex++) + ", 16),");
                continue;
            }
            if (!painting.name().equals(art.name())) {
                System.err.println("Different VanillaPaintings Name: " + art + "/" + painting);
            }
        }
    }
}
