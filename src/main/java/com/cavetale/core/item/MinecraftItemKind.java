package com.cavetale.core.item;

import com.cavetale.core.font.VanillaItems;
import com.cavetale.core.util.BlockColor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import static net.kyori.adventure.text.Component.translatable;
import static net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer.plainText;

@RequiredArgsConstructor
public final class MinecraftItemKind implements ItemKind {
    @Getter private final Material material;

    @Override
    public NamespacedKey getKey() {
        return material.getKey();
    }

    @Override
    public String name(ItemStack item) {
        return plainText().serialize(translatable(item));
    }

    public static TextColor getColor(ItemStack item) {
        BlockColor blockColor = BlockColor.of(item.getType());
        if (blockColor != null) return blockColor.textColor;
        return item.getRarity().getColor();
    }

    public static Component coloredDisplayName(ItemStack item) {
        return Component.translatable(item, getColor(item));
    }

    @Override
    public Component displayName(ItemStack item) {
        return Component.translatable(item);
    }

    @Override
    public Component icon(ItemStack item) {
        return VanillaItems.componentOf(material);
    }

    @Override
    public ItemStack create(String tag) {
        return !tag.isEmpty()
            ? Bukkit.getItemFactory().createItemStack(tag)
            : new ItemStack(material);
    }

    @Override
    public boolean isSimilar(ItemStack a, ItemStack b) {
        return a.isSimilar(b);
    }

    @Override
    public int getMaxStackSize(ItemStack item) {
        return item.getType().getMaxStackSize();
    }
}
