package com.cavetale.core.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public final class Chat {
    public static void sendAndLog(Player player, Component message) {
        ChatHandler.Companion.chatHandler.sendAndLog(player, message);
    }

    public static void sendNoLog(Player player, Component message) {
        ChatHandler.Companion.chatHandler.sendNoLog(player, message);
    }

    private Chat() { }
}
