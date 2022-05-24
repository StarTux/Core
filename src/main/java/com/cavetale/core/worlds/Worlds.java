package com.cavetale.core.worlds;

import com.cavetale.core.CorePlugin;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

/**
 * Service for loading worlds.
 */
public interface Worlds {
    default void register() {
        Companion.worlds = this;
    }

    default void unregister() {
        Companion.worlds = Companion.DEFAULT;
    }

    static Worlds get() {
        return Companion.worlds;
    }

    default Plugin getPlugin() {
        return CorePlugin.getInstance();
    }

    default void loadWorld(String name, Consumer<World> callback) {
        callback.accept(Bukkit.getWorld(name));
    }
}

final class Companion {
    protected static final Worlds DEFAULT = new Worlds() { };
    protected static Worlds worlds = DEFAULT;

    private Companion() { }
}
