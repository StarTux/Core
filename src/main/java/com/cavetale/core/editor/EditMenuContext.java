package com.cavetale.core.editor;

import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The context shall be provided by the framework to EditMenuAdapter
 * and EditMenuDelegate by way of EditMenuNode.
 */
public interface EditMenuContext {
    @NotNull Plugin getOwningPlugin();

    @NotNull Player getPlayer();

    @NotNull Object getRootObject();

    @NotNull List<String> getCurrentPath();
}
