package com.cavetale.core.event.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event is called to communicate whether a certain player has
 * permission to interact with another entity in a certain way.
 *
 * Cancel this event to negate the given relation. Let it pass through
 * to accept it.
 */
@Getter
public final class PlayerEntityAbilityQuery extends Event implements Cancellable {
    @NonNull private final Player player;
    @NonNull private final Entity entity;
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

    public PlayerEntityAbilityQuery(final Player player, final Entity entity, final Action action) {
        this.player = player;
        this.entity = entity;
        this.action = action;
        if (denyBuilding && !player.isOp()) cancelled = true;
    }

    @Getter @RequiredArgsConstructor
    public enum Action {
        DAMAGE,
        POTION,
        SIT, // Cats, Dogs
        FEED,
        BREED,
        LEASH,
        CATCH, // PocketMob
        SHEAR,
        MOUNT,
        DISMOUNT,
        PICKUP,
        OPEN, // Donkeys, Villagers
        INVENTORY, // ItemFrame, ArmorStand
        MOVE, // ArmorStand, Vehciles?
        PLACE, // Vehicles
        GIMMICK; // Funzy stuff, e.g. Blunderbuss

        public boolean query(final Player thePlayer, final Entity theEntity) {
            PlayerEntityAbilityQuery query = new PlayerEntityAbilityQuery(thePlayer, theEntity, this);
            Bukkit.getPluginManager().callEvent(query);
            return !query.isCancelled();
        }
    }
}
