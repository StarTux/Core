package com.cavetale.core.event.dungeon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Discovery of custom dungeons, called once per player per dungeon.
 */
@Getter @RequiredArgsConstructor @ToString
public final class DungeonDiscoverEvent extends Event {
    private final Player player;
    private final Block block;
    /** Has dungeon previously been discovered by someone else? */
    private final boolean isDiscovered;

    /** Required by Event. */
    @Getter private static HandlerList handlerList = new HandlerList();

    /** Required by Event. */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
