package com.cavetale.core.command;

import com.cavetale.core.connect.NetworkServer;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.command.MessageCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * Represents a player sending a command from a different server.
 */
public interface RemotePlayer extends MessageCommandSender {
    UUID getUniqueId();

    String getName();

    String getOriginServerName();

    void bring(Plugin plugin, Location location, Consumer<Player> callback);

    void openBook(ItemStack book);

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

    default NetworkServer getOriginServer() {
        return NetworkServer.of(getOriginServerName());
    }
}
