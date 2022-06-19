package com.cavetale.core.event.hud;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlayerHudPriority {
    HIGHEST(0),
    HIGH(200),
    DEFAULT(400),
    LOW(600),
    LOWEST(1000),

    NOTICE(100),
    GAME(300),
    REMINDER(500),
    UPDATE(700),
    INFO(900),
    ;

    public final int value;
}
