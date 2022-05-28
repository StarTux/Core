package com.cavetale.core.event.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * When a player absorbs an item from the environment.
 */
@Getter @RequiredArgsConstructor
public final class PlayerAbsorbItemEvent extends Event {
    @NonNull private final Player player;
    @NonNull private final Item item;
    private final int amount;

    public int getRemaining() {
        return item.getItemStack().getAmount() - amount;
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
