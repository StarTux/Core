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
        provider.store(new BackLocation(player.getUniqueId(), NetworkServer.current(), plugin, location, description, false));
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

    /**
     * Send a player back if there is a back location.  The sending
     * back will be done as a side effect.  The callback may respond
     * to the result but does not need to do anything.
     *
     * @param player the player
     * @param callback accepts the back location or null
     */
    public static void sendBack(Player player, Consumer<BackLocation> callback) {
        if (provider == null) return;
        provider.back(player.getUniqueId(), callback);
    }

    /**
     * Send the player back as an automatic task.  We only want to
     * send them back if they actually logged out in the back
     * location.  All other logic is equals to sendBack().
     */
    public static void sendBackAuto(Player player, Consumer<BackLocation> callback) {
        if (provider == null) return;
        provider.backAuto(player.getUniqueId(), callback);
    }

    protected static BackProvider provider = null;
    private Back() { }
}
