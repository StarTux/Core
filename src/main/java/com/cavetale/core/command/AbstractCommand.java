package com.cavetale.core.command;

import java.util.List;
import lombok.NonNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Abstract implementation of a TabExecutor using CommandNode.
 *
 * - Override the instructor and onEnable().
 * - After instantiation, call enable().
 *
 * @param <P> the owning plugin class
 */
public abstract class AbstractCommand<P extends JavaPlugin> implements TabExecutor {
    protected final P plugin;
    protected final CommandNode rootNode;
    protected final String commandName;

    public AbstractCommand(@NonNull final P plugin, @NonNull final String commandName) {
        this.plugin = plugin;
        this.rootNode = new CommandNode(commandName);
        this.commandName = commandName;
    }

    public final void enable() {
        onEnable();
        plugin.getCommand(commandName).setExecutor(this);
    }

    protected abstract void onEnable();

    @Override
    public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return rootNode.call(sender, command, label, args);
    }

    @Override
    public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return rootNode.complete(sender, command, label, args);
    }
}
