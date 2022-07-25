package com.cavetale.core.message;

import com.cavetale.core.command.RemotePlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import static com.cavetale.core.resourcepack.ServerResourcePack.hasServerServerResourcePack;

/**
 * An AltTextSupplier is a ComponentLike capable of providing an alt
 * text for players which do not have the Server Resource Pack loaded.
 *
 * An AltTextSupplier is not meant to be recursive.  It is not
 * supposed to redo the work already done by the Adventure framework.
 */
public interface AltTextSupplier extends ComponentLike {
    /**
     * The default implementation returns the regular component to
     * make mass implementation less cumbersome.
     */
    default Component getAltText() {
        return asComponent();
    }

    default Component forPlayer(Player player) {
        return hasServerServerResourcePack(player)
            ? asComponent()
            : getAltText();
    }

    default Component forRemotePlayer(RemotePlayer player) {
        return hasServerServerResourcePack(player)
            ? asComponent()
            : getAltText();
    }

    default Component forAudience(Audience audience) {
        if (audience instanceof Player player) {
            return forPlayer(player);
        } else if (audience instanceof RemotePlayer player) {
            return forRemotePlayer(player);
        } else {
            return getAltText();
        }
    }
}
