package com.cavetale.core.selection;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.struct.Cuboid;
import org.bukkit.entity.Player;

/**
 * Provider for player WorldEdit selections.
 * The Area plugin will provide this.
 */
public interface SelectionProvider {
    default void register() {
        Companion.inst = this;
        CorePlugin.getInstance().getLogger().info("SelectionProvider registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.inst = Companion.DEFAULT;
    }

    static SelectionProvider get() {
        return Companion.inst;
    }

    Cuboid getCuboidSelection(Player player);
}

final class Companion {
    protected static final SelectionProvider DEFAULT = new SelectionProvider() {
            @Override
            public Cuboid getCuboidSelection(Player player) {
                return null;
            }
        };
    protected static SelectionProvider inst = DEFAULT;

    private Companion() { }
}
