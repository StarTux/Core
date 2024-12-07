package com.cavetale.core.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Listen to this menu and call addItem() if you would like to add an
 * icon to the menu.
 * Actual execution will be in the Menu plugin.
 */
@RequiredArgsConstructor
public final class MenuItemEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    @Getter private final Player player;
    @Getter private final List<MenuItemEntry> entries = new ArrayList<>();
    private boolean shouldOpen;

    public void addItem(MenuItemEntry entry) {
        entries.add(entry);
    }

    public void addItem(Consumer<MenuItemEntry.Builder> callback) {
        MenuItemEntry.Builder builder = MenuItemEntry.builder();
        callback.accept(builder);
        entries.add(builder.build());
    }

    /**
     * Call this method to open the menu for a player.
     * The Menu plugin will read the value of the shouldOpen variable
     * and open the menu.
     */
    public static void openMenu(Player player) {
        MenuItemEvent event = new MenuItemEvent(player);
        event.shouldOpen = true;
        event.callEvent();
    }

    public boolean shouldOpen() {
        return shouldOpen;
    }

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
