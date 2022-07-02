package com.cavetale.core.event.player;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * A player interacts with a named NPC.
 * Cancelling the event will prevent the caller from performing their
 * own actions, such as opening a dialogue.
 * Cancelling may still yield a reaction from the entity in question.
 * Denial is rather to be accomplished by canceling
 * PlayerInteractEntityEvent.
 */
@Getter
public final class PlayerInteractNpcEvent extends Event implements Cancellable {
    private final Player player;
    private final String name;
    private final Entity entity;
    @Setter private boolean cancelled;

    public PlayerInteractNpcEvent(@NonNull final Player player, @NonNull final String name, final Entity entity) {
        this.player = player;
        this.name = name;
        this.entity = entity;
    }

    public PlayerInteractNpcEvent(final Player player, final String name) {
        this(player, name, null);
    }

    public boolean hasEntity() {
        return entity != null;
    }

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
}
