package com.cavetale.core.chat;

import com.cavetale.core.connect.NetworkServer;
import java.util.UUID;
import lombok.Value;
import net.kyori.adventure.text.Component;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a message is sent to any chat channel, on any server.
 */
@Value
public final class ChannelChatEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final UUID sender;
    private final UUID recipient;
    private final NetworkServer server;
    private final String channelName;
    private final String rawMessage;
    private final Component message;

    /**
     * Required by Event.
     */
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    /**
     * Required by Event.
     */
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
