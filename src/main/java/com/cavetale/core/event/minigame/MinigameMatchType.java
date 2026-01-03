package com.cavetale.core.event.minigame;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Minigames with limited scope matches and one or more winners.
 */
@Getter @RequiredArgsConstructor
public enum MinigameMatchType {
    // Public
    BINGO(true, "Bingo"),
    BUILD_MY_THING(true, "Build my Thing"),
    CAPTURE_THE_FLAG(true, "Capture the Flag"),
    CAVEPAINT(true, "Cavepaint"),
    CHESS(true, "Chess"),
    COLORFALL(true, "Colorfall"),
    ENDERGOLF(true, "Endergolf"),
    EXTREME_GRASS_GROWING(true, "Extreme Grass Growing"),
    HIDE_AND_SEEK(true, "Hide and Seek"),
    KING_OF_THE_LADDER(true, "King of the Ladder"),
    PVP_ARENA(true, "PvP Arena"),
    RACE(true, "Race"),
    SPLEEF(true, "Spleef"),
    SURVIVAL_GAMES(true, "Survival Games"),
    TETRIS(true, "Tetris"),
    VERTIGO(true, "Vertigo"),
    ENDERBALL(true, "Enderball"),
    // Not public
    OVERBOARD(false, "Overboard"),
    PIT_OF_DOOM(false, "Pit of Doom"),
    ;

    public final boolean publiclyAvailable;
    public final String displayName;
}
