package com.cavetale.core.structure;

import com.cavetale.core.CorePlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

public interface Structures {
    default void register() {
        Companion.inst = this;
        CorePlugin.getInstance().getLogger().info("Structures registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.inst = Companion.DEFAULT;
    }

    static Structures get() {
        return Companion.inst;
    }

    NamespacedKey structureKeyAt(Block block);

    String structurePartNameAt(Block block);

    boolean structureAt(Block block);

    boolean structurePartAt(Block block);
}

final class Companion {
    static final Structures DEFAULT = new Structures() {
            @Override
            public NamespacedKey structureKeyAt(Block block) {
                return null;
            }

            @Override
            public String structurePartNameAt(Block block) {
                return null;
            }

            @Override
            public boolean structureAt(Block block) {
                return false;
            }

            @Override
            public boolean structurePartAt(Block block) {
                return false;
            }
        };
    static Structures inst = DEFAULT;

    private Companion() { }
}
