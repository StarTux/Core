package com.cavetale.core.command;

import java.util.Collections;
import java.util.List;

@FunctionalInterface
public interface CommandCompleter {
    /**
     * Produce a list of possible completions for the command line input.
     * @param context the context
     * @param node the originating node
     * @param args the remaining command line arguments
     */
    List<String> complete(CommandContext context, CommandNode node, String[] args);

    CommandCompleter NULL = new CommandCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String[] args) {
                return null;
            }
        };

    CommandCompleter EMPTY = new CommandCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String[] args) {
                return Collections.emptyList();
            }
        };
}
