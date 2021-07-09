package com.cavetale.core.event.block;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * Check for build permissions before doing anything.
 */
@Getter
public final class PlayerCanBuildEvent extends BlockEvent implements Cancellable {
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

    public PlayerCanBuildEvent(final Player player, final Block block) {
        super(block);
        this.player = player;
    }

    /**
     * Create and call the event.
     * @param player the player
     * @param block the block
     * @return false if the event was cancelled, true otherwise.
     */
    public static boolean call(Player player, Block block) {
        PlayerCanBuildEvent event = new PlayerCanBuildEvent(player, block);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
