package com.cavetale.core.event.hud;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class PlayerHudEvent extends Event {
    @NonNull private final Player player;
    private final List<PlayerHudEntry> sidebar = new ArrayList<>();
    private final List<PlayerBossBarEntry> bossbar = new ArrayList<>();
    private final List<PlayerHudEntry> header = new ArrayList<>();
    private final List<PlayerHudEntry> footer = new ArrayList<>();

    public void sidebar(@NonNull PlayerHudPriority priority, @NonNull List<Component> lines) {
        if (lines.isEmpty()) return;
        sidebar.add(new PlayerHudEntry(priority.value, lines));
    }

    public void header(@NonNull PlayerHudPriority priority, @NonNull List<Component> lines) {
        if (lines.isEmpty()) return;
        header.add(new PlayerHudEntry(priority.value, lines));
    }

    public void footer(@NonNull PlayerHudPriority priority, @NonNull List<Component> lines) {
        if (lines.isEmpty()) return;
        footer.add(new PlayerHudEntry(priority.value, lines));
    }

    public void bossbar(@NonNull PlayerHudPriority priority, @NonNull Component title,
                        @NonNull BossBar.Color color, @NonNull BossBar.Overlay overlay,
                        @NonNull Set<BossBar.Flag> flags, float progress) {
        bossbar.add(new PlayerBossBarEntry(priority.value, title, color, overlay, flags, progress));
    }

    public void bossbar(PlayerHudPriority priority, Component title, BossBar.Color color, BossBar.Overlay overlay, float progress) {
        bossbar(priority, title, color, overlay, Set.of(), progress);
    }

    public void bossbar(PlayerHudPriority priority, BossBar copy) {
        bossbar(priority, copy.name(), copy.color(), copy.overlay(), copy.flags(), copy.progress());
    }

    /**
     * Required by Event.
     */
    @Getter @SuppressWarnings("ConstantName")
    private static final HandlerList handlerList  = new HandlerList();

    /**
     * Required by Event.
     */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
