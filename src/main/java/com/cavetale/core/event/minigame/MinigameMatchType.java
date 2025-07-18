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
    CHESS(true, "Chess"),
    // Not public
    BINGO(false, "Bingo"),
    ENDERBALL(false, "Enderball"),
    OVERBOARD(false, "Overboard"),
    RACE(false, "Race"),
    SURVIVAL_GAMES(false, "Survival Games"),
    CAPTURE_THE_FLAG(false, "Capture the Flag"),
    CAVEPAINT(false, "Cavepaint"),
    KING_OF_THE_LADDER(false, "King of the Ladder"),
    BUILD_MY_THING(false, "Build my Thing"),
    ;

    public final boolean publiclyAvailable;
    public final String displayName;
}
