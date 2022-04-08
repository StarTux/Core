package com.cavetale.core.editor;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A node represents an editor menu displayed in a GUI, with access to
 * the underlying Object.
 *
 * One node shall be provided by the framework to each function of
 * EditMenuAdapter and EditMenuDelegate.
 */
public interface EditMenuNode {
    @NotNull EditMenuContext getContext();

    @NotNull Object getObject();

    @Nullable EditMenuNode getParentNode();
}
