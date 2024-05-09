package com.cavetale.core.skin;

import com.cavetale.core.CorePlugin;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.plugin.Plugin;

public interface PlayerSkinProvider {
    Plugin getPlugin();

    default void register() {
        Companion.provider = this;
        CorePlugin.plugin().getLogger().info("PlayerSkinProvider registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.provider = Companion.DEFAULT;
        CorePlugin.plugin().getLogger().info("PlayerSkinProvider unregistered: " + getClass().getName());
    }

    PlayerSkin getPlayerSkin(UUID uuid);

    void getPlayerSkinAsync(UUID uuid, Consumer<PlayerSkin> callback);

    final class Companion {
        static final PlayerSkinProvider DEFAULT = new PlayerSkinProvider() {
                @Override
                public CorePlugin getPlugin() {
                    return CorePlugin.getInstance();
                }

                @Override
                public PlayerSkin getPlayerSkin(UUID uuid) {
                    return null;
                }

                @Override
                public void getPlayerSkinAsync(UUID uuid, Consumer<PlayerSkin> callback) {
                    callback.accept(null);
                }
            };

        protected static PlayerSkinProvider provider = DEFAULT;

        private Companion() { };
    }
}
