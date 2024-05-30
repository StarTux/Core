package com.cavetale.core;

import com.cavetale.core.bungee.Bungee;
import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandNode;
import com.cavetale.core.command.CommandWarn;
import com.cavetale.core.event.block.PlayerBlockAbilityQuery;
import com.cavetale.core.event.block.PlayerBreakBlockEvent;
import com.cavetale.core.event.entity.PlayerEntityAbilityQuery;
import com.cavetale.core.exploits.PlayerPlacedBlocks;
import com.cavetale.core.font.Emoji;
import com.cavetale.core.font.Unicode;
import com.cavetale.core.font.VanillaItems;
import com.cavetale.core.item.ItemKinds;
import com.cavetale.core.playercache.PlayerCache;
import com.cavetale.core.struct.Cuboid;
import com.cavetale.core.text.LineWrap;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import static net.kyori.adventure.text.Component.text;

public final class CorePlugin extends JavaPlugin {
    @Getter private static CorePlugin instance;
    private CommandNode coreCommand;

    @Override
    public void onLoad() {
        instance = this;
        VanillaItems.initAll();
    }

    @Override
    public void onEnable() {
        VanillaItems.test();
        loadConfiguration();
        Emoji.init();
        Bukkit.getScheduler().runTask(this, () -> {
                getLogger().info(Emoji.count() + " emoji loaded");
            });
        Bungee.enable();
        coreCommand = new CommandNode("core");
        coreCommand.addChild("save")
            .denyTabCompletion()
            .description("Save config to disk")
            .senderCaller(this::saveCommand);
        coreCommand.addChild("reload")
            .denyTabCompletion()
            .description("Reload the config file")
            .senderCaller(this::reloadCommand);
        coreCommand.addChild("unicode")
            .denyTabCompletion()
            .description("Unicode test")
            .senderCaller((sender, a) -> {
                    if (a.length != 0) return false;
                    StringBuilder sb = new StringBuilder();
                    for (Unicode unicode : Unicode.values()) {
                        sb.append(unicode.character);
                    }
                    sender.sendMessage(sb.toString());
                    return true;
                });
        coreCommand.addChild("emoji")
            .denyTabCompletion()
            .description("Emoji dump")
            .senderCaller((sender, args) -> {
                    if (args.length != 0) return false;
                    Emoji.dump(sender);
                    return true;
                });
        coreCommand.addChild("subscript").arguments("<text>")
            .denyTabCompletion()
            .description("Convert to subscript")
            .senderCaller((sender, args) -> {
                    if (args.length == 0) return false;
                    String text = String.join(" ", args);
                    String sub = Unicode.subscript(text);
                    sender.sendMessage(text(sub).insertion(sub));
                    return true;
                });
        coreCommand.addChild("connect").denyTabCompletion()
            .denyTabCompletion()
            .description("Connect debugging")
            .senderCaller((sender, args) -> {
                    if (args.length != 0) return false;
                    sender.sendMessage("serverName=" + com.cavetale.core.connect.Connect.get().getServerName());
                    sender.sendMessage("server=" + com.cavetale.core.connect.NetworkServer.current());
                    sender.sendMessage("category=" + com.cavetale.core.connect.ServerCategory.current());
                    sender.sendMessage("group=" + com.cavetale.core.connect.ServerGroup.current());
                    return true;
                });
        coreCommand.addChild("spawnitem")
            .playerCaller((player, args) -> {
                    player.getInventory().addItem(ItemKinds.create(String.join(" ", args)));
                    return true;
                });
        coreCommand.addChild("showitem")
            .playerCaller(player -> {
                    player.sendMessage(ItemKinds.chatDescription(player.getInventory().getItemInMainHand()));
                });
        coreCommand.addChild("exploits")
            .playerCaller(player -> {
                    player.sendMessage("" + PlayerPlacedBlocks.isPlayerPlaced(player.getLocation().getBlock()));
                });
        coreCommand.addChild("playercache").arguments("<uuid|name>")
            .senderCaller((sender, args) -> {
                    if (args.length != 1) return false;
                    PlayerCache player = PlayerCache.forArg(args[0]);
                    sender.sendMessage("player=" + player);
                    return true;
                });
        coreCommand.addChild("selection").denyTabCompletion()
            .playerCaller((player) -> player.sendMessage("selection=" + Cuboid.selectionOf(player)));
        coreCommand.addChild("linewrap")
            .senderCaller((sender, args) -> {
                    for (var c : new LineWrap().wrap(String.join(" ", args))) {
                        sender.sendMessage(c);
                    }
                    return true;
                });
        coreCommand.addChild("debugplayerteleportutil")
            .description("Debug the player teleport util")
            .senderCaller(sender -> com.cavetale.core.command.PlayerTeleportUtil.debug(sender));
        coreCommand.addChild("kick").arguments("<player> <message>")
            .description("Bungee kick")
            .senderCaller((sender, args) -> {
                    if (args.length < 2) return false;
                    final Player target = CommandArgCompleter.requirePlayer(args[0]);
                    final String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
                    Bungee.kick(target, message);
                    return true;
                });
        coreCommand.addChild("kickraw").arguments("<player> <component>")
            .description("Bungee kick")
            .senderCaller((sender, args) -> {
                    if (args.length < 2) return false;
                    final Player target = CommandArgCompleter.requirePlayer(args[0]);
                    final var message = CommandArgCompleter.requireComponent(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                    Bungee.kickRaw(target, message);
                    return true;
                });
        coreCommand.addChild("skin").arguments("<player>")
            .senderCaller((sender, args) -> {
                    if (args.length != 1) return false;
                    final var target = PlayerCache.require(args[0]);
                    final var playerSkin = com.cavetale.core.skin.PlayerSkin.getPlayerSkin(target.uuid);
                    if (playerSkin == null) {
                        throw new CommandWarn("Skin not found: " + target);
                    }
                    sender.sendMessage("texture=" + playerSkin.getTextureBase64());
                    sender.sendMessage("face=" + playerSkin.getFaceBase64());
                    return true;
                });
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return coreCommand.call(sender, command, alias, args);
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        return coreCommand.complete(sender, command, alias, args);
    }

    private boolean saveCommand(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        saveDefaultConfig();
        sender.sendMessage("[Core] Default configuration saved to disk!");
        return true;
    }

    private boolean reloadCommand(CommandSender sender, String[] args) {
        if (args.length != 0) return false;
        loadConfiguration();
        sender.sendMessage("[Core] Default configuration saved to disk!");
        return true;
    }

    private void loadConfiguration() {
        reloadConfig();
        boolean denyBuilding = getConfig().getBoolean("DenyBuilding");
        PlayerBreakBlockEvent.setDenyBuilding(denyBuilding);
        PlayerBlockAbilityQuery.setDenyBuilding(denyBuilding);
        PlayerEntityAbilityQuery.setDenyBuilding(denyBuilding);
        if (denyBuilding) {
            getLogger().info("Deny building enabled!");
        }
    }

    public static CorePlugin plugin() {
        return instance;
    }
}
