package com.cavetale.core.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The basis for command line resolution.
 * For example code, please refer to the README.md.
 */
public final class CommandNode {
    private final CommandNode parent;
    private final List<CommandNode> children = new ArrayList<>();
    private final String key;
    private List<String> aliases = new ArrayList<>();
    private CommandCall call;
    private CommandCompleter completer;
    private CommandHelp help;
    private String permission;
    private String arguments; // compiling help
    private String description; // compiling help
    @Getter private boolean hidden;

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
     * This exists to allow functions with Player and String[]
     * arguments.
     */
    public CommandNode playerCaller(BiFunction<Player, String[], Boolean> callback) {
        this.call = (ctx, nod, args) -> callback.apply(ctx.requirePlayer(), args);
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
        completer = (ctx, nod, args) -> Collections.emptyList();
        return this;
    }

    /**
     * Set the helper which produces the help for this node.
     * Permission checks and subcommand help is automated and need not
     * be worried about.
     */
    public CommandNode helper(CommandHelp newHelp) {
        this.help = newHelp;
        return this;
    }

    /**
     * Supply the exact help as a list.
     * Permission checks and subcommand help is automated.
     */
    public CommandNode helpList(List<String> newHelp) {
        this.help = (ctx, nod) -> {
            return newHelp;
        };
        return this;
    }

    /**
     * Same as helpList() but with a varchar.
     */
    public CommandNode helpLines(String... lines) {
        if (lines.length == 0) throw new IllegalArgumentException("Lines cannot bot empty");
        return helpList(Arrays.asList(lines));
    }

    /**
     * Supply the description for this command which will be printed
     * as part of the help. Calling helper(), helpList(), or
     * helpLine() will render this obsolete.
     */
    public CommandNode description(String desc) {
        this.description = desc;
        return this;
    }

    /**
     * Supply the args which will be part of the automated
     * help. Calling helper() or helpList() will render this obsolete.
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
        if (args.length > 0 && !children.isEmpty()) {
            CommandNode child = findChildCommand(args[0]);
            if (child != null && child.hasPermission(context)) {
                boolean res = child.call(context, Arrays.copyOfRange(args, 1, args.length));
                if (!res) res = sendHelp(context);
                return res;
            }
        }
        if (call != null) {
            boolean res;
            try {
                res = call.call(context, this, args);
            } catch (CommandWarn warn) {
                context.sender.sendMessage(ChatColor.RED + warn.getMessage());
                return true;
            }
            if (!res) res = sendHelp(context);
            return res;
        }
        return sendHelp(context);
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
        if (child != null && child.hasPermission(context)) {
            return child.complete(context, Arrays.copyOfRange(args, 1, args.length));
        }
        List<String> listChildren = args.length == 1 ? completeChildren(context, args[0]) : Collections.emptyList();
        List<String> listCustom = completeCustom(context, args);
        if (listChildren == null && listCustom == null) return null;
        List<String> list = new ArrayList<>();
        if (listChildren != null) list.addAll(listChildren);
        if (listCustom != null) list.addAll(listCustom);
        return list;
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
            .filter(child -> child.hasPermission(context))
            .filter(child -> child.labelStartsWith(arg))
            .flatMap(CommandNode::getLabelStream)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<String> completeCustom(CommandContext context, String[] args) {
        if (completer == null) return null;
        return completer.complete(context, this, args);
    }

    /**
     * Send help to a player. This will check the permission and
     * compute the correct help string for this node.
     */
    private int sendHelpUtil(CommandContext context, int indent) {
        if (hidden) return 0;
        if (!hasPermission(context)) return 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i += 1) sb.append(' ');
        String prefix = sb.toString();
        int count = 0;
        String autoLine = getColorizedCommandLine()
            + (arguments != null
               ? " " + ChatColor.GRAY + ChatColor.ITALIC + arguments
               : "")
            + (description != null
               ? ChatColor.DARK_GRAY + " - " + ChatColor.WHITE + description
               : "");
        List<String> lines;
        if (help == null) {
            lines = Arrays.asList(autoLine);
        } else {
            lines = help.help(context, this);
        }
        for (String line : lines) {
            if (context.isPlayer()) {
                ComponentBuilder cb = new ComponentBuilder()
                    .append(prefix + line)
                    .event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, getCommandLine()))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(line)));
                context.player.sendMessage(cb.create());
            } else {
                context.sender.sendMessage(prefix + line);
            }
            count += 1;
        }
        return count;
    }

    /**
     * Send help to the sender.
     * @return true if at least a line of help was sent, false
     * otherwise
     */
    public boolean sendHelp(CommandContext context) {
        int lineCount = 0;
        lineCount += sendHelpUtil(context, 0);
        for (CommandNode child : children) {
            lineCount += child.sendHelpUtil(context, 2);
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
    public boolean hasPermission(CommandContext context) {
        if (permission == null) return true;
        if (context.isConsole()) return true;
        if (context.isPlayer()) return context.player.hasPermission(permission);
        if (context.isEntity()) return true;
        return false;
    }

    /**
     * Produce the entire command line up to this point from the root
     * node.
     */
    public String getCommandLine() {
        if (parent == null) return "/" + key;
        return parent.getCommandLine() + " " + key;
    }

    public String getColorizedCommandLine() {
        if (parent == null) return ChatColor.YELLOW + "/" + key;
        return parent.getColorizedCommandLine() + ChatColor.GOLD + " " + key;
    }
}
