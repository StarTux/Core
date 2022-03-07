package com.cavetale.core.command;

import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.command.MessageCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 * Represents a player sending a command from a different server.
 */
public interface RemotePlayer extends MessageCommandSender {
    UUID getUniqueId();

    String getName();

    String getOriginServerName();

    void bring(Plugin plugin, Consumer<PlayerSpawnLocationEvent> callback);

    default boolean isRemote() {
        return true;
    }

    default boolean isPlayer() {
        return false;
    }

    default Player getPlayer() {
        throw new UnsupportedOperationException("getPlayer() Not implemented");
    }
}