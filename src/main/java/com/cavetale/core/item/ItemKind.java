package com.cavetale.core.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.subscript;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;
import static net.kyori.adventure.text.format.NamedTextColor.*;

public interface ItemKind extends Keyed {
    String name(ItemStack item);

    Component displayName(ItemStack item);

    Component icon(ItemStack item);

    ItemStack create(String tag);

    boolean isSimilar(ItemStack a, ItemStack b);

    int getMaxStackSize(ItemStack item);

    default Component iconDisplayName(ItemStack item) {
        return join(noSeparators(),
                    icon(item),
                    displayName(item));
    }

    default Component chatDescription(ItemStack item) {
        return chatDescription(item, item.getAmount());
    }

    default Component chatDescription(ItemStack item, int amount) {
        Component text = amount > 1
            ? join(noSeparators(),
                   text(amount),
                   text("\u00D7", GRAY),
                   icon(item), displayName(item))
            : join(noSeparators(), icon(item), displayName(item));
        return text.hoverEvent(item.asHoverEvent());
    }

    default Component iconDescription(ItemStack item) {
        return iconDescription(item, item.getAmount());
    }

    default Component iconDescription(ItemStack item, int amount) {
        return (amount > 1
                ? join(noSeparators(), icon(item), text(subscript(amount)))
                : icon(item))
            .hoverEvent(item.asHoverEvent());
    }

    static ItemKind of(ItemStack item) {
        return ItemFinder.get().findItem(item);
    }

    static ItemKind of(NamespacedKey key) {
        return ItemFinder.get().findItem(key);
    }
}
