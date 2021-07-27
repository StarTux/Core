package com.cavetale.core;

import com.cavetale.core.command.CommandNode;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.block.PlayerCanBuildEvent;
import com.cavetale.core.font.Emoji;
import com.cavetale.core.font.Unicode;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class CorePlugin extends JavaPlugin {
    @Getter private static CorePlugin instance;
    private CommandNode coreCommand;

    @Override
    public void onEnable() {
        instance = this;
        loadConfiguration();
        Emoji.init();
        Bukkit.getScheduler().runTask(this, () -> {
                getLogger().info(Emoji.count() + " emoji loaded");
            });
        coreCommand = new CommandNode("core");
        coreCommand.addChild("save")
            .denyTabCompletion()
            .description("Save config to disk")
            .senderCaller(this::saveCommand);
        coreCommand.addChild("reload")
            .denyTabCompletion()
            .description("Reload the config file")
            .senderCaller(this::reloadCommand);
        coreCommand.addChild("unicode")
            .denyTabCompletion()
            .description("Unicode test")
            .senderCaller((sender, a) -> {
                    if (a.length != 0) return false;
                    StringBuilder sb = new StringBuilder();
                    for (Unicode unicode : Unicode.values()) {
                        sb.append(unicode.character);
                    }
                    sender.sendMessage(sb.toString());
                    return true;
                });
        coreCommand.addChild("emoji")
            .denyTabCompletion()
            .description("Emoji dump")
            .senderCaller((sender, args) -> {
                    if (args.length != 0) return false;
                    Emoji.dump(sender);
                    return true;
                });
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return coreCommand.call(sender, command, alias, args);
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return coreCommand.complete(sender, command, alias, args);
    }

    private boolean saveCommand(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        saveDefaultConfig();
        sender.sendMessage("[Core] Default configuration saved to disk!");
        return true;
    }

    private boolean reloadCommand(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        loadConfiguration();
        sender.sendMessage("[Core] Default configuration saved to disk!");
        return true;
    }

    private void loadConfiguration() {
        reloadConfig();
        boolean denyBuilding = getConfig().getBoolean("DenyBuilding");
        PlayerCanBuildEvent.setDenyBuilding(denyBuilding);
        PlayerBreakBlockEvent.setDenyBuilding(denyBuilding);
        if (denyBuilding) {
            getLogger().info("Deny building enabled!");
        }
    }
}
