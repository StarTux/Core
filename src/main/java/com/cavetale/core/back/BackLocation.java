package com.cavetale.core.back;

import com.cavetale.core.connect.NetworkServer;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

@Value @AllArgsConstructor
/**
 * A BackLocation is a return location, unique per player, where a
 * player was last seen and can be returned to.
 *
 * A BackLocation can be serialized and sent over the network.
 */
public final class BackLocation {
    private final UUID playerUuid;
    private final NetworkServer server;
    private final String plugin;
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private final String description;

    public BackLocation(final UUID playerUuid, final NetworkServer server, final Plugin plugin, final Location location, final String description) {
        this.playerUuid = playerUuid;
        this.server = server;
        this.plugin = plugin.getName();
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
        this.description = description;
    }

    public boolean isOnThisServer() {
        return NetworkServer.current() == this.server;
    }

    /**
     * Get the location.  If the location is not from the current
     * server, this will yield null.
     */
    public Location getLocation() {
        World w = Bukkit.getWorld(world);
        if (w == null) return null;
        return new Location(w, x, y, z, yaw, pitch);
    }
}
