package com.cavetale.core.command;

public interface CommandCall {
    boolean call(CommandContext context, CommandNode node, String[] args);
}
