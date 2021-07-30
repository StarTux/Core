package com.cavetale.core.event.block;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Check for build permissions before doing anything.
 */
@Getter
public final class PlayerCanBuildEvent extends Event implements Cancellable {
    private final Player player;
    private final Block block;
    @Setter private boolean cancelled;
    @Setter private static boolean denyBuilding = false;

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
        this.player = player;
        this.block = block;
        if (denyBuilding && !player.isOp()) cancelled = true;
    }

    /**
     * Create and call the event.
     * @param player the player
     * @param block the block
     * @return false if the event was cancelled, true otherwise.
     */
    public static boolean call(Player player, Block block) {
        if (denyBuilding && !player.isOp()) return false;
        PlayerCanBuildEvent event = new PlayerCanBuildEvent(player, block);
        Bukkit.getPluginManager().callEvent(event);
        return !event.isCancelled();
    }
}
