package com.cavetale.core.event.player;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.connect.Connect;
import com.cavetale.core.util.Json;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * A generic event meant for inter-plugin communication.  The primary
 * intention here is to send messages from anywhere to a tutorial or
 * quests plugin, without having to define many different event types,
 * and especially without creating myriad complicated inter-plugin
 * dependencies.
 *
 * Each event object contains a reference to the issuing plugin.
 *
 * <code>
 * PluginPlayerEvent.Name.SET_HOME.make(plugin, player)
 *   .detail(Detail.HOME_NAME, homeName)
 *   .callEvent();
 * </code>
 */
@Getter @RequiredArgsConstructor
public final class PluginPlayerEvent extends Event {
    @NonNull private final Plugin plugin;
    @NonNull private final Player player;
    @NonNull private final Name name;
    @NonNull private final Map<Detail, Object> details = new HashMap<>();

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

    public static void call(Plugin plugin, Player player, Name name) {
        make(plugin, player, name).callEvent();
    }

    @Value
    public static final class Payload {
        public static final String CHANNEL = "core:plugin_player_event";
        public final String pluginName;
        public final UUID uuid;
        public final Name name;

        public boolean callEvent() {
            if (name == null) return false;
            Player thePlayer = Bukkit.getPlayer(uuid);
            if (thePlayer == null) return false;
            Plugin thePlugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (thePlugin == null) thePlugin = CorePlugin.getInstance();
            PluginPlayerEvent.call(thePlugin, thePlayer, name);
            return true;
        }
    }

    public static void broadcast(Plugin plugin, UUID uuid, Name name) {
        Connect.get().broadcastMessageToAll(Payload.CHANNEL, Json.serialize(new Payload(plugin.getName(), uuid, name)));
    }

    public static PluginPlayerEvent make(Plugin plugin, Player player, Name name) {
        return new PluginPlayerEvent(plugin, player, name);
    }

    public <E> PluginPlayerEvent detail(@NonNull Detail<E> detail, E value) {
        if (value == null) {
            details.remove(detail);
        } else {
            details.put(detail, value);
        }
        return this;
    }

    public <E> E getDetail(@NonNull Detail<E> detail, E defaultValue) {
        Object object = details.get(detail);
        return detail.valueType.isInstance(object) ? detail.valueType.cast(object) : defaultValue;
    }

    public <E> boolean isDetail(@NonNull Detail<E> detail, @NonNull E value) {
        return Objects.equals(details.get(detail), value);
    }

    public enum Name {
        // Claims
        USE_WILD,
        USE_WILD_WITH_CLAIM,
        CREATE_CLAIM,
        BUY_CLAIM_BLOCKS,
        GROW_CLAIM,
        VIEW_CLAIM_INFO,
        VIEW_CLAIM_SETTINGS,
        CHANGE_CLAIM_SETTING,
        CLAIM_TRUST,
        CLAIM_UNTRUST,
        CREATE_SUBCLAIM,
        SUBCLAIM_TRUST,
        SUBCLAIM_UNTRUST,
        // Homes
        SET_PRIMARY_HOME,
        SET_NAMED_HOME,
        USE_PRIMARY_HOME,
        USE_NAMED_HOME,
        VISIT_HOME,
        VISIT_PUBLIC_HOME,
        VIEW_PUBLIC_HOMES,
        INVITE_HOME,
        UNINVITE_HOME,
        LIST_HOMES,
        DELETE_HOME,
        // Warps
        LIST_WARPS,
        USE_WARP,
        USE_SPAWN,
        OPEN_STASH,
        // Chat
        FOCUS_CHAT_CHANNEL,
        OPEN_CHAT_SETTINGS,
        USE_CHAT_CHANNEL,
        LIST_CHAT_CHANNELS,
        JOIN_CHAT_PARTY,
        USE_CHAT_PARTY,
        USE_CHAT_TEAM,
        USE_PRIVATE_CHAT,
        USE_PRIVATE_CHAT_REPLY,
        FOCUS_PRIVATE_CHAT,
        // Server
        VIEW_SERVER_LIST,
        SWITCH_SERVER,
        // Mine
        USE_MINE,
        DUNGEON_LOOT,
        // Menu
        OPEN_MENU,
        MENU_GO_BACK,
        // Mass Storage
        OPEN_MASS_STORAGE,
        STORE_MASS_STORAGE,
        SEARCH_MASS_STORAGE,
        FIND_MASS_STORAGE,
        MASS_STORAGE_GO_BACK,
        // Sell
        SELL_ITEM,
        // Fam
        VIEW_PROFILE,
        VIEW_FRIENDS_LIST,
        ENTER_BIRTHDAY,
        SHARE_FRIENDSHIP_ITEM,
        MAKE_FRIEND,
        // Shop
        SHOP_SEARCH,
        SHOP_SEARCH_PORT,
        MAKE_SHOP_CHEST,
        // Money
        USE_MONEY,
        // Title
        LIST_PLAYER_TITLES,
        SELECT_PLAYER_TITLE,
        // Mail
        READ_MAIL,
        SEND_MAIL,
        // Televator
        RIDE_TELEVATOR,
        // Pocket Mob
        POCKET_MOB_CATCH,
        POCKET_MOB_RELEASE,
        // Link Portal
        MAKE_LINK_PORTAL,
        LINK_PORTAL_TRAVEL,
        // Kit
        KIT_OPEN,
        // NPC
        INTERACT_NPC,
        // Raid
        RAID_START,
        RAID_VICTORY,
        // Minigame
        MINIGAME_PLAY_GAME,
        MINIGAME_WIN_GAME,
        // Mytems
        PLAY_NOTE,
        // Generic
        START_FLYING,
        PLAYER_SESSION_LOADED;

        public void call(Plugin thePlugin, Player thePlayer) {
            PluginPlayerEvent.call(thePlugin, thePlayer, this);
        }

        public PluginPlayerEvent make(Plugin thePlugin, Player thePlayer) {
            return PluginPlayerEvent.make(thePlugin, thePlayer, this);
        }

        public void broadcast(Plugin thePlugin, UUID uuid) {
            PluginPlayerEvent.broadcast(thePlugin, uuid, this);
        }
    }

    public static final class Detail<E> {
        public static final Detail<Block> BLOCK = new Detail<>("block", Block.class);
        public static final Detail<BlockFace> DIRECTION = new Detail<>("direction", BlockFace.class);
        public static final Detail<Boolean> TOGGLE = new Detail<>("toggle", Boolean.class);
        public static final Detail<Double> MONEY = new Detail<>("money", Double.class);
        public static final Detail<Entity> ENTITY = new Detail<>("entity", Entity.class);
        public static final Detail<Instrument> INSTRUMENT = new Detail<>("instrument", Instrument.class);
        public static final Detail<Integer> COUNT = new Detail<>("count", Integer.class);
        public static final Detail<Integer> INDEX = new Detail<>("index", Integer.class);
        public static final Detail<ItemStack> ITEM = new Detail<>("item", ItemStack.class);
        public static final Detail<Location> LOCATION = new Detail<>("location", Location.class);
        public static final Detail<Material> MATERIAL = new Detail<>("material", Material.class);
        public static final Detail<Note> NOTE = new Detail<>("note", Note.class);
        public static final Detail<String> NAME = new Detail<>("name", String.class);
        public static final Detail<UUID> OWNER = new Detail<>("owner", UUID.class);
        public static final Detail<UUID> TARGET = new Detail<>("target", UUID.class);

        public final String key;
        public final Class<E> valueType;

        public Detail(final String key, final Class<E> valueType) {
            this.key = key;
            this.valueType = valueType;
        }

        public void set(PluginPlayerEvent event, E value) {
            event.detail(this, value);
        }

        public E get(PluginPlayerEvent event, E defaultValue) {
            return event.getDetail(this, defaultValue);
        }

        public boolean is(PluginPlayerEvent event, E value) {
            return event.isDetail(this, value);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Detail detail)) return false;
            return key.equals(detail.key);
        }
    }
}
