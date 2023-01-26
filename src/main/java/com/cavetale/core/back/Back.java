package com.cavetale.core.back;

import com.cavetale.core.connect.NetworkServer;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Public access utility class for the Back framework.
 */
public final class Back {
    public static void setBackLocation(Player player, Plugin plugin, Location location, String description) {
        if (provider == null) return;
        provider.store(new BackLocation(player.getUniqueId(), NetworkServer.current(), plugin, location, description));
    }

    public static void resetBackLocation(Player player) {
        if (provider == null) return;
        provider.reset(player.getUniqueId());
    }

    public static void resetBackLocation(Player player, Plugin plugin) {
        if (provider == null) return;
        provider.reset(player.getUniqueId(), plugin);
    }

    public static void resetAllBackLocations(Plugin plugin) {
        if (provider == null) return;
        provider.resetAll(plugin);
    }

    public static void getBackLocation(Player player, Consumer<BackLocation> callback) {
        if (provider == null) return;
        provider.load(player.getUniqueId(), callback);
    }

    public static void sendBack(Player player, Consumer<BackLocation> callback) {
        if (provider == null) return;
        provider.back(player.getUniqueId(), callback);
    }

    protected static BackProvider provider = null;
    private Back() { }
}
