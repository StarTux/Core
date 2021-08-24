package com.cavetale.core.event.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

/**
 * An event for plugins to communicate specific entity events among
 * each other.
 */
@Getter @RequiredArgsConstructor
public final class PluginEntityEvent extends Event implements Cancellable {
    @NonNull private final Plugin plugin;
    @NonNull private final Entity entity;
    @NonNull private final Action action;
    @Setter private boolean cancelled;
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

    public enum Action {
        EGGIFY; // PocketMob

        public boolean call(final Plugin thePlugin, final Entity theEntity) {
            PluginEntityEvent event = new PluginEntityEvent(thePlugin, theEntity, this);
            Bukkit.getPluginManager().callEvent(event);
            return !event.isCancelled();
        }
    }
}
