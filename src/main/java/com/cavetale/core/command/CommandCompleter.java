package com.cavetale.core.command;

import java.util.List;

public interface CommandCompleter {
    /**
     * Produce a list of possible completions for the command line input.
     * @param context the context
     * @param node the originating node
     * @param args the remaining command line arguments
     */
    List<String> complete(CommandContext context, CommandNode node, String[] args);
}
