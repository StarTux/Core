package com.cavetale.core.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public final class MenuItemClickEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    @Getter private final Player player;
    @Getter private final MenuItemEntry entry;

    /**
     * Required by Event.
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Required by Event.
     */
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
