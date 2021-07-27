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
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

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
    public final GlyphPolicy glyphPolicy;
    public final Component componentWithTooltip;
    public final Enum enume;

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

    private Emoji(final String name, final Component component, final Component tooltip, final GlyphPolicy glyphPolicy, final Enum enume) {
        this.name = name;
        this.component = component;
        this.glyphPolicy = glyphPolicy;
        this.componentWithTooltip = !Objects.equals(tooltip, Component.empty())
            ? component.hoverEvent(HoverEvent.showText(tooltip))
            : component;
        this.enume = enume;
    }

    public static void init() {
        textReplacementConfigBuilder = TextReplacementConfig.builder()
            .match(":[0-9a-z_]+:")
            .condition(Emoji::shouldReplace);
        for (DefaultFont defaultFont : DefaultFont.values()) {
            String name = defaultFont.name().toLowerCase();
            Component tooltip = Component.text(camelCase(defaultFont.name()), NamedTextColor.WHITE);
            addEmoji(name, defaultFont.component, tooltip, defaultFont.policy, defaultFont);
        }
        for (VanillaItems vanillaItems : VanillaItems.values()) {
            String itemName = vanillaItems.material.isItem()
                ? new ItemStack(vanillaItems.material).getI18NDisplayName()
                : camelCase(vanillaItems.name());
            String name = vanillaItems.name().toLowerCase();
            Component tooltip = Component.text(itemName, NamedTextColor.WHITE);
            addEmoji(name, vanillaItems.component, tooltip, vanillaItems.getPolicy(), vanillaItems);
        }
        for (Unicode unicode : Unicode.values()) {
            addEmoji("u" + unicode.key, Component.text(unicode.character), GlyphPolicy.PUBLIC, unicode);
        }
    }

    public static void addEmoji(String name, Component component, Component tooltip, GlyphPolicy policy, Enum enume) {
        if (EMOJI_MAP.containsKey(Objects.requireNonNull(name, "name=null"))) {
            CorePlugin.getInstance().getLogger().warning("Emoji: Duplicate " + name);
        }
        EMOJI_MAP.put(name, new Emoji(name,
                                      Objects.requireNonNull(component, "component=null"),
                                      Objects.requireNonNull(tooltip, "tooltip=null"),
                                      Objects.requireNonNull(policy, "policy=null"),
                                      enume));
    }

    public static void addEmoji(String name, Component component, GlyphPolicy policy, Enum enume) {
        addEmoji(name, component, Component.empty(), policy, enume);
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
        if (!in.contains(":")) return Component.text(in);
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
                head = head + ":" + key;
                tail = tail2.substring(index2);
                continue;
            }
            head = head + tail.substring(0, index);
            if (!head.isEmpty()) {
                list.add(Component.text(head));
                head = "";
            }
            list.add(withTooltip ? emoji.componentWithTooltip : emoji.component);
            tail = tail2.substring(index2 + 1);
        } while (!tail.isEmpty());
        if (list.isEmpty()) return Component.text(in);
        if (!head.isEmpty() || !tail.isEmpty()) {
            list.add(Component.text(head + tail));
        }
        return Component.text().append(list);
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
            : Component.text(group);
    }

    private static String camelCase(String msg) {
        StringBuilder sb = new StringBuilder();
        for (String tok : msg.split("_")) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(tok.substring(0, 1).toUpperCase());
            sb.append(tok.substring(1).toLowerCase());
        }
        return sb.toString();
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
            : Component.empty();
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
        List<Emoji> all = Emoji.all();
        Collections.sort(all, (a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a.name, b.name));
        List<Component> allc = new ArrayList<>(all.size());
        TextComponent.Builder cb = Component.text();
        for (Emoji emoji : all) {
            cb.append(Component.space());
            if (!emoji.isHidden()) cb.append(emoji.componentWithTooltip);
        }
        sender.sendMessage(cb.build());
    }
}
