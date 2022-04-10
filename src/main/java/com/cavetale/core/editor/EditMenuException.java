package com.cavetale.core.editor;

/**
 * An exception thrown when an error occurs while the user clicks in
 * the editor menu.  The message is intended to be displayed to them.
 *
 * However, the error is not supposed to print to console or cause any
 * other disruption.
 */
public final class EditMenuException extends RuntimeException {
    public EditMenuException(final String message) {
        super(message);
    }
}
