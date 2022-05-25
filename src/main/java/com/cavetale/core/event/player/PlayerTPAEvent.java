package com.cavetale.core.event.player;

import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * When a player requests TPA to another player or the receiving
 * player accepts it.  The requesting player may not currently be on
 * this server.
 */
@Getter @RequiredArgsConstructor
public final class PlayerTPAEvent extends Event implements Cancellable {
    @NonNull private final UUID requester;
    @NonNull private final Player target;
    @NonNull private final boolean accepted;
    @Setter private boolean cancelled;

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
