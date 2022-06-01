package com.cavetale.core.command;

import com.cavetale.core.connect.Connect;
import com.cavetale.core.connect.NetworkServer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.event.ClickEvent.suggestCommand;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.*;

/**
 * The basis for command line resolution.
 * For example code, please refer to the README.md.
 */
public final class CommandNode {
    private final CommandNode parent;
    @Getter private final List<CommandNode> children = new ArrayList<>();
    private final String key;
    private List<String> aliases = new ArrayList<>();
    private CommandCall call;
    private CommandCompleter completer;
    private String permission;
    private String arguments; // compiling help
    private Component description; // compiling help
    @Getter private boolean hidden;
    private NetworkServer remoteServer;

    /**
     * Constructor for a child node. Internal use only.
     */
    public CommandNode(final CommandNode parent, final String key) {
        this.parent = parent;
        this.key = key;
    }

    /**
     * Constructor for a root node.
     */
    public CommandNode(final String key) {
        this(null, key);
    }

    /**
     * Create a new child and return it.
     * This is useful for chaining with the chain setters below.
     */
    public CommandNode addChild(String childKey) {
        CommandNode child = new CommandNode(this, childKey);
        children.add(child);
        return child;
    }

    /**
     * Return a child by the given label or alias.
     */
    public CommandNode getChild(String childKey) {
        CommandNode child = findChildCommand(childKey);
        if (child == null) throw new IllegalArgumentException("Child not found: " + childKey);
        return child;
    }

    /**
     * Set the command caller. (Chainable)
     */
    public CommandNode caller(CommandCall newCall) {
        this.call = newCall;
        return this;
    }

    /**
     * Set the command caller. (Chainable, convenience)
     */
    public CommandNode caller(BiFunction<CommandContext, String[], Boolean> callback) {
        this.call = (ctx, nod, args) -> callback.apply(ctx, args);
        return this;
    }

    /**
     * Set the command caller. (Chainable, convenience)
     * This exists to allow functions with CommandSender and String[]
     * arguments.
     */
    public CommandNode senderCaller(BiFunction<CommandSender, String[], Boolean> callback) {
        this.call = (ctx, nod, args) -> callback.apply(ctx.sender, args);
        return this;
    }

    /**
     * Set the command caller. (Chainable, convenience)
     * This allows functions with CommandSender, requiring an empty
     * argument list.
     */
    public CommandNode senderCaller(Consumer<CommandSender> callback) {
        this.call = (ctx, nod, args) -> {
            if (args.length != 0) return false;
            callback.accept(ctx.sender);
            return true;
        };
        return this;
    }

    /**
     * Set the command caller. (Chainable, convenience)
     * This exists to allow functions with Player and String[]
     * arguments.
     */
    public CommandNode playerCaller(BiFunction<Player, String[], Boolean> callback) {
        this.call = (ctx, nod, args) -> callback.apply(ctx.requirePlayer(), args);
        return this;
    }

    /**
     * Set the command caller.
     * This allows functions with a Player, requiring an empty
     * argument list.
     */
    public CommandNode playerCaller(Consumer<Player> callback) {
        this.call = (ctx, nod, args) -> {
            if (args.length != 0) return false;
            callback.accept(ctx.requirePlayer());
            return true;
        };
        return this;
    }

    public CommandNode remotePlayerCaller(BiFunction<RemotePlayer, String[], Boolean> callback) {
        this.call = (ctx, nod, args) -> callback.apply(ctx.requireRemotePlayer(), args);
        return this;
    }

    public CommandNode remotePlayerCaller(Consumer<RemotePlayer> callback) {
        this.call = (ctx, nod, args) -> {
            if (args.length != 0) return false;
            callback.accept(ctx.requireRemotePlayer());
            return true;
        };
        return this;
    }

    /**
     * Set the tab completer.
     * This will add to completions generated from child nodes.
     */
    public CommandNode completer(CommandCompleter newCompleter) {
        this.completer = newCompleter;
        return this;
    }

    public CommandNode completers(final CommandArgCompleter... newCompleters) {
        if (newCompleters.length == 0) {
            this.completer = CommandCompleter.EMPTY;
            return this;
        }
        final boolean doRepeat = newCompleters[newCompleters.length - 1] == CommandArgCompleter.REPEAT;
        final CommandArgCompleter[] array = doRepeat
            ? Arrays.copyOfRange(newCompleters, 0, newCompleters.length - 1)
            : newCompleters;
        if (array.length == 0) throw new IllegalArgumentException("completers=[REPEAT]");
        this.completer = (context, node, args) -> {
            int argc = args.length;
            if (argc < 1) return null;
            if (argc > array.length) {
                return doRepeat
                    ? array[array.length - 1].complete(context, node, args[argc - 1])
                    : Collections.emptyList();
            }
            return array[argc - 1].complete(context, node, args[argc - 1]);
        };
        return this;
    }

    /**
     * Supply a list of possible completions. It will be filtered to
     * match the input.
     * This only makes sense if the command node accepts exactly one
     * argument.
     */
    public CommandNode completableList(List<String> list) {
        this.completer = (ctx, nod, args) -> {
            if (args.length != 1) return Collections.emptyList();
            String arg = args[0].toLowerCase();
            return list.stream()
            .filter(i -> i.toLowerCase().startsWith(arg))
            .collect(Collectors.toList());
        };
        return this;
    }

    /**
     * Supply a producer of possible completions. It will be filtered
     * to match the input.
     */
    public CommandNode completableList(Function<CommandContext, List<String>> func) {
        this.completer = (ctx, nod, args) -> {
            if (args.length == 0) return null;
            String arg = args[args.length - 1].toLowerCase();
            return func.apply(ctx).stream()
            .filter(i -> i.toLowerCase().startsWith(arg))
            .collect(Collectors.toList());
        };
        return this;
    }

    /**
     * Tab completion will yield nothing.
     * Completions generated from child nodes will still take place.
     */
    public CommandNode denyTabCompletion() {
        completer = CommandCompleter.EMPTY;
        return this;
    }

    /**
     * Supply the description for this command which will be printed
     * as part of the help.
     */
    public CommandNode description(String desc) {
        this.description = text(desc, WHITE);
        return this;
    }

    /**
     * Supply the description for this command which will be printed
     * as part of the help.
     */
    public CommandNode description(Component desc) {
        this.description = desc;
        return this;
    }

    /**
     * Supply the args which will be part of the automated help.
     * Example: <code>{@code node.arguments("<player> <item> [page]");}</code>
     */
    public CommandNode arguments(String args) {
        this.arguments = args;
        return this;
    }

    /**
     * Add one or more aliases.
     */
    public CommandNode alias(String... newAliases) {
        for (String newAlias : newAliases) {
            aliases.add(newAlias);
        }
        return this;
    }

    /**
     * Set the permission for this node.
     */
    public CommandNode permission(String newPermission) {
        this.permission = newPermission;
        return this;
    }

    public CommandNode hidden(boolean hide) {
        this.hidden = hide;
        return this;
    }

    public CommandNode remoteServer(NetworkServer networkServer) {
        this.remoteServer = networkServer;
        return this;
    }

    /**
     * Call this node as if it was entered in the command line, maybe
     * with additional arguments which will be handled here.
     * This will recursively traverse the tree, resulting in potential
     * user feedback or execution at any point, which is why we don't
     * use a utility traversal function.
     * CommandExecutor::onCommand may wrap this function directly.
     * @param context the context
     * @param args the remaining command line arguments
     */
    public boolean call(CommandContext context, String[] args) {
        if (remoteServer != null && context.isPlayer()) {
            String cmd = context.label + " " + String.join(" " + context.args);
            Connect.get().dispatchRemoteCommand(context.player, cmd, remoteServer.registeredName);
            return true;
        }
        if (args.length > 0 && !children.isEmpty()) {
            CommandNode child = findChildCommand(args[0]);
            if (child != null && child.hasPermission(context.sender)) {
                boolean res = child.call(context, Arrays.copyOfRange(args, 1, args.length));
                if (!res) res = sendHelp(context.sender);
                return res;
            }
        }
        if (call != null) {
            return wrapExecutor(context.sender, () -> call.call(context, this, args));
        }
        return sendHelp(context.sender);
    }

    public boolean call(CommandSender sender, Command command, String label, String[] args) {
        return call(new CommandContext(sender, command, label, args), args);
    }

    /**
     * Recursively find the tab completion of this node as if it was
     * entered into the command line, with the given arguments.
     * TabCompleter::onTabComplete may wrap this function directly.
     */
    public List<String> complete(CommandContext context, String[] args) {
        if (args.length == 0) return null;
        CommandNode child = findChildCommand(args[0]);
        if (child != null && child.hasPermission(context.sender)) {
            return child.complete(context, Arrays.copyOfRange(args, 1, args.length));
        }
        if (completer != null) {
            return completer.complete(context, this, args);
        }
        return args.length == 1 ? completeChildren(context, args[0]) : Collections.emptyList();
    }

    public List<String> complete(CommandSender sender, Command command, String label, String[] args) {
        return complete(new CommandContext(sender, command, label, args), args);
    }

    /**
     * Find the immediate child command for the given command line
     * argument.
     */
    public CommandNode findChildCommand(String arg) {
        for (CommandNode child : children) {
            if (child.key.equalsIgnoreCase(arg)) {
                return child;
            }
            for (String alias : child.aliases) {
                if (alias.equalsIgnoreCase(arg)) {
                    return child;
                }
            }
        }
        return null;
    }

    /**
     * Test if the label or any of the aliases begin with the given
     * argument. (Helper function)
     */
    public boolean labelStartsWith(String arg) {
        String argl = arg.toLowerCase();
        if (key.toLowerCase().startsWith(argl)) return true;
        for (String alias : aliases) {
            if (alias.toLowerCase().startsWith(argl)) return true;
        }
        return false;
    }

    /**
     * Stream the label and all aliases. (Convenience method)
     */
    public Stream<String> getLabelStream() {
        return Stream.concat(Stream.of(key), aliases.stream());
    }

    /**
     * Fetch the tab completions of this node for the given command
     * line argument.
     */
    public List<String> completeChildren(CommandContext context, String arg) {
        if (children.isEmpty()) return null;
        return children.stream()
            .filter(child -> !child.hidden)
            .filter(child -> child.hasPermission(context.sender))
            .filter(child -> child.labelStartsWith(arg))
            .flatMap(CommandNode::getLabelStream)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Send help to a player. This will check the permission and
     * compute the correct help string for this node.
     */
    private int sendHelpUtil(CommandSender sender, int indent) {
        if (hidden) return 0;
        if (!hasPermission(sender)) return 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i += 1) sb.append(' ');
        String prefix = sb.toString();
        int count = 0;
        List<Component> lines;
        Component commandLine = getColorizedCommandLine().asComponent();
        TextComponent.Builder line = text()
            .append(commandLine);
        if (arguments != null) {
            line.append(Component.space());
            line.append(text(arguments, GRAY));
        } else if (!children.isEmpty()) {
            line.append(text("...", AQUA));
        }
        if (description != null) {
            line.append(text(" \u2014 ", DARK_GRAY));
            line.append(description);
        }
        line.clickEvent(suggestCommand(getCommandLine()));
        line.hoverEvent(showText(commandLine));
        lines = Arrays.asList(line.build());
        sender.sendMessage(join(separator(newline()), lines));
        return lines.size();
    }

    /**
     * Send help to the sender.
     * @return true if at least a line of help was sent, false
     * otherwise
     */
    public boolean sendHelp(CommandSender sender) {
        int lineCount = 0;
        lineCount += sendHelpUtil(sender, 0);
        for (CommandNode child : children) {
            lineCount += child.sendHelpUtil(sender, 2);
        }
        return lineCount > 0;
    }

    /**
     * Test if this node can be executed by itself as a command.
     */
    public boolean hasCommandCall() {
        return call != null;
    }

    /**
     * Test if this is a root node, meaning it has no parent node.
     */
    public boolean isRootNode() {
        return parent == null;
    }

    /**
     * Wander up the tree to find the root node and return it.
     */
    public CommandNode getRootNode() {
        CommandNode node = this;
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    /**
     * Check if the given context has permission to use this
     * node. (Convenience function)
     */
    public boolean hasPermission(CommandSender sender) {
        if (permission == null) return true;
        return sender.hasPermission(permission);
    }

    /**
     * Produce the entire command line up to this point from the root
     * node.
     */
    public String getCommandLine() {
        if (parent == null) return "/" + key;
        return parent.getCommandLine() + " " + key;
    }

    public TextComponent.Builder getColorizedCommandLine() {
            return parent == null
                ? text().append(text("/" + key, GREEN))
                : (parent.getColorizedCommandLine()
                   .append(Component.space())
                   .append(text(key, AQUA)));
    }

    /**
     * Wrap up another function so it reports CommandWarn or negative
     * return values just like a command would.
     */
    public boolean wrapExecutor(CommandSender sender, Supplier<Boolean> supplier) {
        final boolean res;
        try {
            res = supplier.get();
        } catch (CommandWarn warn) {
            sender.sendMessage(text(warn.getMessage(), RED));
            return true;
        }
        return res ? res : sendHelp(sender);
    }

    /**
     * Wrap up another function so it reports CommandWarn just like a
     * command would.
     */
    public static void wrap(CommandSender sender, Runnable runnable) {
        try {
            runnable.run();
        } catch (CommandWarn warn) {
            sender.sendMessage(text(warn.getMessage(), RED));
        }
    }
}
