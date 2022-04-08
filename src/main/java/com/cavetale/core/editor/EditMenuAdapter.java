package com.cavetale.core.editor;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Objects can implement this to enhance the interactivity of data
 * structures edited by the Editor plugin.
 */
public interface EditMenuAdapter {
    /**
     * Add buttons to the top bar of a menu view.
     */
    default @Nullable List<EditMenuButton> getEditMenuButtons(EditMenuNode node) {
        return List.of();
    }

    default @Nullable ItemStack getMenuIcon(EditMenuNode node) {
        return null;
    }

    default @Nullable List<Component> getTooltip(EditMenuNode node) {
        return null;
    }

    default @Nullable List<Object> getPossibleValues(EditMenuNode node, String fieldName, int typeIndex) {
        return null;
    }

    default @Nullable Boolean validateValue(EditMenuNode node, String fieldName, Object newValue, int typeIndex) {
        return null;
    }

    default @Nullable Object createNewValue(EditMenuNode node, String fieldName, int typeIndex) {
        return null;
    }
}
