package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.command.RemotePlayer;
import com.cavetale.core.event.connect.ConnectMessageEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

final class DefaultConnect implements Connect {
    @Override
    public CorePlugin getPlugin() {
        return CorePlugin.getInstance();
    }

    @Override
    public String getServerName() {
        return "unknown";
    }

    @Override
    public void sendMessage(String targetServer, String channel, String payload) { }

    @Override
    public void broadcastMessage(String channel, String payload) { }

    @Override
    public void broadcastMessageToAll(String channel, String payload) {
        new ConnectMessageEvent(channel, payload, getServerName(), getServerName(), new Date()).callEvent();
    }

    @Override
    public void dispatchRemoteCommand(Player player, String command, String targetServer) { }

    @Override
    public Set<UUID> getOnlinePlayers() {
        Set<UUID> result = new HashSet<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            result.add(player.getUniqueId());
        }
        return result;
    }

    @Override
    public List<RemotePlayer> getRemotePlayers() {
        List<RemotePlayer> result = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            result.add(RemotePlayer.wrap(player));
        }
        return result;
    }

    @Override
    public RemotePlayer getRemotePlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null
            ? RemotePlayer.wrap(player)
            : null;
    }
}
