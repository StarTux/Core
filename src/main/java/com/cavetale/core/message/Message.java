package com.cavetale.core.message;

import com.cavetale.core.command.RemotePlayer;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.JoinConfiguration;
import org.bukkit.entity.Player;
import static com.cavetale.core.resourcepack.ServerResourcePack.hasServerServerResourcePack;
import static net.kyori.adventure.text.Component.join;

/**
 * Utility class to send messages with possible alt text.
 */
public final class Message {
    public static Component buildWithAltText(JoinConfiguration joinConfiguration, Iterable<? extends ComponentLike> components) {
        List<Component> list = new ArrayList<>();
        for (ComponentLike like : components) {
            list.add(like instanceof AltTextSupplier altTextSupplier
                     ? altTextSupplier.getAltText()
                     : like.asComponent());
        }
        return join(joinConfiguration, list);
    }

    public static Component buildFor(Audience target, JoinConfiguration joinConfiguration, Iterable<? extends ComponentLike> components) {
        if (target instanceof Player player) {
            return hasServerServerResourcePack(player)
                ? join(joinConfiguration, components)
                : buildWithAltText(joinConfiguration, components);
        } else if (target instanceof RemotePlayer player) {
            return hasServerServerResourcePack(player)
                ? join(joinConfiguration, components)
                : buildWithAltText(joinConfiguration, components);
        } else {
            return buildWithAltText(joinConfiguration, components);
        }
    }

    public static void send(Audience target, JoinConfiguration joinConfiguration, Iterable<? extends ComponentLike> components) {
        target.sendMessage(buildFor(target, joinConfiguration, components));
    }

    private Message() { }
}
