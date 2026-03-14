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
 * When an entity hits another entity with a ranged attack.
 */
@Getter
public final class EntityShootEntityEvent extends Event implements Cancellable {
    /**
     * Required by Event.
     */
    @Getter private static HandlerList handlerList = new HandlerList();
    private final Entity shooter;
    private final Entity target;
    private final ItemStack weapon;
    private final Entity projectile;
    @Setter private double targetDamage;
    @Setter private Vector targetVelocity;
    @Setter private boolean cancelled;

    /**
     * Required by Event.
     */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }

    public EntityShootEntityEvent(
        final Entity shooter,
        final Entity target,
        final ItemStack weapon,
        final Entity projectile,
        final double targetDamage,
        final Vector targetVelocity
    ) {
        this.shooter = shooter;
        this.target = target;
        this.weapon = weapon;
        this.projectile = projectile;
        this.targetDamage = targetDamage;
        this.targetVelocity = targetVelocity;
    }

    public EntityShootEntityEvent(
        final Entity shooter,
        final Entity target,
        final ItemStack weapon,
        final Entity projectile
    ) {
        this.shooter = shooter;
        this.target = target;
        this.weapon = weapon;
        this.projectile = projectile;
    }

    public EntityShootEntityEvent(
        final Entity shooter,
        final Entity target,
        final ItemStack weapon
    ) {
        this(shooter, target, weapon, null);
    }

    public EntityShootEntityEvent(
        final Entity shooter,
        final Entity target
    ) {
        this(shooter, target, null, null);
    }

    public boolean hasWeapon() {
        return weapon != null;
    }

    public boolean hasProjectile() {
        return projectile != null;
    }
}
