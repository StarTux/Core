package com.cavetale.core.playercache;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * The default implementation uses the builtin player storage.
 */
final class DefaultPlayerCacheDataSource implements PlayerCacheDataSource {
    protected static final DefaultPlayerCacheDataSource INSTANCE = new DefaultPlayerCacheDataSource();

    @Override
    public PlayerCache forUuid(UUID uuid) {
        OfflinePlayer off = Bukkit.getOfflinePlayer(uuid);
        return off != null
            ? PlayerCache.of(off)
            : null;
    }

    @Override
    public PlayerCache forName(String name) {
        OfflinePlayer off = Bukkit.getOfflinePlayer(name);
        return off != null
            ? PlayerCache.of(off)
            : null;
    }

    @Override
    public List<String> completeNames(String arg) {
        List<String> list = new ArrayList<>();
        String lower = arg.toLowerCase();
        for  (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().contains(lower)) {
                list.add(player.getName());
            }
        }
        if (!list.isEmpty()) return list;
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName().toLowerCase().contains(lower)) {
                list.add(player.getName());
            }
        }
        return list;
    }
}
