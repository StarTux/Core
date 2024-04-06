package com.cavetale.core.chat;

import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class Chat {
    public static void sendAndLog(Player player, Component message) {
        ChatHandler.Companion.chatHandler.sendAndLog(player, message);
    }

    public static void sendNoLog(Player player, Component message) {
        ChatHandler.Companion.chatHandler.sendNoLog(player, message);
    }

    public static boolean doesIgnore(UUID ignorer, UUID ignoree) {
        return ChatHandler.Companion.chatHandler.doesIgnore(ignorer, ignoree);
    }

    private Chat() { }
}
