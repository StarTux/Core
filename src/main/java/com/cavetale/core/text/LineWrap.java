package com.cavetale.core.text;

import com.cavetale.core.font.Emoji;
import com.cavetale.core.font.GlyphPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.Component;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.noSeparators;

/**
 * Line wrapping utility which can handle Emoji.
 * Wart: All characters and emoji are assumed to have the same width.
 */
@Accessors(fluent = true, chain = true) @Getter @Setter
public final class LineWrap {
    private int maxLineLength = 26;
    private Function<String, Component> componentMaker = Component::text;
    private boolean tooltip = false;
    private GlyphPolicy glyphPolicy = GlyphPolicy.HIDDEN;

    public List<Component> wrap(String input) {
        List<List<Object>> lines = new ArrayList<>();
        // Respect paragraphs
        for (String paragraph : input.split("\n\n+")) {
            // Respect newlines
            for (String line : paragraph.split("\n")) {
                // Split the input line into words.  The line will be
                // chaotic at first, containing tiny strings and
                // compounds, but we will reduce that in a later step.
                List<Object> words = new ArrayList<>();
                for (String word : line.split("\\s+")) {
                    // Parse emoji in each word
                    words.add(parseEmojis(word));
                }
                if (words.size() == 0) {
                    lines.add(List.of());
                    continue;
                }
                int lineLength = 0;
                List<Object> lineBuilder = new ArrayList<>();
                for (int i = 0; i < words.size(); ++i) {
                    Object item = words.get(i);
                    int wordLength = length(item);
                    if (lineBuilder.isEmpty()) {
                        // Special case for first word
                        if (wordLength > maxLineLength) {
                            List<Object> splits = split(item);
                            Object firstSplit = splits.get(0);
                            lineBuilder.add(firstSplit);
                            lineLength += length(firstSplit);
                            words.set(i, firstSplit);
                            for (int j = 1; j < splits.size(); j += 1) {
                                words.add(i + j, splits.get(j));
                            }
                        } else {
                            lineBuilder.add(item);
                            lineLength += wordLength;
                        }
                    } else if (lineLength + wordLength + 1 > maxLineLength) {
                        // Line length has been exceeded
                        lines.add(lineBuilder);
                        lineBuilder = new ArrayList<>();
                        lineLength = 0;
                        i -= 1;
                    } else {
                        // Add a word and move on
                        lineBuilder.add(" ");
                        lineBuilder.add(item);
                        lineLength += wordLength + 1;
                    }
                }
                if (!lineBuilder.isEmpty()) lines.add(lineBuilder);
            }
        }
        List<Component> result = new ArrayList<>(lines.size());
        for (List<Object> line : lines) {
            reduceLine(line);
            result.add(formatLine(line));
        }
        return result;
    }

    /**
     * Simplify a list of line components so that consecutive Strings
     * are combined into one and compounds replaced by their contents.
     */
    private void reduceLine(List<Object> line) {
        for (int i = 0; i < line.size(); i += 1) {
            if (line.get(i) instanceof String string) {
                String theString = string;
                while (line.size() >= i + 2 && line.get(i + 1) instanceof String string2) {
                    theString = theString + string2;
                    line.set(i, theString);
                    line.remove(i + 1);
                }
            } else if (line.get(i) instanceof Compound compound) {
                int j = 1;
                for (Object member : compound.members) {
                    line.add(i + j++, member);
                }
                line.remove(i);
                i -= 1;
            }
        }
    }

    /**
     * Turn a list of line members into one line component.
     * A line member is either a String or an Emoji.
     */
    private Component formatLine(List<Object> line) {
        if (line.isEmpty()) return empty();
        if (line.size() == 1) return formatLineMember(line.get(0));
        List<Component> components = new ArrayList<>();
        for (Object o : line) {
            components.add(formatLineMember(o));
        }
        return join(noSeparators(), components);
    }

    /**
     * Turn one line member into a component.
     * A line member is either a String or an Emoji.
     */
    private Component formatLineMember(Object o) {
        if (o instanceof String string) {
            return componentMaker.apply(string);
        } else if (o instanceof Emoji emoji) {
            return tooltip
                ? emoji.getComponentWithTooltip()
                : emoji.getComponent();
        } else {
            return empty();
        }
    }

    private static int length(Object o) {
        if (o instanceof String string) {
            return string.length();
        } else if (o instanceof Emoji emoji) {
            // FIXME emoji length
            return 1;
        } else if (o instanceof Compound compound) {
            return compound.length;
        } else {
            return 0;
        }
    }

    /**
     * Split a member so that the first resulting element fits a line
     * with maxLineLength.
     * This is used when an empty line wants to add a member which
     * exceeds maxLineLength.
     */
    private List<Object> split(Object o) {
        if (o instanceof String string) {
            if (string.length() <= maxLineLength) return List.of(string);
            return List.of(string.substring(0, maxLineLength),
                           string.substring(maxLineLength, string.length()));
        } else if (o instanceof Compound compound) {
            List<Object> left = new ArrayList<>();
            List<Object> right = compound.members;
            int length = 0;
            while (length < maxLineLength && !right.isEmpty()) {
                int remain = maxLineLength - length;
                if (right.get(0) instanceof String string) {
                    if (string.length() < remain) {
                        left.add(string);
                        right.remove(0);
                        length += string.length();
                    } else {
                        left.add(string.substring(0, remain));
                        right.set(0, string.substring(remain, string.length()));
                        length += remain;
                    }
                } else if (right.get(0) instanceof Emoji emoji) {
                    // FIXME emoji length
                    length += 1;
                    left.add(emoji);
                    right.remove(0);
                }
            }
            List<Object> result = new ArrayList<>(2);
            result.add(left.size() == 1
                       ? left.get(0)
                       : new Compound(length, left));
            if (!right.isEmpty()) {
                result.add(new Compound(compound.length - length, right));
            }
            return result;
        } else {
            return List.of(o);
        }
    }

    /**
     * A compound is a combination of emoji and text which cannot be
     * split.
     */
    @Value
    private static final class Compound {
        private final int length;
        private final List<Object> members;

        @Override
        public String toString() {
            return "Compound(" + members + ")";
        }
    }

    /**
     * Return either String, Emoji, or Compound.
     */
    private Object parseEmojis(String in) {
        if (in.isEmpty() || !in.contains(":")) return in;
        int length = 0;
        List<Object> list = new ArrayList<>();
        String head = "";
        String tail = in;
        do {
            int index = tail.indexOf(":");
            if (index < 0) break;
            String tail2 = tail.substring(index + 1);
            int index2 = tail2.indexOf(":");
            if (index2 < 0) break;
            String key = tail2.substring(0, index2);
            Emoji emoji = Emoji.getEmoji(key);
            if (emoji == null || !glyphPolicy.entails(emoji.glyphPolicy)) {
                head = head + tail.substring(0, index) + ":" + key;
                tail = tail2.substring(index2);
                continue;
            }
            head = head + tail.substring(0, index);
            if (!head.isEmpty()) {
                length += head.length();
                list.add(head);
                head = "";
            }
            // FIXME emoji length
            length += 1;
            list.add(emoji);
            tail = tail2.substring(index2 + 1);
        } while (!tail.isEmpty());
        if (!head.isEmpty() || !tail.isEmpty()) {
            String remain = head + tail;
            length += remain.length();
            list.add(remain);
        }
        if (list.size() == 1) return list.get(0);
        return new Compound(length, list);
    }
}
