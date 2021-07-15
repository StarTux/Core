package com.cavetale.core.command;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface CommandArgCompleter {
    /**
     * Produce a list of possible completions for the command line argument.
     * @param context the context
     * @param node the originating node
     * @param args the remaining command line arguments
     */
    List<String> complete(CommandContext context, CommandNode node, String arg);

    CommandArgCompleter NULL = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return null;
            }
        };

    CommandArgCompleter EMPTY = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return Collections.emptyList();
            }
        };

    CommandArgCompleter REPEAT = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                throw new IllegalStateException("REPEAT::complete was called!");
            }
        };

    static CommandArgCompleter list(final List<String> args) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return args.stream()
                    .filter(it -> it.contains(arg))
                    .collect(Collectors.toList());
            }
        };
    }

    static CommandArgCompleter ignoreCaseList(final List<String> args) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                String lower = arg.toLowerCase();
                return args.stream()
                    .filter(it -> it.toLowerCase().contains(lower))
                    .collect(Collectors.toList());
            }
        };
    }
}
