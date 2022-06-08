package com.cavetale.core.font;

import com.cavetale.core.CorePlugin;
import com.cavetale.core.command.CommandArgCompleter;
import com.cavetale.core.command.CommandContext;
import com.cavetale.core.command.CommandNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.MatchResult;
import lombok.Value;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.PatternReplacementResult;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.command.CommandSender;
import static com.cavetale.core.util.CamelCase.toCamelCase;
import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.newline;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.event.HoverEvent.showText;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static net.kyori.adventure.text.format.TextDecoration.*;

/**
 * A global cache of avilable emoji. Methods to replace chat tags with
 * said emoji. Other plugins may add their own emoji. Mytems will do
 * so.
 *
 * The replaceText and tabComplete have convenience overrides using
 * the PUBLIC glyph policy, whereas the getComponent() override
 * defaults to HIDDEN.
 */
@Value
public final class Emoji {
    private static final Map<String, Emoji> EMOJI_MAP = new HashMap<>();
    private static TextReplacementConfig.Builder textReplacementConfigBuilder;
    public final String name;
    public final Component component;
    public final Component componentWithTooltip;
    public final GlyphPolicy glyphPolicy;
    public final Enum enume;
    public final String category;

    public static final CommandArgCompleter PUBLIC_COMPLETER = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return tabComplete(arg, GlyphPolicy.PUBLIC);
            }
        };

    public static final CommandArgCompleter HIDDEN_COMPLETER = new CommandArgCompleter() {
            @Override
            public List<String> complete(CommandContext context, CommandNode node, String arg) {
                return tabComplete(arg, GlyphPolicy.HIDDEN);
            }
        };

    private Emoji(final String name, final Component component, final Component displayName,
                  final GlyphPolicy glyphPolicy, final Enum enume, final String category) {
        this.name = name;
        this.component = component;
        this.componentWithTooltip = component.hoverEvent(showText(join(separator(newline()),
                                                                       displayName,
                                                                       text(category, DARK_GRAY, ITALIC))));
        this.glyphPolicy = glyphPolicy;
        this.enume = enume;
        this.category = category;
    }

    public static void init() {
        textReplacementConfigBuilder = TextReplacementConfig.builder()
            .match(":[0-9a-z_]+:")
            .condition(Emoji::shouldReplace);
        init(VanillaEffects.class);
        init(VanillaPaintings.class);
        init(DefaultFont.class);
        init(VanillaItems.class);
        for (Unicode unicode : Unicode.values()) {
            addEmoji("u" + unicode.key, text(unicode.character), GlyphPolicy.PUBLIC, unicode, toCamelCase(" ", unicode.category));
        }
    }

    private static <E extends Enum<E> & Font> void init(Class<E> clazz) {
        for (E font : clazz.getEnumConstants()) {
            addEmoji(font.getEmojiName(), font.getComponent(), font.getDisplayName(), font.getPolicy(), font, font.getCategory());
        }
    }

    public static void addEmoji(String name, Component component, Component displayName, GlyphPolicy policy, Enum enume, String category) {
        if (EMOJI_MAP.containsKey(Objects.requireNonNull(name, "name=null"))) {
            CorePlugin.getInstance().getLogger().warning("Emoji: Duplicate " + name);
        }
        EMOJI_MAP.put(name, new Emoji(name,
                                      Objects.requireNonNull(component, "component=null"),
                                      Objects.requireNonNull(displayName, "displayName=null"),
                                      Objects.requireNonNull(policy, "policy=null"),
                                      enume,
                                      category));
    }

    public static void addEmoji(String name, Component component, GlyphPolicy policy, Enum enume, String category) {
        addEmoji(name, component, text(name), policy, enume, category);
    }

    public static Component replaceText(Component in, GlyphPolicy glyphPolicy, boolean withTooltip) {
        TextReplacementConfig textReplacementConfig = textReplacementConfigBuilder
            .replacement((MatchResult matchResult, TextComponent.Builder unused) ->
                         Emoji.replacer(matchResult, glyphPolicy, withTooltip))
            .build();
        return in.replaceText(textReplacementConfig);
    }

    public static Component replaceText(Component in, GlyphPolicy glyphPolicy) {
        return replaceText(in, glyphPolicy, false);
    }

    public static Component replaceText(Component in) {
        return replaceText(in, GlyphPolicy.PUBLIC, false);
    }

    /**
     * Replace emoji in a text string.
     * This method has the advantage is that it doesn't use
     * potentially slow regex pattern matching.
     * @return The component. Never null.
     */
    public static ComponentLike replaceText(String in, GlyphPolicy glyphPolicy, boolean withTooltip) {
        if (!in.contains(":")) return text(in);
        List<Component> list = new ArrayList<>();
        String head = "";
        String tail = in;
        do {
            int index = tail.indexOf(":");
            if (index < 0) break;
            String tail2 = tail.substring(index + 1);
            int index2 = tail2.indexOf(":");
            if (index2 < 0) break;
            String key = tail2.substring(0, index2);
            Emoji emoji = EMOJI_MAP.get(key);
            if (emoji == null || !glyphPolicy.entails(emoji.glyphPolicy)) {
                head = head + tail.substring(0, index) + ":" + key;
                tail = tail2.substring(index2);
                continue;
            }
            head = head + tail.substring(0, index);
            if (!head.isEmpty()) {
                list.add(text(head));
                head = "";
            }
            list.add(withTooltip ? emoji.componentWithTooltip : emoji.component);
            tail = tail2.substring(index2 + 1);
        } while (!tail.isEmpty());
        if (list.isEmpty()) return text(in);
        if (!head.isEmpty() || !tail.isEmpty()) {
            list.add(text(head + tail));
        }
        return text().append(list);
    }

    public static List<String> tabComplete(String arg, GlyphPolicy glyphPolicy) {
        int colonCount = 0;
        int lastColonIndex = -1;
        for (int i = 0; i < arg.length(); i += 1) {
            if (arg.charAt(i) == ':') {
                colonCount += 1;
                lastColonIndex = i;
            }
        }
        if (colonCount == 0 || colonCount % 2 != 1) {
            return Collections.emptyList();
        }
        String prefix = arg.substring(0, lastColonIndex + 1);
        String key = arg.substring(lastColonIndex + 1);
        List<String> result = new ArrayList<>(EMOJI_MAP.size());
        for (Emoji emoji : EMOJI_MAP.values()) {
            if (glyphPolicy.entails(emoji.glyphPolicy) && emoji.name.contains(key)) {
                result.add(prefix + emoji.name + ":");
            }
        }
        return result;
    }

    public static List<String> tabComplete(String arg) {
        return tabComplete(arg, GlyphPolicy.PUBLIC);
    }

    /**
     * Implements TextReplacementConfig.Condition.
     */
    private static PatternReplacementResult shouldReplace(MatchResult matchResult, int matchCount, int replaced) {
        String group = matchResult.group();
        String key = group.substring(1, group.length() - 1);
        return EMOJI_MAP.containsKey(key)
            ? PatternReplacementResult.REPLACE
            : PatternReplacementResult.CONTINUE; // skip
    }

    /**
     * Emoji replacer. Used to create the textReplacementConfig in replaceText().
     */
    private static ComponentLike replacer(MatchResult matchResult, GlyphPolicy glyphPolicy, boolean withTooltip) {
        String group = matchResult.group();
        String key = group.substring(1, group.length() - 1);
        Emoji emoji = EMOJI_MAP.get(key);
        return emoji != null && glyphPolicy.entails(emoji.glyphPolicy)
            ? (withTooltip
               ? emoji.componentWithTooltip
               : emoji.component)
            : text(group);
    }

    // Nullable!
    public static Emoji getEmoji(String name) {
        return EMOJI_MAP.get(name);
    }

    // NotNull!
    public static Component getComponent(String name, GlyphPolicy glyphPolicy) {
        Emoji emoji = EMOJI_MAP.get(name);
        return emoji != null && glyphPolicy.entails(emoji.glyphPolicy)
            ? emoji.component
            : empty();
    }

    public static Component getComponent(String name) {
        return getComponent(name, GlyphPolicy.HIDDEN);
    }

    public static int count() {
        return EMOJI_MAP.size();
    }

    public boolean isUnicode() {
        return enume instanceof Unicode;
    }

    public boolean isVanillaItems() {
        return enume instanceof VanillaItems;
    }

    public boolean isDefaultFont() {
        return enume instanceof DefaultFont;
    }

    public static List<Emoji> all() {
        return new ArrayList<>(EMOJI_MAP.values());
    }

    public boolean isHidden() {
        return glyphPolicy == GlyphPolicy.HIDDEN;
    }

    public boolean isPublic() {
        return glyphPolicy == GlyphPolicy.PUBLIC;
    }

    public static void dump(CommandSender sender) {
        Map<String, List<Emoji>> all = new HashMap<>();
        for (Emoji emoji : all()) {
            if (emoji.isHidden()) continue;
            all.computeIfAbsent(emoji.category, c -> new ArrayList<>()).add(emoji);
        }
        List<String> cats = new ArrayList<>(all.keySet());
        cats.sort(String.CASE_INSENSITIVE_ORDER);
        for (String category : cats) {
            List<Emoji> list = all.get(category);
            Collections.sort(list, (a, b) -> {
                    int v = String.CASE_INSENSITIVE_ORDER.compare(a.category, b.category);
                    if (v != 0) return v;
                    return String.CASE_INSENSITIVE_ORDER.compare(a.name, b.name);
                });
            List<Component> allc = new ArrayList<>(list.size());
            for (Emoji e : list) {
                allc.add(e.componentWithTooltip);
            }
            sender.sendMessage(join(separator(space()), allc));
        }
    }
}
