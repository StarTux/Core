package com.cavetale.core.event.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * A generic event meant for inter-plugin communication. The primary
 * intention here is to send messages from anywhere to a tutorial or
 * quests plugin, without having to define many different event types,
 * and especially without creating myriad complicated inter-plugin
 * dependencies.
 *
 * Each event object contains a reference to the issuing plugin. The
 * eventName should be a snake case descriptive name of what happened,
 * without the name of the plugin. Event objects may be defined as
 * cancellable or ultimate (uncancellable). We expect most events to
 * not be cancellable.
 *
 * <code>
 * PluginPlayerEvent.ultimate(plugin, player, "set_home")
 *   .detail("home_name", homeName)
 *   .call();
 * // OR
 * PluginPlayerEvent.Name.SET_HOME.ultimate(plugin, player)
 *   .detail(Detail.HOME_NAME, homeName)
 *   .call();
 * </code>
 *
 * In the future we may add an enum with a collection of event names
 * and detail names for increased compile-time safety.
 */
@Getter @RequiredArgsConstructor
public final class PluginPlayerEvent extends Event implements Cancellable {
    @NonNull private final Plugin plugin;
    @NonNull private final Player player;
    @NonNull private final String eventName;
    @NonNull private final Map<String, Object> details = new HashMap<>();
    private final boolean cancellable;
    private boolean cancelled;

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

    public static PluginPlayerEvent ultimate(Plugin plugin, Player player, String eventName) {
        return new PluginPlayerEvent(plugin, player, eventName, false);
    }

    public static PluginPlayerEvent cancellable(Plugin plugin, Player player, String eventName) {
        return new PluginPlayerEvent(plugin, player, eventName, true);
    }

    public static void call(Plugin plugin, Player player, String eventName) {
        ultimate(plugin, player, eventName).call();
    }

    public PluginPlayerEvent detail(@NonNull String key, Object value) {
        if (value == null) {
            details.remove(key);
        } else {
            details.put(key, value);
        }
        return this;
    }

    public <E> PluginPlayerEvent detail(@NonNull Detail<E> detail, E value) {
        return detail(detail.key, value);
    }

    public <E> E getDetail(@NonNull String key, Class<E> clazz, E defaultValue) {
        Object object = details.get(key);
        return clazz.isInstance(object) ? clazz.cast(object) : defaultValue;
    }

    public <E> E getDetail(@NonNull Detail<E> detail, E defaultValue) {
        return getDetail(detail.key, detail.valueType, defaultValue);
    }

    public boolean isDetail(@NonNull String key, @NonNull Object value) {
        return Objects.equals(details.get(key), value);
    }

    public <E> boolean isDetail(@NonNull Detail<E> detail, @NonNull E value) {
        return Objects.equals(details.get(detail.key), value);
    }


    public boolean call() {
        Bukkit.getPluginManager().callEvent(this);
        return !cancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        if (!cancellable) {
            throw new IllegalArgumentException("event is not cancellable!");
        }
        this.cancelled = isCancelled;
    }

    public String getPluginName() {
        return plugin.getName();
    }

    @NonNull public Name parseName() {
        try {
            return Name.valueOf(eventName.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return Name.UNKNOWN;
        }
    }

    public enum Name {
        USE_WILD,
        SET_PRIMARY_HOME,
        SET_NAMED_HOME,
        USE_PRIMARY_HOME,
        USE_NAMED_HOME,
        VISIT_HOME,
        VISIT_PUBLIC_HOME,
        VIEW_PUBLIC_HOMES,
        LIST_WARPS,
        USE_WARP,
        CREATE_CLAIM,
        USE_SPAWN,
        OPEN_STASH,
        FOCUS_CHAT_CHANNEL,
        OPEN_CHAT_SETTINGS,
        USE_CHAT_CHANNEL,
        LIST_CHAT_CHANNELS,
        SWITCH_SERVER,
        USE_MINE,
        DUNGEON_LOOT,
        OPEN_MENU,
        UNKNOWN;

        public final String key;

        Name() {
            this.key = name().toLowerCase();
        }

        public PluginPlayerEvent ultimate(Plugin thePlugin, Player thePlayer) {
            return PluginPlayerEvent.ultimate(thePlugin, thePlayer, key);
        }

        public PluginPlayerEvent cancellable(Plugin thePlugin, Player thePlayer) {
            return PluginPlayerEvent.cancellable(thePlugin, thePlayer, key);
        }

        public void call(Plugin thePlugin, Player thePlayer) {
            PluginPlayerEvent.call(thePlugin, thePlayer, key);
        }
    }

    @RequiredArgsConstructor
    public static final class Detail<E> {
        public static final Detail<Block> BLOCK = new Detail<>("block", Block.class);
        public static final Detail<Entity> ENTITY = new Detail<>("entity", Entity.class);
        public static final Detail<Integer> INDEX = new Detail<>("index", Integer.class);
        public static final Detail<ItemStack> ITEM = new Detail<>("item", ItemStack.class);
        public static final Detail<Location> LOCATION = new Detail<>("location", Location.class);
        public static final Detail<Material> MATERIAL = new Detail<>("material", Material.class);
        public static final Detail<String> NAME = new Detail<>("name", String.class);
        public static final Detail<UUID> OWNER = new Detail<>("owner", UUID.class);

        public final String key;
        public final Class<E> valueType;

        public void set(PluginPlayerEvent event, E value) {
            event.detail(this, value);
        }

        public E get(PluginPlayerEvent event, E defaultValue) {
            return event.getDetail(this, defaultValue);
        }

        public boolean is(PluginPlayerEvent event, E value) {
            return event.isDetail(this, value);
        }
    }
}
