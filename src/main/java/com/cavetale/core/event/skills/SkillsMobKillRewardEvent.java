package com.cavetale.core.event.skills;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDeathEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public final class SkillsMobKillRewardEvent extends SkillsRewardEvent {
    private final AbstractArrow arrow;
    private final Mob mob;
    private final EntityDeathEvent entityDeathEvent;

    public SkillsMobKillRewardEvent(final Player player, final AbstractArrow arrow, final Mob mob, final int skillPoints, final double money, final int exp, final EntityDeathEvent entityDeathEvent) {
        super(player, skillPoints, money, exp);
        this.arrow = arrow;
        this.mob = mob;
        this.entityDeathEvent = entityDeathEvent;
    }

    public SkillsMobKillRewardEvent(final Player player, final Mob mob, final int skillPoints, final double money, final int exp, final EntityDeathEvent entityDeathEvent) {
        this(player, null, mob, skillPoints, money, exp, entityDeathEvent);
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

