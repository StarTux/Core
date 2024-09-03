package com.cavetale.core.event.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public final class PlayerTeamQuery extends Event {
    /**
     * Required by Event.
     */
    @Getter private static HandlerList handlerList = new HandlerList();
    private final Map<UUID, Team> playerTeamMap = new HashMap<>();
    private final Map<String, Team> teamMap = new HashMap<>();

    @RequiredArgsConstructor @Getter
    public static final class Team {
        public final String key;
        public final Component displayName;
        public final TextColor textColor;

        @Deprecated
        public Team(final String key, final Component displayName) {
            this(key, displayName, null);
        }
    }

    /**
     * Required by Event.
     */
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public void setTeam(Player player, Team team) {
        playerTeamMap.put(player.getUniqueId(), team);
        teamMap.computeIfAbsent(team.key, k -> team);
    }

    public void setTeam(UUID uuid, Team team) {
        playerTeamMap.put(uuid, team);
        teamMap.computeIfAbsent(team.key, k -> team);
    }

    public Team getTeam(Player player) {
        return playerTeamMap.get(player.getUniqueId());
    }

    public Team getTeam(UUID uuid) {
        return playerTeamMap.get(uuid);
    }

    public Team getTeam(String key) {
        return teamMap.get(key);
    }

    public boolean hasTeams() {
        return !playerTeamMap.isEmpty();
    }
}
