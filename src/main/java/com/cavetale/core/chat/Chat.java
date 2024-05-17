package com.cavetale.core.chat;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
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

    public static void getChannelLog(String channelName, Instant from, Instant to, Consumer<List<ChannelChatEvent>> callback) {
        ChatHandler.Companion.chatHandler.getChannelLog(channelName, from, to, callback);
    }

    public static void getChannelLog(String channelName, Instant since, Consumer<List<ChannelChatEvent>> callback) {
        ChatHandler.Companion.chatHandler.getChannelLog(channelName, since, callback);
    }

    private Chat() { }
}
