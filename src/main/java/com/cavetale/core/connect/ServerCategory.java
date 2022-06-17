package com.cavetale.core.connect;

public enum ServerCategory {
    SURVIVAL,
    SURVIVAL_TEST,
    CREATIVE,
    MINIGAME,
    EVENT,
    WORLD_GENERATION,
    UNKNOWN;

    public boolean isSurvival() {
        return this == SURVIVAL
            || this == SURVIVAL_TEST;
    }

    public static ServerCategory current() {
        return NetworkServer.current().category;
    }
}
