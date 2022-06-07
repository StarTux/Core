package com.cavetale.core.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.*;

/**
 * Can be thrown from inside CommandCall::call.
 */
public final class CommandWarn extends RuntimeException {
    protected Component message;

    public CommandWarn(final String msg) {
        super(msg);
        this.message = text(msg, RED);
    }

    public CommandWarn(final Component msg) {
        this.message = msg;
    }

    public void send(CommandSender sender) {
        sender.sendMessage(message);
    }
}
