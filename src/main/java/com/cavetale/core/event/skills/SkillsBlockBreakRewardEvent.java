package com.cavetale.core.event.skills;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Data
@EqualsAndHashCode(callSuper = true)
public final class SkillsBlockBreakRewardEvent extends SkillsRewardEvent {
    private final List<Block> blocks;

    public SkillsBlockBreakRewardEvent(final Player player, final List<Block> blocks) {
        super(player);
        this.blocks = blocks;
    }

    public SkillsBlockBreakRewardEvent(final Player player, final List<Block> blocks, final int skillPoints, final double money, final int exp) {
        super(player, skillPoints, money, exp);
        this.blocks = blocks;
    }

    public SkillsBlockBreakRewardEvent(final Player player, final Block block) {
        this(player, List.of(block));
    }

    public SkillsBlockBreakRewardEvent(final Player player, final Block block, final int skillPoints, final double money, final int exp) {
        this(player, List.of(block), skillPoints, money, exp);
    }

    public int getBlockCount() {
        return blocks.size();
    }

    /**
     * Required by Event.
     */
    @Getter
    private static HandlerList handlerList = new HandlerList();

    /**
     * Required by Event.
     */
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
