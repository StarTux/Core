package com.cavetale.core.event.minigame;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Minigames with limited scope matches and one or more winners.
 */
@Getter @RequiredArgsConstructor
public enum MinigameMatchType {
    // Public
    COLORFALL(true, "Colorfall"),
    EXTREME_GRASS_GROWING(true, "Extreme Grass Growing"),
    HIDE_AND_SEEK(true, "Hide and Seek"),
    PVP_ARENA(true, "PvP Arena"),
    TETRIS(true, "Tetris"),
    VERTIGO(true, "Vertigo"),
    SPLEEF(true, "Spleef"),
    // Not public
    BINGO(false, "Bingo"),
    ENDERBALL(false, "Enderball"),
    OVERBOARD(false, "Overboard"),
    RACE(false, "Race"),
    SURVIVAL_GAMES(false, "Survival Games"),
    WINDICATOR(false, "Windicator"),
    CAPTURE_THE_FLAG(false, "Capture the Flag"),
    ;

    public final boolean publiclyAvailable;
    public final String displayName;
}
