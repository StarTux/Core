package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.command.RemotePlayer;
import java.util.ArrayList;
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
}
