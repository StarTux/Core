package com.cavetale.core.chat;

import com.cavetale.core.CorePlugin;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface ChatHandler {
    ChatHandler DEFAULT = new ChatHandler() {
            @Override
            public void sendAndLog(Player player, Component message) {
                player.sendMessage(message);
            }

            @Override
            public void sendNoLog(Player player, Component message) {
                player.sendMessage(message);
            }

            @Override
            public boolean doesIgnore(UUID ignorer, UUID ignoree) {
                return false;
            }

            @Override
            public JavaPlugin getPlugin() {
                return CorePlugin.plugin();
            }
        };

    void sendAndLog(Player player, Component message);

    void sendNoLog(Player player, Component message);

    boolean doesIgnore(UUID ignorer, UUID ignoree);

    JavaPlugin getPlugin();

    default void register() {
        Companion.chatHandler = this;
        CorePlugin.plugin().getLogger().info("ChatHandler registered: " + getClass().getName());
    }

    final class Companion {
        private Companion() { };

        protected static ChatHandler chatHandler = ChatHandler.DEFAULT;
    }
}
