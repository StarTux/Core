package com.cavetale.core.bungee;

import com.cavetale.core.CorePlugin;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class Bungee {
    public static void enable() {
        Bukkit.getMessenger().registerOutgoingPluginChannel(CorePlugin.getInstance(), "BungeeCord");
    }

    public static void send(Player player, String serverName) {
        final byte[] pluginMessage;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(serverName);
            pluginMessage = byteArrayOutputStream.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        player.sendPluginMessage(CorePlugin.getInstance(), "BungeeCord", pluginMessage);
    }

    private Bungee() { }
}
