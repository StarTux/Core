package com.cavetale.core.editor;

import com.cavetale.core.CorePlugin;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Public registry.
 */
public interface Editor {
    default void register() {
        Companion.editor = this;
        CorePlugin.getInstance().getLogger().info("Editor registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.editor = Companion.DEFAULT;
    }

    static Editor get() {
        return Companion.editor;
    }

    default void open(Plugin plugin, Player player, Object rootObject, EditMenuDelegate delegate) {
        throw new IllegalStateException("open not implemented");
    }
}

final class Companion {
    protected static final Editor DEFAULT = new Editor() { };

    private Companion() { };

    protected static Editor editor = DEFAULT;
}
