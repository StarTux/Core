package com.cavetale.core.command;

@FunctionalInterface
public interface CommandCall {
    boolean call(CommandContext context, CommandNode node, String[] args);
}
