package com.cavetale.core.command;

import com.cavetale.core.connect.Connect;
import java.util.UUID;
import java.util.function.Consumer;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;

@Value
public final class RemotePlayerWrapper implements RemotePlayer {
    private final Player player;

    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getOriginServerName() {
        return Connect.get().getServerName();
    }

    @Override
    public void bring(Plugin plugin, Location location, Consumer<Player> callback) {
        PlayerTeleportUtil.loadNearbyChunks(location, util -> {
                player.teleport(location);
                util.cleanup();
                callback.accept(player);
            });
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean isRemote() {
        return false;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void sendMessage(String msg) {
        player.sendMessage(msg);
    }

    @Override
    public void sendMessage(Component component) {
        player.sendMessage(component);
    }

    @Override
    public Component name() {
        return player.name();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public boolean isPermissionSet(String permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public boolean isPermissionSet(Permission permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void openBook(ItemStack book) {
        player.openBook(book);
    }
}
