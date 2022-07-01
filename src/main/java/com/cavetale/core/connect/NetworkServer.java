package com.cavetale.core.connect;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumerate each server connecting within the Cavetale network.
 */
public enum NetworkServer {
    HUB(ServerCategory.SURVIVAL, ServerGroup.MAIN),
    CAVETALE(ServerCategory.SURVIVAL, ServerGroup.MUSEUM),
    MINE(ServerCategory.SURVIVAL, ServerGroup.MAIN),
    RAID(ServerCategory.SURVIVAL, ServerGroup.MAIN),

    EINS(ServerCategory.SURVIVAL, ServerGroup.MAIN),
    ZWEI(ServerCategory.SURVIVAL, ServerGroup.MAIN),

    CREATIVE(ServerCategory.CREATIVE, ServerGroup.MAIN),

    ALPHA(ServerCategory.SURVIVAL_TEST, ServerGroup.TESTING),
    BETA(ServerCategory.SURVIVAL_TEST, ServerGroup.TESTING),

    COLORFALL(ServerCategory.MINIGAME, ServerGroup.MAIN),
    VERTIGO(ServerCategory.MINIGAME, ServerGroup.MAIN),
    PVP_ARENA(ServerCategory.MINIGAME, ServerGroup.MAIN),

    RACE(ServerCategory.EVENT, ServerGroup.MAIN),
    SPLEEF(ServerCategory.EVENT, ServerGroup.MAIN),
    BINGO(ServerCategory.EVENT, ServerGroup.MAIN),
    ENDERBALL(ServerCategory.EVENT, ServerGroup.MAIN),
    HIDE_AND_SEEK(ServerCategory.EVENT, ServerGroup.MAIN),
    SURVIVAL_GAMES(ServerCategory.EVENT, ServerGroup.MAIN),

    WORLDGEN(ServerCategory.WORLD_GENERATION, ServerGroup.NONE),
    UNKNOWN(ServerCategory.UNKNOWN, ServerGroup.NONE);

    public final ServerCategory category;
    public final ServerGroup group;
    public final String registeredName;

    NetworkServer(final ServerCategory category, final ServerGroup group) {
        this.category = category;
        this.group = group;
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

    public NetworkServer getManager() {
        switch (this.group) {
        case TESTING: return BETA;
        case MAIN: return HUB;
        case MUSEUM: return CAVETALE;
        default: return UNKNOWN;
        }
    }

    public NetworkServer getMining() {
        switch (this.group) {
        case TESTING: return BETA;
        case MAIN: return MINE;
        case MUSEUM: return MINE;
        default: return UNKNOWN;
        }
    }

    public static List<NetworkServer> of(ServerCategory theCategory) {
        List<NetworkServer> list = new ArrayList<>();
        for (NetworkServer it : values()) {
            if (it.category == theCategory) list.add(it);
        }
        return list;
    }

    public static List<NetworkServer> of(ServerGroup theGroup) {
        List<NetworkServer> list = new ArrayList<>();
        for (NetworkServer it : values()) {
            if (it.group == theGroup) list.add(it);
        }
        return list;
    }

    @Deprecated
    public static NetworkServer manager() {
        return current().getManager();
    }

    @Deprecated
    public static NetworkServer mining() {
        return current().getMining();
    }
}
