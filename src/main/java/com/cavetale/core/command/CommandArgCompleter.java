package com.cavetale.core.command;

import com.cavetale.core.connect.Connect;
import com.cavetale.core.playercache.PlayerCache;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface CommandArgCompleter {
    /**
     * Produce a list of possible completions for the command line argument.
     * @param context the context
     * @param node the originating node
     * @param args the remaining command line arguments
     */
    List<String> complete(CommandContext context, CommandNode node, String arg);

    CommandArgCompleter EMPTY = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return List.of();
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

    static CommandArgCompleter list(final String... args) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return Stream.of(args)
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

    static <E extends Enum> CommandArgCompleter enumList(final Class<E> clazz, Function<E, String> translator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                List<String> result = new ArrayList<>();
                for (E e : clazz.getEnumConstants()) {
                    String name = translator.apply(e);
                    if (name.contains(arg)) {
                        result.add(name);
                    }
                }
                return result;
            }
        };
    }

    static <E extends Enum> CommandArgCompleter enumList(final Class<E> clazz) {
        return enumList(clazz, Enum::name);
    }

    static <E extends Enum> CommandArgCompleter enumLowerList(final Class<E> clazz) {
        return enumList(clazz, e -> e.name().toLowerCase());
    }

    static CommandArgCompleter supplyStream(final Supplier<Stream<String>> streamSupplier) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return streamSupplier.get()
                    .filter(a -> a.contains(arg))
                    .collect(Collectors.toList());
            }
        };
    }

    static CommandArgCompleter supplyList(final Supplier<List<String>> listSupplier) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                List<String> result = new ArrayList<>();
                for (String it : listSupplier.get()) {
                    if (it.contains(arg)) {
                        result.add(it);
                    }
                }
                return result;
            }
        };
    }

    static CommandArgCompleter supplyIgnoreCaseList(final Supplier<List<String>> listSupplier) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                List<String> result = new ArrayList<>();
                String lower = arg.toLowerCase();
                for (String it : listSupplier.get()) {
                    if (lower.isEmpty() || it.toLowerCase().contains(lower)) {
                        result.add(it);
                    }
                }
                return result;
            }
        };
    }

    CommandArgCompleter INTEGER = integer(i -> true);

    static CommandArgCompleter integer(final IntPredicate validator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                if (arg.isEmpty()) {
                    ArrayList<String> result = new ArrayList<>();
                    for (int i : List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                                         -1, -2, -3, -4, -5, -6, -7, -8, -9)) {
                        if (validator.test(i)) result.add("" + i);
                    }
                    return result;
                }
                final int input;
                try {
                    input = Integer.parseInt(arg);
                } catch (NumberFormatException nfe) {
                    return List.of();
                }
                if (!validator.test(input)) return List.of();
                ArrayList<String> result = new ArrayList<>();
                result.add("" + input);
                for (int i : List.of(10, 100, 1000)) {
                    int value = input * i;
                    if (validator.test(value)) result.add("" + value);
                }
                return result;
            }
        };
    }

    CommandArgCompleter LONG = longs(l -> true);

    static CommandArgCompleter longs(final LongPredicate validator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    long input = Long.parseLong(arg);
                    return validator.test(input)
                        ? List.of("" + input)
                        : List.of();
                } catch (NumberFormatException nfe) {
                    return List.of();
                }
            }
        };
    }

    CommandArgCompleter DOUBLE = doubles(d -> true);

    static CommandArgCompleter doubles(final DoublePredicate validator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    double input = Double.parseDouble(arg);
                    return validator.test(input)
                        ? List.of("" + input)
                        : List.of();
                } catch (NumberFormatException nfe) {
                    return List.of();
                }
            }
        };
    }

    CommandArgCompleter BOOLEAN = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                List<String> result = new ArrayList<>(2);
                for (String it : List.of("true", "false")) {
                    if (it.contains(arg.toLowerCase())) {
                        result.add(it);
                    }
                }
                return result;
            }
        };

    CommandArgCompleter ONLINE_PLAYERS = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                final List<RemotePlayer> players = Connect.get().getRemotePlayers();
                if (players.isEmpty()) return List.of();
                final String lower = arg.toLowerCase();
                final List<String> result = new ArrayList<>(players.size());
                for (RemotePlayer it : players) {
                    if (it.getName().toLowerCase().contains(lower)) {
                        result.add(it.getName());
                    }
                }
                return result;
            }
        };

    CommandArgCompleter PLAYER_CACHE = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return PlayerCache.completeNames(arg);
            }
        };

    CommandArgCompleter NULL = ONLINE_PLAYERS;

    static int requireInt(String arg, IntPredicate validator) {
        final int input;
        try {
            input = Integer.parseInt(arg);
        } catch (NumberFormatException nfe) {
            throw new CommandWarn("Number expected: " + arg);
        }
        if (!validator.test(input)) {
            throw new CommandWarn("Invalid number: " + arg);
        }
        return input;
    }

    static int requireInt(String arg) {
        return requireInt(arg, i -> true);
    }

    static long requireLong(String arg, LongPredicate validator) {
        final long input;
        try {
            input = Long.parseLong(arg);
        } catch (NumberFormatException nfe) {
            throw new CommandWarn("Number expected: " + arg);
        }
        if (!validator.test(input)) {
            throw new CommandWarn("Invalid number: " + arg);
        }
        return input;
    }

    static long requireLong(String arg) {
        return requireLong(arg, i -> true);
    }

    static double requireDouble(String arg, DoublePredicate validator) {
        final double input;
        try {
            input = Double.parseDouble(arg);
        } catch (NumberFormatException nfe) {
            throw new CommandWarn("Number expected: " + arg);
        }
        if (!validator.test(input)) {
            throw new CommandWarn("Invalid number: " + arg);
        }
        return input;
    }

    static double requireDouble(String arg) {
        return requireDouble(arg, i -> true);
    }

    static boolean requireBoolean(String arg) {
        switch (arg.toLowerCase()) {
        case "true":
        case "on":
        case "yes":
            return true;
        case "false":
        case "off":
        case "no":
            return false;
        default: throw new CommandWarn("True or false expected: " + arg);
        }
    }

    static <E extends Enum<E>> E requireEnum(Class<E> enumClass, String arg) {
        try {
            return Enum.valueOf(enumClass, arg.toUpperCase());
        } catch (IllegalArgumentException iae) {
            throw new CommandWarn(enumClass.getSimpleName() + " expected: " + arg);
        }
    }
}
