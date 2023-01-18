package com.cavetale.core.event.friends;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

@Getter @RequiredArgsConstructor @ToString
public final class PlayerShareFriendshipGiftEvent extends Event {
    private final Player thrower;
    private final Player recipient;
    private final ItemStack itemStack;

    public List<Player> getBothPlayers() {
        return List.of(thrower, recipient);
    }

    /** Required by Event. */
    @Getter private static HandlerList handlerList = new HandlerList();

    /** Required by Event. */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
