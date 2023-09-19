package com.cavetale.core.event.minigame;

import lombok.RequiredArgsConstructor;

/**
 * Minigames with limited scope matches and one or more winners.
 */
@RequiredArgsConstructor
public enum MinigameMatchType {
    // Public
    COLORFALL(true, "Colorfall"),
    EXTREME_GRASS_GROWING(true, "Extreme Grass Growing"),
    HIDE_AND_SEEK(true, "Hide and Seek"),
    PVP_ARENA(true, "PvP Arena"),
    TETRIS(true, "Tetris"),
    VERTIGO(true, "Vertigo"),
    // Not public
    BINGO(false, "Bingo"),
    ENDERBALL(false, "Enderball"),
    OVERBOARD(false, "Overboard"),
    RACE(false, "Race"),
    SPLEEF(false, "Spleef"),
    SURVIVAL_GAMES(false, "Survival Games"),
    WINDICATOR(false, "Windicator"),
    ;

    public final boolean publiclyAvailable;
    public final String displayName;
}
