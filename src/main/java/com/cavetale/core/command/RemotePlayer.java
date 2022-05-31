package com.cavetale.core.command;

import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.command.MessageCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Represents a player sending a command from a different server.
 */
public interface RemotePlayer extends MessageCommandSender {
    UUID getUniqueId();

    String getName();

    String getOriginServerName();

    void bring(Plugin plugin, Location location, Consumer<Player> callback);

    default boolean isRemote() {
        return true;
    }

    default boolean isPlayer() {
        return false;
    }

    default Player getPlayer() {
        throw new UnsupportedOperationException("getPlayer() Not implemented");
    }

    static RemotePlayer wrap(Player player) {
        return new RemotePlayerWrapper(player);
    }
}
