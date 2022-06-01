package com.cavetale.core.event.connect;

import com.cavetale.core.event.player.PluginPlayerEvent;
import com.cavetale.core.util.Json;
import java.util.Date;
import java.util.function.Supplier;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter @RequiredArgsConstructor
public final class ConnectMessageEvent extends Event {
    @NonNull private final String channel;
    @NonNull private final String payload;
    @NonNull private final String originServer;
    @NonNull private final String targetServer;
    @NonNull private final Date created;

    public <T> T getPayload(Class<T> type, Supplier<T> dfl) {
        return Json.deserialize(payload, type, dfl);
    }

    public <T> T getPayload(Class<T> type) {
        return Json.deserialize(payload, type);
    }

    @Override
    public boolean callEvent() {
        switch (channel) {
        case PluginPlayerEvent.Payload.CHANNEL: {
            PluginPlayerEvent.Payload pluginPlayerEventPayload = getPayload(PluginPlayerEvent.Payload.class);
            if (pluginPlayerEventPayload != null) {
                pluginPlayerEventPayload.callEvent();
            }
            break;
        }
        default: break;
        }
        return super.callEvent();
    }

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
}
