package com.cavetale.core.connect;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

final class DefaultConnect implements Connect {
    protected static final DefaultConnect INSTANCE = new DefaultConnect();

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
}
