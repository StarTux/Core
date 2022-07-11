package com.cavetale.core.playercache;

import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandContext;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import java.util.List;
import java.util.UUID;
import lombok.Value;
import org.bukkit.OfflinePlayer;

@Value
public final class PlayerCache {
    protected static PlayerCacheDataSource dataSource = new DefaultPlayerCacheDataSource();
    public static final CommandArgCompleter NAME_COMPLETER = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return completeNames(arg);
            }
        };

    public final UUID uuid;
    public final String name;

    public UUID getUniqueId() {
        return uuid;
    }

    public static PlayerCache of(OfflinePlayer off) {
        return new PlayerCache(off.getUniqueId(), off.getName());
    }

    public static PlayerCache forUuid(UUID uuid) {
        return dataSource.forUuid(uuid);
    }

    public static PlayerCache forName(String name) {
        return dataSource.forName(name);
    }

    public static PlayerCache forArg(String arg) {
        return dataSource.forArg(arg);
    }

    public static String nameForUuid(UUID uuid) {
        PlayerCache player = forUuid(uuid);
        return player != null ? player.name : null;
    }

    public static UUID uuidForName(String name) {
        PlayerCache player = forName(name);
        return player != null ? player.uuid : null;
    }

    public static PlayerCache require(String in) {
        PlayerCache result = forArg(in);
        if (result == null) throw new CommandWarn("Player not found: " + in);
        return result;
    }

    public static PlayerCache requireForName(String in) {
        PlayerCache result = forName(in);
        if (result == null) throw new CommandWarn("Player not found: " + in);
        return result;
    }

    public static PlayerCache requireForUuid(UUID in) {
        PlayerCache result = forUuid(in);
        if (result == null) throw new CommandWarn("Player not found: " + in);
        return result;
    }

    public static List<String> completeNames(String arg) {
        return dataSource.completeNames(arg);
    }
}
