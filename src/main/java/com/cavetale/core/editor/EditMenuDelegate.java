package com.cavetale.core.editor;

import org.jetbrains.annotations.Nullable;

/**
 * The delegate is called by the framework and is to provide standard
 * functions built into the menu.  Each call may yield null to deny
 * the desired functionality.  Returning not null requires a working
 * implementation of save, load, etc, within the current context.
 */
public interface EditMenuDelegate {
    default @Nullable Runnable getSaveFunction(EditMenuNode node) {
        return null;
    }

    default @Nullable Runnable getReloadFunction(EditMenuNode node) {
        return null;
    }
}
