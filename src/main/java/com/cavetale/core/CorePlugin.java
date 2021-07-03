package com.cavetale.core;

import com.cavetale.core.font.Emoji;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class CorePlugin extends JavaPlugin {
    @Getter private static CorePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Emoji.init();
        Bukkit.getScheduler().runTask(this, () -> {
                getLogger().info(Emoji.count() + " emoji loaded");
            });
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(final CommandSender sender,
                             final Command command,
                             final String alias,
                             final String[] args) {
        return true;
    }
}
