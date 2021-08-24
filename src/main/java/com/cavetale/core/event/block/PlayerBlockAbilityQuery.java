package com.cavetale.core.event.block;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Query if a player is permitted to perform a certain action with or
 * at a certain block.
 *
 * Cancel this event to negate the given relation. Let it pass through
 * to accept it.
 */
@Getter
public final class PlayerBlockAbilityQuery extends Event implements Cancellable {
    @NonNull private final Player player;
    @NonNull private final Block block;
    @NonNull private final Action action;
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

    public PlayerBlockAbilityQuery(final Player player, final Block block, final Action action) {
        this.player = player;
        this.block = block;
        this.action = action;
        if (denyBuilding && !player.isOp()) cancelled = true;
    }

    @Getter @RequiredArgsConstructor @SuppressWarnings("RedundantModifier")
    public static enum Action {
        BUILD,
        OPEN, // Chests
        READ, // Lectern
        INVENTORY, // Lectern
        USE, // Buttons, Doors
        PLACE_ENTITY,
        SPAWN_MOB; // PocketMob (before the attempt)

        public boolean query(final Player thePlayer, final Block theBlock) {
            PlayerBlockAbilityQuery query = new PlayerBlockAbilityQuery(thePlayer, theBlock, this);
            Bukkit.getPluginManager().callEvent(query);
            return !query.isCancelled();
        }
    }
}
