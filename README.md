# Core

Core plugin.

## Command

The `com.cavetale.core.command` package helps with quick creation of
command trees. Each node can be told how to check permissions,
execute, send help to the sender, and tab complete.

### CommandNode

`CommandNode` is the basis for command line resolution. Once during
plugin enable, a tree of this object will be created, for example:

```java
CommandNode root = new CommandNode("race");
root.addChild("start")
  .arguments("<countdown>")
  .description("Start the race")
  .caller(this::start)
  .completer(this::completeStart);
```

The functions of TabCompleter call into the root node:

```java
@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
  return root.call(sender, command, label, args);
}
@Override
public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
  return root.complete(sender, command, label, args);
}
```

A proper setup should do the rest.

### Known Issues

Trees with intermediate arguments are not supported yet.
For example: `/perm user <name> perm info`
