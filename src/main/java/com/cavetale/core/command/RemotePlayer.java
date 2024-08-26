package com.cavetale.core.command;

import com.cavetale.core.connect.NetworkServer;
import com.cavetale.core.perm.Perm;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

/**
 * Represents a player sending a command from a different server.
 */
public interface RemotePlayer extends CommandSender {
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

    @Override
    default Spigot spigot() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    @SuppressWarnings("deprecation")
    default void sendMessage(UUID uuid, String[] messages) {
        sendMessage(messages);
    }

    @Override
    @SuppressWarnings("deprecation")
    default void sendMessage(UUID uuid, String message) {
        sendMessage(message);
    }

    @Override
    default void sendMessage(String[] messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    @Override
    default Set<PermissionAttachmentInfo> getEffectivePermissions() {
        final Set<PermissionAttachmentInfo> result = new HashSet<>();
        for (Map.Entry<String, Boolean> entry : Perm.get().getPerms(getUniqueId()).entrySet()) {
            result.add(new PermissionAttachmentInfo(this, entry.getKey(), null, entry.getValue()));
        }
        return result;
    }

    @Override
    default void recalculatePermissions() { }

    @Override
    default PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        throw new UnsupportedOperationException();
    }

    @Override
    default PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Override
    default PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        throw new UnsupportedOperationException();
    }

    @Override
    default PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void removeAttachment(PermissionAttachment attachment) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void setOp(boolean value) {
        throw new UnsupportedOperationException();
    }
}
