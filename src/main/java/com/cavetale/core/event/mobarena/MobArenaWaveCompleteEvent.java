package com.cavetale.core.event.mobarena;

import com.cavetale.core.event.minigame.MinigameFlag;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor @ToString
public final class MobArenaWaveCompleteEvent extends Event {
    private final int wave;
    private final Set<MinigameFlag> flags = EnumSet.noneOf(MinigameFlag.class);
    private final Set<UUID> playerUuids = new HashSet<>();

    public void addFlags(MinigameFlag... theFlags) {
        for (var it : theFlags) flags.add(it);
    }

    public void addPlayer(Player p) {
        playerUuids.add(p.getUniqueId());
    }

    public void addPlayers(Iterable<Player> iter) {
        for (var it : iter) playerUuids.add(it.getUniqueId());
    }

    public void addPlayerUuid(UUID uuid) {
        playerUuids.add(uuid);
    }

    public void addPlayerUuids(Iterable<UUID> iter) {
        for (var it : iter) playerUuids.add(it);
    }

    public List<Player> getPlayers() {
        return playerUuids.stream()
            .map(Bukkit::getPlayer)
            .filter(Objects::nonNull)
            .toList();
    }

    /**
     * Required by Event.
     */
    @Getter private static HandlerList handlerList = new HandlerList();

    /**
     * Required by Event.
     */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
