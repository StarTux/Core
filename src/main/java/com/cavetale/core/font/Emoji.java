package com.cavetale.core.font;

import com.cavetale.core.CorePlugin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.MatchResult;
import lombok.Value;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
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
    public final Component tooltip;
    public final GlyphPolicy glyphPolicy;
    public final Component componentWithTooltip;

    private Emoji(final String name, final Component component, final Component tooltip, final GlyphPolicy glyphPolicy) {
        this.name = name;
        this.component = component;
        this.tooltip = tooltip;
        this.glyphPolicy = glyphPolicy;
        this.componentWithTooltip = component.hoverEvent(HoverEvent.showText(tooltip));
    }

    public static void init() {
        textReplacementConfigBuilder = TextReplacementConfig.builder().match("\\B:[0-9a-z_]+:\\B");
        for (DefaultFont defaultFont : DefaultFont.values()) {
            String name = defaultFont.name().toLowerCase();
            Component component = Component.text()
                .content(defaultFont.character + "")
                .color(NamedTextColor.WHITE)
                .font(Key.key("cavetale:default"))
                .build();
            Component tooltip = Component.text(camelCase(defaultFont.name()), NamedTextColor.WHITE);
            addEmoji(name, component, tooltip, defaultFont.policy);
        }
        for (VanillaItems vanillaItems : VanillaItems.values()) {
            String itemName = vanillaItems.material.isItem()
                ? new ItemStack(vanillaItems.material).getI18NDisplayName()
                : camelCase(vanillaItems.name());
            String name = vanillaItems.name().toLowerCase();
            Component component = Component.text()
                .content(vanillaItems.character + "")
                .color(NamedTextColor.WHITE)
                .font(Key.key("cavetale:default"))
                .build();
            Component tooltip = Component.text(itemName, NamedTextColor.WHITE);
            addEmoji(name, component, tooltip, vanillaItems.getPolicy());
        }
    }

    public static void addEmoji(String name, Component component, Component tooltip, GlyphPolicy policy) {
        if (EMOJI_MAP.containsKey(Objects.requireNonNull(name, "name=null"))) {
            CorePlugin.getInstance().getLogger().warning("Emoji: Duplicate " + name);
        }
        EMOJI_MAP.put(name, new Emoji(name,
                                      Objects.requireNonNull(component, "component=null"),
                                      Objects.requireNonNull(tooltip, "tooltip=null"),
                                      Objects.requireNonNull(policy, "policy=null")));
    }

    public static void addEmoji(String name, Component component, GlyphPolicy policy) {
        addEmoji(name, component, Component.empty(), policy);
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
}
