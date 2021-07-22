package com.cavetale.core.command;

import java.util.List;
import net.kyori.adventure.text.Component;

public interface CommandHelp {
    List<Component> help(CommandContext context, CommandNode node);
}
