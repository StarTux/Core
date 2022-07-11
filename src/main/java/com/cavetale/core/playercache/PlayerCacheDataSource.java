package com.cavetale.core.playercache;

import com.cavetale.core.CorePlugin;
import java.util.List;
import java.util.UUID;

public interface PlayerCacheDataSource {
    default void register() {
        PlayerCache.dataSource = this;
        CorePlugin.getInstance().getLogger().info("PlayerCacheDataSource registered: " + getClass().getName());
    }

    default void unregister() {
        PlayerCache.dataSource = DefaultPlayerCacheDataSource.INSTANCE;
    }

    static PlayerCacheDataSource get() {
        return PlayerCache.dataSource;
    }

    PlayerCache forUuid(UUID uuid);

    PlayerCache forName(String name);

    default PlayerCache forArg(String arg) {
        try {
            return forUuid(UUID.fromString(arg));
        } catch (IllegalArgumentException iae) {
            return forName(arg);
        }
    }

    List<String> completeNames(String arg);
}
