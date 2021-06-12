package com.cavetale.core.event.block;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * A plugin generated event to notifiy other plugins that a block was
 * broken on behalf of a player. This is to avoid the assumption of
 * BlockBreakEvent that the player is looking at the block and
 * breaking it with their hands, which often triggers anti-cheat
 * plugins.
 */
@Getter
public final class PlayerBreakBlockEvent extends BlockEvent implements Cancellable {
    private final Player player;
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

    public PlayerBreakBlockEvent(final Player player, final Block block) {
        super(block);
        this.player = player;
    }
}
