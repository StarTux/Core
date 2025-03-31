package com.cavetale.core.item;

import com.cavetale.core.font.Unicode;
import net.kyori.adventure.text.Component;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.font.Unicode.subscript;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.Component.textOfChildren;

public interface ItemKind extends Keyed {
    String name(ItemStack item);

    Component displayName(ItemStack item);

    Component icon(ItemStack item);

    ItemStack create(String tag);

    boolean isSimilar(ItemStack a, ItemStack b);

    int getMaxStackSize(ItemStack item);

    default Component iconDisplayName(ItemStack item) {
        return textOfChildren(icon(item), displayName(item));
    }

    default Component chatDescription(ItemStack item) {
        return chatDescription(item, item.getAmount());
    }

    default Component chatDescription(ItemStack item, int amount) {
        Component text = amount > 1
            ? textOfChildren(text(amount),
                             text(Unicode.MULTIPLICATION.string),
                             icon(item), displayName(item))
            : textOfChildren(icon(item), displayName(item));
        return text.hoverEvent(item.asOne().asHoverEvent());
    }

    default Component iconDescription(ItemStack item) {
        return iconDescription(item, item.getAmount());
    }

    default Component iconDescription(ItemStack item, int amount) {
        return (amount > 1
                ? textOfChildren(icon(item), text(subscript(amount)))
                : icon(item))
            .hoverEvent(item.asOne().asHoverEvent());
    }

    static ItemKind of(ItemStack item) {
        return ItemFinder.get().findItem(item);
    }

    static ItemKind of(NamespacedKey key) {
        return ItemFinder.get().findItem(key);
    }
}
