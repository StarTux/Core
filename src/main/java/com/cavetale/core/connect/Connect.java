package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;
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
}

final class Companion {
    private Companion() { };

    protected static Connect connect = DefaultConnect.INSTANCE;
}
