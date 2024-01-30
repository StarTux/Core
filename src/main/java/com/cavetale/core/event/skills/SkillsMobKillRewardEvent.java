package com.cavetale.core.event.skills;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Data
@EqualsAndHashCode(callSuper = true)
public final class SkillsMobKillRewardEvent extends SkillsRewardEvent {
    private final AbstractArrow arrow;
    private final Mob mob;

    public SkillsMobKillRewardEvent(final Player player, final AbstractArrow arrow, final Mob mob) {
        super(player);
        this.arrow = arrow;
        this.mob = mob;
    }

    public SkillsMobKillRewardEvent(final Player player, final Mob mob) {
        this(player, null, mob);
    }

    public SkillsMobKillRewardEvent(final Player player, final AbstractArrow arrow, final Mob mob, final int skillPoints, final double money, final int exp) {
        super(player, skillPoints, money, exp);
        this.arrow = arrow;
        this.mob = mob;
    }

    public SkillsMobKillRewardEvent(final Player player, final Mob mob, final int skillPoints, final double money, final int exp) {
        this(player, null, mob, skillPoints, money, exp);
    }

    public boolean isMelee() {
        return arrow == null;
    }

    public boolean isRanged() {
        return arrow != null;
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

