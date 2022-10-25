package com.cavetale.core.connect;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import static com.cavetale.core.connect.ServerCategory.EVENT;
import static com.cavetale.core.connect.ServerCategory.HOME;
import static com.cavetale.core.connect.ServerCategory.MINIGAME;
import static com.cavetale.core.connect.ServerCategory.MINING;
import static com.cavetale.core.connect.ServerCategory.SURVIVAL;
import static com.cavetale.core.connect.ServerCategory.SURVIVAL_TEST;
import static com.cavetale.core.connect.ServerGroup.*;

/**
 * Enumerate each server connecting within the Cavetale network.
 */
public enum NetworkServer {
    HUB(SURVIVAL, MAIN),
    CAVETALE(SURVIVAL, MUSEUM),
    MINE(SURVIVAL, MAIN, Set.of(MINING)),
    RAID(SURVIVAL, MAIN),
    MOB_ARENA(SURVIVAL, MAIN),

    EINS(SURVIVAL, MAIN, Set.of(HOME)),
    ZWEI(SURVIVAL, MAIN, Set.of(HOME)),
    DREI(SURVIVAL, MAIN, Set.of(HOME)),
    VIER(SURVIVAL, MAIN, Set.of(HOME)),

    CREATIVE(ServerCategory.CREATIVE, MAIN),

    ALPHA(SURVIVAL_TEST, TESTING),
    BETA(SURVIVAL_TEST, TESTING),

    COLORFALL(MINIGAME, MAIN),
    VERTIGO(MINIGAME, MAIN),
    PVP_ARENA(MINIGAME, MAIN),

    RACE(EVENT, MAIN),
    SPLEEF(EVENT, MAIN),
    BINGO(EVENT, MAIN),
    ENDERBALL(EVENT, MAIN),
    HIDE_AND_SEEK(EVENT, MAIN),
    SURVIVAL_GAMES(EVENT, MAIN),

    WORLDGEN(ServerCategory.WORLD_GENERATION, NONE),
    UNKNOWN(ServerCategory.UNKNOWN, NONE);

    public final ServerCategory category;
    public final ServerGroup group;
    public final String registeredName;
    private final Set<ServerCategory> categories;

    NetworkServer(final ServerCategory category, final ServerGroup group, final Set<ServerCategory> extraCategories) {
        this.category = category;
        this.group = group;
        this.registeredName = name().toLowerCase().replace("_", "");
        categories = EnumSet.of(category);
        categories.addAll(extraCategories);
    }

    NetworkServer(final ServerCategory category, final ServerGroup group) {
        this(category, group, Set.of());
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

    public boolean isOnline() {
        return Connect.get().serverIsOnline(registeredName);
    }

    public boolean isSurvival() {
        return categories.contains(ServerCategory.SURVIVAL);
    }

    public boolean isHome() {
        return categories.contains(ServerCategory.HOME);
    }

    public boolean isMining() {
        return categories.contains(ServerCategory.MINING);
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
