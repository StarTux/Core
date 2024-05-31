package com.cavetale.core.item;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

/**
 * This utility class has convenience methods replacing
 * ItemKind.of(item)#xyz().
 */
public final class ItemKinds {
    public static NamespacedKey getKey(ItemStack item) {
        return ItemKind.of(item).getKey();
    }

    public static ItemKind itemKind(ItemStack item) {
        return ItemKind.of(item);
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

    public static boolean isSimilar(ItemStack a, ItemStack b) {
        return ItemKind.of(a).isSimilar(a, b);
    }

    public static int getMaxStackSize(ItemStack item) {
        return ItemKind.of(item).getMaxStackSize(item);
    }

    public static Component chatDescription(ItemStack item) {
        return ItemKind.of(item).chatDescription(item);
    }

    public static Component chatDescription(ItemStack item, int amount) {
        return ItemKind.of(item).chatDescription(item, amount);
    }

    public static Component iconDescription(ItemStack item) {
        return ItemKind.of(item).iconDescription(item);
    }

    public static Component iconDescription(ItemStack item, int amount) {
        return ItemKind.of(item).iconDescription(item, amount);
    }

    private ItemKinds() { }
}
