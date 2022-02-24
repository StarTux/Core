package com.cavetale.core.connect;

import org.bukkit.entity.Player;

final class DefaultConnect implements Connect {
    protected static final DefaultConnect INSTANCE = new DefaultConnect();

    @Override
    public String getServerName() {
        return "unknown";
    }

    @Override
    public void dispatchRemoteCommand(Player player, String command, String targetServer) { }
}
