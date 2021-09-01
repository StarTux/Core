package com.cavetale.core.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    CommandArgCompleter INTEGER = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    return Arrays.asList("" + Integer.parseInt(arg));
                } catch (NumberFormatException nfe) {
                    return Collections.emptyList();
                }
            }
        };

    static CommandArgCompleter integer(final IntPredicate validator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    int input = Integer.parseInt(arg);
                    return validator.test(input)
                        ? Arrays.asList("" + input)
                        : Collections.emptyList();
                } catch (NumberFormatException nfe) {
                    return Collections.emptyList();
                }
            }
        };
    }

    CommandArgCompleter LONG = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    return Arrays.asList("" + Long.parseLong(arg));
                } catch (NumberFormatException nfe) {
                    return Collections.emptyList();
                }
            }
        };

    static CommandArgCompleter longs(final LongPredicate validator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    long input = Long.parseLong(arg);
                    return validator.test(input)
                        ? Arrays.asList("" + input)
                        : Collections.emptyList();
                } catch (NumberFormatException nfe) {
                    return Collections.emptyList();
                }
            }
        };
    }

    CommandArgCompleter DOUBLE = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    return Arrays.asList("" + Double.parseDouble(arg));
                } catch (NumberFormatException nfe) {
                    return Collections.emptyList();
                }
            }
        };

    static CommandArgCompleter doubles(final DoublePredicate validator) {
        return new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                try {
                    double input = Double.parseDouble(arg);
                    return validator.test(input)
                        ? Arrays.asList("" + input)
                        : Collections.emptyList();
                } catch (NumberFormatException nfe) {
                    return Collections.emptyList();
                }
            }
        };
    }
}
