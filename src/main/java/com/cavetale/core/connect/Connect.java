package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;
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

    void dispatchRemoteCommand(Player player, String command, String targetServer);

    Set<UUID> getOnlinePlayers();
}

final class Companion {
    protected static final DefaultConnect DEFAULT = new DefaultConnect();
    protected static Connect connect = DEFAULT;

    private Companion() { }
}
