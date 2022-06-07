package com.cavetale.core.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public final class ItemKinds {
    public static NamespacedKey getKey(ItemStack item) {
        return ItemKind.of(item).getKey();
    }

    public static String name(ItemStack item) {
        return ItemKind.of(item).name(item);
    }

    public static Component displayName(ItemStack item) {
        return ItemKind.of(item).displayName(item);
    }

    public static Component icon(ItemStack item) {
        return ItemKind.of(item).icon(item);
    }

    public static ItemStack create(String input) {
        int index = input.indexOf("{");
        final String name = index >= 0
            ? input.substring(0, index - 1)
            : input;
        final String tag = index >= 0
            ? input.substring(index)
            : "";
        NamespacedKey key = NamespacedKey.fromString(name);
        if (key == null) return null;
        return ItemKind.of(key).create(tag);
    }

    public static Component chatDescription(ItemStack item) {
        return ItemKind.of(item).chatDescription(item);
    }

    private ItemKinds() { }
}
