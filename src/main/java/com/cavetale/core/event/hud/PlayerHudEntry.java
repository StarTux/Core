package com.cavetale.core.event.hud;

import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;

@Getter @RequiredArgsConstructor
public final class PlayerHudEntry implements Comparable<PlayerHudEntry> {
    private final int priority;
    @NonNull private final List<Component> lines;

    @Override
    public int compareTo(PlayerHudEntry other) {
        return Integer.compare(priority, other.priority);
    }

    public int getLineCount() {
        return lines.size();
    }
}
