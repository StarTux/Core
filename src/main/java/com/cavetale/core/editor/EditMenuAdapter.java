package com.cavetale.core.editor;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

/**
 * Objects can implement this to enhance the interactivity of data
 * structures edited by the Editor plugin.
 */
public interface EditMenuAdapter {
    /**
     * Add buttons to the top bar of a menu view.
     */
    default List<EditMenuButton> getEditMenuButtons() {
        return List.of();
    }

    default ItemStack getMenuIcon() {
        return null;
    }

    default List<Component> getTooltip() {
        return null;
    }

    default List<Object> getPossibleValues(String fieldName, int typeIndex) {
        return null;
    }

    default Boolean validateValue(String fieldName, Object newValue, int typeIndex) {
        return null;
    }
}
