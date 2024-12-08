package com.cavetale.core.menu;

import lombok.Data;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.format.TextColor;
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
    @NonNull private String sortingName;
    private final String command;
    private final TextColor highlightColor;

    public enum Priority {
        SERVER,
        HOTBAR,
        NOTIFICATION,
        REGULAR,
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
        private String sortingName;
        private String command;
        private TextColor highlightColor;

        public MenuItemEntry build() {
            if (priority == null) throw new NullPointerException("priority cannot be null");
            if (key == null) throw new NullPointerException("key cannot be null");
            if (icon == null) throw new NullPointerException("icon cannot be null");
            if (sortingName == null) {
                sortingName = key.contains(":")
                    ? key.split(":", 2)[1]
                    : key;
            }
            return new MenuItemEntry(priority, key, icon, sortingName, command, highlightColor);
        }
    }

    @Override
    public int compareTo(MenuItemEntry other) {
        final int prio = Integer.compare(priority.getValue(), other.priority.getValue());
        if (prio != 0) return prio;
        return sortingName.compareToIgnoreCase(other.sortingName);
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean hasCommand() {
        return command != null;
    }

    public boolean hasHighlightColor() {
        return highlightColor != null;
    }
}
