package com.cavetale.core.back.event;

import com.cavetale.core.back.BackLocation;
import com.cavetale.core.command.RemotePlayer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

@Getter @RequiredArgsConstructor @ToString
/**
 * Called whenever a player is about to be sent back to this server.
 * The plugin owning the BackLocation can use this to interfere and
 * cancel.
 */
public final class PlayerBackEvent extends Event implements Cancellable {
    private final RemotePlayer player;
    private final BackLocation backLocation;
    @Setter private boolean cancelled;

    /**
     * Get the plugin owning the BackLocation.
     */
    public Plugin getOwningPlugin() {
        return Bukkit.getPluginManager().getPlugin(backLocation.getPlugin());
    }

    public Location getLocation() {
        return backLocation.getLocation();
    }

    /**
     * Required by Event.
     */
    @Getter private static HandlerList handlerList = new HandlerList();

    /**
     * Required by Event.
     */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
