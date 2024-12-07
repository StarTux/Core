package com.cavetale.core.menu;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import org.bukkit.inventory.ItemStack;

/**
 * Most plugins will want to put one item in the menu.  They should
 * either listen to MenuItemClickEvent or set the command property in
 * order to respond to a click.
 */
@Value
public final class MenuItemEntry implements Comparable<MenuItemEntry> {
    @NonNull private final Priority priority;
    @NonNull private final String key; // e.g. "money"
    @NonNull private final ItemStack icon;
    private final String command;

    public enum Priority {
        SERVER,
        HOTBAR,
        HIGHEST,
        HIGH,
        REGULAR,
        LOW,
        LOWEST,
        ;

        public int getValue() {
            return ordinal();
        }
    }

    @Data
    @Accessors(fluent = true, chain = true)
    public static final class Builder {
        private Priority priority = Priority.REGULAR;
        private String key;
        private ItemStack icon;
        private String command;

        public MenuItemEntry build() {
            return new MenuItemEntry(priority, key, icon, command);
        }
    }

    @Override
    public int compareTo(MenuItemEntry other) {
        return Integer.compare(priority.getValue(), other.priority.getValue());
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean hasCommand() {
        return command != null;
    }
}
