package com.cavetale.core.connect;

import com.cavetale.core.CorePlugin;

public interface Connect {
    default void register() {
        Holder.connect = this;
        CorePlugin.getInstance().getLogger().info("Connect registered: " + getClass().getName());
    }

    default void unregister() {
        Holder.connect = DefaultConnect.INSTANCE;
    }

    static Connect get() {
        return Holder.connect;
    }

    String getServerName();
}

final class Holder {
    private Holder() { };

    protected static Connect connect = DefaultConnect.INSTANCE;
}
