package com.cavetale.core.util;

import com.cavetale.core.CorePlugin;
import org.bukkit.Bukkit;

public final class BukkitHacks {
    private static boolean syncCommandsScheduled;

    private BukkitHacks() { }

    public static void syncCommands() {
        if (syncCommandsScheduled) return;
        syncCommandsScheduled = true;
        Bukkit.getScheduler().runTask(CorePlugin.getInstance(), () -> {
                syncCommandsScheduled = false;
                try {
                    Bukkit.getServer().getClass().getMethod("syncCommands").invoke(Bukkit.getServer());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
    }
}
