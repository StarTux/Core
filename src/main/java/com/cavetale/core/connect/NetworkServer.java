package com.cavetale.core.connect;

/**
 * Enumerate each server connecting within the Cavetale network.
 */
public enum NetworkServer {
    CAVETALE(ServerCategory.SURVIVAL),
    MINE(ServerCategory.SURVIVAL),
    RAID(ServerCategory.SURVIVAL),

    CREATIVE(ServerCategory.CREATIVE),

    ALPHA(ServerCategory.SURVIVAL_TEST),
    BETA(ServerCategory.SURVIVAL_TEST),

    HUB(ServerCategory.HUB),

    COLORFALL(ServerCategory.MINIGAME),
    VERTIGO(ServerCategory.MINIGAME),
    PVP_ARENA(ServerCategory.MINIGAME),

    RACE(ServerCategory.EVENT),
    SPLEEF(ServerCategory.EVENT),
    BINGO(ServerCategory.EVENT),
    ENDERBALL(ServerCategory.EVENT),
    HIDE_AND_SEEK(ServerCategory.EVENT),
    SURVIVAL_GAMES(ServerCategory.EVENT),

    WORLDGEN(ServerCategory.WORLD_GENERATION),
    UNKNOWN(ServerCategory.UNKNOWN);

    public final ServerCategory category;
    public final String registeredName;

    NetworkServer(final ServerCategory category) {
        this.category = category;
        this.registeredName = name().toLowerCase().replace("_", "");
    }

    public static NetworkServer of(String name) {
        for (NetworkServer it : NetworkServer.values()) {
            if (it.registeredName.equals(name)) return it;
        }
        return NetworkServer.UNKNOWN;
    }

    public static NetworkServer current() {
        return of(Connect.get().getServerName());
    }

    public static NetworkServer manager() {
        switch (current()) {
        case ALPHA: case BETA: return BETA;
        default: return CAVETALE;
        }
    }

    public static NetworkServer mining() {
        switch (current()) {
        case ALPHA: case BETA: return BETA;
        default: return MINE;
        }
    }
}
