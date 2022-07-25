package com.cavetale.core.resourcepack;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.command.RemotePlayer;
import java.util.UUID;
import org.bukkit.entity.Player;

public interface ServerResourcePack {
    default void register() {
        Companion.inst = this;
        CorePlugin.getInstance().getLogger().info("ServerResourcePack registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.inst = Companion.DEFAULT;
    }

    static ServerResourcePack get() {
        return Companion.inst;
    }

    boolean has(UUID uuid);

    static boolean hasServerServerResourcePack(UUID uuid) {
        return get().has(uuid);
    }

    static boolean hasServerServerResourcePack(Player player) {
        return get().has(player.getUniqueId());
    }

    static boolean hasServerServerResourcePack(RemotePlayer player) {
        return get().has(player.getUniqueId());
    }
}

final class Companion {
    static final ServerResourcePack DEFAULT = new ServerResourcePack() {
            @Override public boolean has(UUID uuid) {
                return true;
            }
        };
    static ServerResourcePack inst = DEFAULT;

    private Companion() { }
}
