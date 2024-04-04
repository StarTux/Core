package com.cavetale.core.bungee;

import com.cavetale.core.CorePlugin;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.gson;
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

    public static void kick(Player player, String message) {
        final byte[] pluginMessage;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
            dataOutputStream.writeUTF("KickPlayer");
            dataOutputStream.writeUTF(player.getName());
            dataOutputStream.writeUTF(message);
            pluginMessage = byteArrayOutputStream.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        player.sendPluginMessage(CorePlugin.getInstance(), "BungeeCord", pluginMessage);
    }

    public static void kickRaw(Player player, Component message) {
        final byte[] pluginMessage;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream)) {
            dataOutputStream.writeUTF("KickPlayerRaw");
            dataOutputStream.writeUTF(player.getName());
            dataOutputStream.writeUTF(gson().serialize(message));
            pluginMessage = byteArrayOutputStream.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }
        player.sendPluginMessage(CorePlugin.getInstance(), "BungeeCord", pluginMessage);
    }

    private Bungee() { }
}
