package com.cavetale.core.event.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

@Getter @RequiredArgsConstructor
public final class PluginBlockEvent extends Event implements Cancellable {
    @NonNull private final Plugin plugin;
    @NonNull private final Block block;
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
        BREAK,
        NATURAL; // Not player placed. More of a query, but the response is so simple.

        public boolean call(final Plugin thePlugin, final Block theBlock) {
            PluginBlockEvent event = new PluginBlockEvent(thePlugin, theBlock, this);
            Bukkit.getPluginManager().callEvent(event);
            return !event.isCancelled();
        }
    }
}
