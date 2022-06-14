package com.cavetale.core.connect;

/**
 * Certain plugins want to share their functionality only with their
 * respective group.
 * Including inventories, auctions, etc.
 * Not including: Chat.
 */
public enum ServerGroup {
    MAIN,
    TESTING,
    MUSEUM,
    NONE;

    public static ServerGroup current() {
        return NetworkServer.current().group;
    }
}
