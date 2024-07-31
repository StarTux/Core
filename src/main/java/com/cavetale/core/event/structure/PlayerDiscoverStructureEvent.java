package com.cavetale.core.event.structure;

import com.cavetale.core.structure.Structure;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Value
@EqualsAndHashCode(callSuper = true)
public final class PlayerDiscoverStructureEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final Player player;
    private final Structure structure;

    /**
     * Required by Event.
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
