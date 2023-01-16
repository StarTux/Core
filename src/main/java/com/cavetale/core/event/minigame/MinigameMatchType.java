package com.cavetale.core.event.minigame;

import lombok.RequiredArgsConstructor;

/**
 * Minigames with limited scope matches and one or more winners.
 */
@RequiredArgsConstructor
public enum MinigameMatchType {
    // Public
    COLORFALL(true),
    EXTREME_GRASS_GROWING(true),
    HIDE_AND_SEEK(true),
    PVP_ARENA(true),
    TETRIS(true),
    VERTIGO(true),
    // Not public
    BINGO(false),
    ENDERBALL(false),
    OVERBOARD(false),
    RACE(false),
    SPLEEF(false),
    SURVIVAL_GAMES(false),
    WINDICATOR(false),
    ;

    public final boolean publiclyAvailable;
}
