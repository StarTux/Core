package com.cavetale.core.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class CommandContext {
    public final CommandSender sender;
    public final Command command;
    public final Player player;
    public final String label;
    public final String[] args;

    public CommandContext(final CommandSender sender, final Command command, final String label, final String[] args) {
        this.sender = sender;
        this.command = command;
        this.player = sender instanceof Player ? (Player) sender : null;
        this.label = label;
        this.args = args;
    }

    public boolean isPlayer() {
        return player != null;
    }

    public boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    public boolean isBlock() {
        return sender instanceof BlockCommandSender;
    }

    public boolean isEntity() {
        return sender instanceof Entity;
    }

    public Player requirePlayer() {
        if (!isPlayer()) throw new CommandWarn("Player expected");
        return player;
    }

    public RemotePlayer requireRemotePlayer() {
        if (player != null) return new RemotePlayerWrapper(player);
        if (sender instanceof RemotePlayer remotePlayer) return remotePlayer;
        throw new CommandWarn("(Remote) Player expected");
    }

    public void message(String msg) {
        sender.sendMessage(msg);
    }

    public void message(Component msg) {
        sender.sendMessage(msg);
    }

    /**
     * Convenience function for the command caller.
     * Call like so: `return context.warn("Error Message");`
     */
    public boolean warn(String msg) {
        throw new CommandWarn(msg);
    }
}
