package com.cavetale.core.back;

import com.cavetale.core.CorePlugin;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.plugin.Plugin;

/**
 * A BackProvider is the backend to load and store back locations,
 * broadcast requests and replies.
 * The Server plugin will likely implement this.
 */
public interface BackProvider {
    default void register() {
        Back.provider = this;
        CorePlugin.getInstance().getLogger().info("BackProvider registered: " + getClass().getName());
    }

    default void unregister() {
        Back.provider = null;
    }

    /**
     * Store the BackLocation in the backing storage which is likely
     * SQL.
     */
    void store(BackLocation backLocation);

    /**
     * Delete the BackLocation of the given player from the backing
     * storage.
     */
    void reset(UUID playerUuid);

    /**
     * Delete the BackLocation of the given player from the backing
     * storage, provided it belongs to the given plugin.
     */
    void reset(UUID playerUuid, Plugin plugin);

    /**
     * Delete all BackLocations provided they belong to the given
     * plugin.
     */
    void resetAll(Plugin plugin);

    /**
     * Load the BackLocation from the backing storage and give the
     * result to a callback.
     */
    void load(UUID playerUuid, Consumer<BackLocation> callback);

    /**
     * Attempt to send a player back.  The provider must call
     * PlayerBackEvent on the target server and respect its
     * cancellation state.
     * If successful, the back location shall be reset.
     */
    void back(UUID playerUuid, Consumer<BackLocation> callback);

    /**
     * Same as above, but only take action if the back server was also
     * the logout server.
     */
    void backAuto(UUID playerUuid, Consumer<BackLocation> callback);
}
