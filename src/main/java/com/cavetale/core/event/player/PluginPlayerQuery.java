package com.cavetale.core.event.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

/**
 */
@Getter @RequiredArgsConstructor
public final class PluginPlayerQuery extends Event {
    @NonNull private final Plugin plugin;
    @NonNull private final Player player;
    @NonNull private final String queryName;
    private final List<Response> responses = new ArrayList<>();

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

    public static <T> T call(@NonNull Plugin plugin, @NonNull Player player, String queryName, Class<T> responseType) {
        PluginPlayerQuery event;
        event = new PluginPlayerQuery(plugin, player, queryName);
        Bukkit.getPluginManager().callEvent(event);
        for (Response response : event.responses) {
            if (responseType.isInstance(response.value)) {
                return responseType.cast(response.value);
            }
        }
        return null;
    }

    public static PluginPlayerQuery prep(@NonNull Plugin plugin, @NonNull Player player, String queryName) {
        PluginPlayerQuery event;
        event = new PluginPlayerQuery(plugin, player, queryName);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    public String getPluginName() {
        return plugin.getName();
    }

    @NonNull public Name parseName() {
        Name result = Name.of(queryName);
        return result != null ? result : Name.UNKNOWN;
    }

    public void respond(@NonNull Plugin responder, @NonNull Object value) {
        responses.add(new Response(responder, value));
        Name name = Name.of(queryName);
        if (name != null && !name.responseType.isInstance(value)) {
            plugin.getLogger().warning("PlayerPluginQuery::respond: Invalid response type!"
                                       + " name=" + name.key + "(" + name.responseType.getSimpleName() + ")"
                                       + " plugin=" + responder.getName()
                                       + " value=" + value);
        }
    }

    public static final class Name<T> {
        private static final Map<String, Name> KEY_MAP = new HashMap<>();
        public static final Name<Void> UNKNOWN = new Name<>("unknown", Void.class);
        public static final Name<Integer> CLAIM_COUNT = new Name<>("claim_count", Integer.class);
        public static final Name<Boolean> INSIDE_TRUSTED_CLAIM = new Name<>("inside_trusted_claim", Boolean.class);
        public static final Name<Boolean> PRIMARY_HOME_IS_SET = new Name<>("primary_home_is_set", Boolean.class);
        public static final Name<Integer> HOME_COUNT = new Name<>("home_count", Integer.class);

        public final String key;
        public final Class<T> responseType;

        private Name(final String key, final Class<T> responseType) {
            this.key = key;
            this.responseType = responseType;
            KEY_MAP.put(key, this);
        }

        public PluginPlayerQuery prep(Plugin thePlugin, Player thePlayer) {
            return PluginPlayerQuery.prep(thePlugin, thePlayer, key);
        }

        public T call(Plugin thePlugin, Player thePlayer) {
            return PluginPlayerQuery.call(thePlugin, thePlayer, key, responseType);
        }

        public T call(Plugin thePlugin, Player thePlayer, T dfl) {
            T result = call(thePlugin, thePlayer);
            return result != null ? result : dfl;
        }

        public void respond(PluginPlayerQuery query, Plugin responder, T value) {
            Name otherName = query.parseName();
            if (otherName != this) {
                throw new IllegalArgumentException(otherName.key + " != " + key);
            }
            query.respond(responder, value);
        }

        public static Name of(String key) {
            return KEY_MAP.get(key);
        }
    }

    @Value
    public static final class Response {
        public final Plugin responder;
        public final Object value;
    }
}
