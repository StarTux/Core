package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.command.RemotePlayer;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface Connect {
    default void register() {
        Companion.connect = this;
        CorePlugin.getInstance().getLogger().info("Connect registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.connect = Companion.DEFAULT;
    }

    static Connect get() {
        return Companion.connect;
    }

    Plugin getPlugin();

    String getServerName();

    /**
     * ConnectMessageEvent.
     * Send a message to some server. They will receive
     */
    void sendMessage(String targetServer, String channel, String payload);

    /**
     * Send a message to all servers. They will receive
     * ConnectMessageEvent.
     */
    void broadcastMessage(String channel, String payload);

    void broadcastMessage(ServerGroup group, String channel, String payload);

    /**
     * Send a message to all servers including this server. They will
     * receive ConnectMessageEvent.
     */
    void broadcastMessageToAll(String channel, String payload);

    void broadcastMessageToAll(ServerGroup group, String channel, String payload);

    void dispatchRemoteCommand(Player player, String command, String targetServer);

    int getOnlinePlayerCount();

    Set<UUID> getOnlinePlayers();

    /**
     * Get a list of all online players, represented as RemotePlayer
     * instances.
     */
    List<RemotePlayer> getRemotePlayers();

    RemotePlayer getRemotePlayer(UUID uuid);

    Set<String> getOnlineServerNames();

    default boolean serverIsOnline(String name) {
        return getOnlineServerNames().contains(name);
    }
}

final class Companion {
    protected static final DefaultConnect DEFAULT = new DefaultConnect();
    protected static Connect connect = DEFAULT;

    private Companion() { }
}
