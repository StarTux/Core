package com.cavetale.core.event.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * When a player absorbs an item from the environment.
 */
@Getter @RequiredArgsConstructor
public final class PlayerAbsorbItemEvent extends Event {
    @NonNull private final Player player;
    @NonNull private final ItemStack item;

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
