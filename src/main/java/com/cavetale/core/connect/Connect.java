package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Player;

public interface Connect {
    default void register() {
        Companion.connect = this;
        CorePlugin.getInstance().getLogger().info("Connect registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.connect = DefaultConnect.INSTANCE;
    }

    static Connect get() {
        return Companion.connect;
    }

    String getServerName();

    void dispatchRemoteCommand(Player player, String command, String targetServer);

    Set<UUID> getOnlinePlayers();
}

final class Companion {
    private Companion() { };

    protected static Connect connect = DefaultConnect.INSTANCE;
}
