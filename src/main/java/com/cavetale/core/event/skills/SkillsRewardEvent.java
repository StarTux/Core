package com.cavetale.core.event.skills;

import java.util.concurrent.ThreadLocalRandom;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public abstract class SkillsRewardEvent extends Event implements Cancellable {
    private final Player player;
    private int skillPoints;
    private double money;
    private int exp;
    private double postMultiplyFactor = 1.0;
    private boolean cancelled;

    public SkillsRewardEvent(final Player player, final int skillPoints, final double money, final int exp) {
        this(player);
        this.skillPoints = skillPoints;
        this.money = money;
        this.exp = exp;
    }

    private static int roundFair(double in) {
        final double floor = Math.floor(in);
        final double remain = in;
        return ThreadLocalRandom.current().nextDouble() < remain
            ? (int) floor + 1
            : (int) floor;
    }

    /**
     * Get the skills points multiplied with the post multiply factor.
     */
    public final int getFinalSkillPoints() {
        return postMultiplyFactor == 1.0
            ? skillPoints
            : roundFair((double) skillPoints * postMultiplyFactor);
    }

    /**
     * Get the money reward multiplied with the post multiply factor.
     */
    public final double getFinalMoney() {
        return money * postMultiplyFactor;
    }

    /**
     * Get the exp reward multiplied with the post multiply factor.
     */
    public final int getFinalExp() {
        return postMultiplyFactor == 1.0
            ? exp
            : roundFair((double) exp * postMultiplyFactor);
    }

    /**
     * Adds another factor to the factor.
     */
    public void multiplyFactor(final double factor) {
        postMultiplyFactor *= factor;
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
