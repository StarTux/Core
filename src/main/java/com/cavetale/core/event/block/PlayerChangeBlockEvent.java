package com.cavetale.core.event.block;

import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a block is changed on behalf of a player.  This action
 * is not cancellable; use the PlayerBlockAbilityQuery instead!
 *
 * The caller must call this event before making any changes to the
 * world, so that the old state can be preserved!
 */
@Getter
public final class PlayerChangeBlockEvent extends Event {
    private final Player player;
    private final Block block;
    private BlockData newBlockData;
    private BlockState newBlockState;
    private final ItemStack itemStack;

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

    public PlayerChangeBlockEvent(final Player player, final Block block, final BlockData newBlockData, final ItemStack itemStack) {
        this.player = player;
        this.block = block;
        this.newBlockData = newBlockData;
        this.newBlockState = null;
        this.itemStack = itemStack;
    }

    public PlayerChangeBlockEvent(final Player player, final Block block, final BlockData newBlockData) {
        this(player, block, newBlockData, null);
    }

    public PlayerChangeBlockEvent(final Player player, final Block block, final BlockState newBlockState, final ItemStack itemStack) {
        this.player = player;
        this.block = block;
        this.newBlockData = null;
        this.newBlockState = newBlockState;
        this.itemStack = itemStack;
    }

    public PlayerChangeBlockEvent(final Player player, final Block block, final BlockState newBlockState) {
        this(player, block, newBlockState, null);
    }

    public BlockData getOldBlockData() {
        return block.getBlockData();
    }

    public BlockState getOldBlockState() {
        return block.getState();
    }

    public BlockData getNewBlockData() {
        return newBlockData != null
            ? newBlockData
            : newBlockState.getBlockData();
    }

    public BlockState getNewBlockState() {
        if (newBlockState != null) return newBlockState;
        BlockState result = block.getState(true);
        result.setBlockData(newBlockData);
        return result;
    }

    public boolean hasItem() {
        return itemStack != null;
    }
}
