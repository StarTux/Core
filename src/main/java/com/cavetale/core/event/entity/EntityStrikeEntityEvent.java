package com.cavetale.core.event.entity;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * When an entity hits another entity with a melee attack.
 */
@Getter
public final class EntityStrikeEntityEvent extends Event implements Cancellable {
    /**
     * Required by Event.
     */
    @Getter private static HandlerList handlerList = new HandlerList();
    private final Entity attacker;
    private final Entity target;
    private final ItemStack weapon;
    @Setter private double targetDamage;
    @Setter private Vector targetVelocity;
    @Setter private boolean cancelled;

    /**
     * Required by Event.
     */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }

    public EntityStrikeEntityEvent(
        final Entity attacker,
        final Entity target,
        final ItemStack weapon,
        final double targetDamage,
        final Vector targetVelocity
    ) {
        this.attacker = attacker;
        this.target = target;
        this.weapon = weapon;
        this.targetDamage = targetDamage;
        this.targetVelocity = targetVelocity;
    }

    public EntityStrikeEntityEvent(
        final Entity attacker,
        final Entity target,
        final ItemStack weapon
    ) {
        this.attacker = attacker;
        this.target = target;
        this.weapon = weapon;
    }

    public EntityStrikeEntityEvent(
        final Entity attacker,
        final Entity target
    ) {
        this(attacker, target, null);
    }

    public boolean hasWeapon() {
        return weapon != null;
    }
}
