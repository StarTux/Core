package com.cavetale.core.editor;

import java.util.List;
import java.util.function.BiConsumer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public interface EditMenuButton {
    ItemStack getMenuIcon();

    List<Component> getTooltip();

    void onClick(Player player, ClickType clickType);

    static EditMenuButton of(final ItemStack theItem,
                             final List<Component> theTooltip,
                             final BiConsumer<Player, ClickType> theCallback) {
        return new EditMenuButton() {
            @Override
            public ItemStack getMenuIcon() {
                return theItem.clone();
            }

            @Override
            public List<Component> getTooltip() {
                return theTooltip;
            }

            @Override
            public void onClick(Player player, ClickType clickType) {
                theCallback.accept(player, clickType);
            }
        };
    }
}
