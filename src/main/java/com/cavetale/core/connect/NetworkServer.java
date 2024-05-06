package com.cavetale.core.connect;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

/**
 * Enumerate each server connecting within the Cavetale network.
 */
@Getter
public enum NetworkServer {
    // Technical servers
    VOID(ServerCategory.TECHNICAL, ServerGroup.MAIN, FolderLocation.BASE),
    WEB(ServerCategory.TECHNICAL, ServerGroup.MAIN, FolderLocation.BASE),

    // Additional survival servers
    HUB(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE),
    CAVETALE(ServerCategory.SURVIVAL, ServerGroup.MUSEUM, FolderLocation.BASE),
    MINE(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE, Set.of(ServerCategory.MINING)),
    RAID(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.MINI),
    MOB_ARENA(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.MINI, Set.of(ServerCategory.MINIGAME)),

    // The two multi purpose servers
    FESTIVAL(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE),
    CHALLENGE(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.BASE),

    // The main survival servers
    EINS(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE, Set.of(ServerCategory.HOME)),
    ZWEI(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE, Set.of(ServerCategory.HOME)),
    DREI(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE, Set.of(ServerCategory.HOME)),
    VIER(ServerCategory.SURVIVAL, ServerGroup.MAIN, FolderLocation.BASE, Set.of(ServerCategory.HOME)),

    // Creative
    CREATIVE(ServerCategory.CREATIVE, ServerGroup.MAIN, FolderLocation.BASE),

    // Testing servers
    ALPHA(ServerCategory.SURVIVAL_TEST, ServerGroup.TESTING, FolderLocation.BASE),
    BETA(ServerCategory.SURVIVAL_TEST, ServerGroup.TESTING, FolderLocation.BASE),

    // Minigames, joinable any time
    BINGO(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    COLORFALL(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    HIDE_AND_SEEK(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    PVP_ARENA(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    RED_GREEN_LIGHT(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    SKYBLOCK(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    SPLEEF(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    SURVIVAL_GAMES(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    TETRIS(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),
    VERTIGO(ServerCategory.MINIGAME, ServerGroup.MAIN, FolderLocation.MINI),

    // Event games, only open during events
    ADVENTURE(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),
    CAPTURE_THE_FLAG(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),
    ENDERBALL(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),
    OVERBOARD(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),
    PIT_OF_DOOM(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),
    RACE(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),
    WINDICATOR(ServerCategory.EVENT, ServerGroup.MAIN, FolderLocation.MINI),

    // Hard to categorize and literally uncategorized
    WORLDGEN(ServerCategory.WORLD_GENERATION, ServerGroup.NONE, FolderLocation.BASE),
    UNKNOWN(ServerCategory.UNKNOWN, ServerGroup.NONE, FolderLocation.NONE),
    ;

    public enum FolderLocation {
        NONE,
        BASE,
        MINI;
    }

    public final ServerCategory category;
    private final FolderLocation folderLocation;
    public final ServerGroup group;
    public final String registeredName;
    private final Set<ServerCategory> categories;

    NetworkServer(final ServerCategory category, final ServerGroup group, final FolderLocation folderLocation, final Set<ServerCategory> extraCategories) {
        this.category = category;
        this.folderLocation = folderLocation;
        this.group = group;
        this.registeredName = name().toLowerCase().replace("_", "");
        categories = EnumSet.of(category);
        categories.addAll(extraCategories);
    }

    NetworkServer(final ServerCategory category, final ServerGroup group, final FolderLocation folderLocation) {
        this(category, group, folderLocation, Set.of());
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
        default: return HUB;
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
        return categories.contains(ServerCategory.SURVIVAL)
            || categories.contains(ServerCategory.SURVIVAL_TEST);
    }

    public boolean isHome() {
        return categories.contains(ServerCategory.HOME);
    }

    public boolean isMining() {
        return categories.contains(ServerCategory.MINING);
    }

    public boolean isMinigame() {
        return categories.contains(ServerCategory.MINIGAME);
    }

    public boolean isEvent() {
        return categories.contains(ServerCategory.EVENT);
    }

    @Deprecated
    public static NetworkServer manager() {
        return current().getManager();
    }

    @Deprecated
    public static NetworkServer mining() {
        return current().getMining();
    }

    public boolean isThisServer() {
        return current() == this;
    }
}
