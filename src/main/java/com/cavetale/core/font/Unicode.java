package com.cavetale.core.font;

import java.util.function.Function;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;

/**
 * Unicode does not implement Font because it has several differences.
 * It does not reference an image file and does not have a made up
 * character.  As such, it is of no interest to the resource pack.
 */
public enum Unicode implements ComponentLike {
    CENT((char) 0x00A2), // ¢
    COPYRIGHT((char) 0x00A9), // ©
    SUPER_2((char) 0x00B2), // ²
    SUPER_3((char) 0x00B3), // ³
    PILCROW((char) 0x00B6), // ¶
    SUPER_1((char) 0x00B9), // ¹
    ONE_QUARTER((char) 0x00BC), // ¼
    ONE_HALF((char) 0x00BD), // ½
    THREE_QUARTERS((char) 0x00BE), // ¾
    MULTIPLICATION((char) 0x00D7), // ×
    ALPHA((char) 0x03B1), // α
    BETA((char) 0x03B2), // β
    GAMMA((char) 0x03B3), // γ
    DELTA((char) 0x03B4), // δ
    EPSILON((char) 0x03B5), // ε
    ZETA((char) 0x03B6), // ζ
    ETA((char) 0x03B7), // η
    THETA((char) 0x03B8), // θ
    IOTA((char) 0x03B9), // ι
    KAPPA((char) 0x03BA), // κ
    LAMBDA((char) 0x03BB), // λ
    MU((char) 0x03BC), // μ
    NU((char) 0x03BD), // ν
    XI((char) 0x03BE), // ξ
    OMICRON((char) 0x03BF), // ο
    PI((char) 0x03C0), // π
    RHO((char) 0x03C1), // ρ
    FINAL_SIGMA((char) 0x03C2), // ς
    SIGMA((char) 0x03C3), // σ
    TAU((char) 0x03C4), // τ
    UPSILON((char) 0x03C5), // υ
    PHI((char) 0x03C6), // φ
    CHI((char) 0x03C7), // χ
    PSI((char) 0x03C8), // ψ
    OMEGA((char) 0x03C9), // ω
    ENDASH((char) 0x2013), // –
    EMDASH((char) 0x2014), // —
    BULLET_POINT((char) 0x2022), // •
    TRIANGULAR_BULLET((char) 0x2023), // ‣
    HYPHEN_BULLET((char) 0x2043), // ⁃
    REVERSE_PILCROW((char) 0x204B), // ⁋
    SUPER_0((char) 0x2070), // ⁰
    SUPER_4((char) 0x2074), // ⁴
    SUPER_5((char) 0x2075), // ⁵
    SUPER_6((char) 0x2076), // ⁶
    SUPER_7((char) 0x2077), // ⁷
    SUPER_8((char) 0x2078), // ⁸
    SUPER_9((char) 0x2079), // ⁹
    SUPER_PLUS((char) 0x207A), // ⁺
    SUPER_MINUS((char) 0x207B), // ⁻
    SUPER_EQ((char) 0x207C), // ⁼
    SUPER_LPAR((char) 0x207D), // ⁽
    SUPER_RPAR((char) 0x207E), // ⁾
    SUPER_N((char) 0x207F), // ⁿ
    SUB_0((char) 0x2080), // ₀
    SUB_1((char) 0x2081), // ₁
    SUB_2((char) 0x2082), // ₂
    SUB_3((char) 0x2083), // ₃
    SUB_4((char) 0x2084), // ₄
    SUB_5((char) 0x2085), // ₅
    SUB_6((char) 0x2086), // ₆
    SUB_7((char) 0x2087), // ₇
    SUB_8((char) 0x2088), // ₈
    SUB_9((char) 0x2089), // ₉
    SUB_PLUS((char) 0x208A), // ₊
    SUB_MINUS((char) 0x208B), // ₋
    SUB_EQ((char) 0x208C), // ₌
    SUB_LPAR((char) 0x208D), // ₍
    SUB_RPAR((char) 0x208E), // ₎
    EURO_CENT((char) 0x20A0), // ₠
    EURO((char) 0x20AC), // €
    TRADEMARK((char) 0x2122), // ™
    ARROW_LEFT((char) 0x2190), // ←
    ARROW_UP((char) 0x2191), // ↑
    ARROW_RIGHT((char) 0x2192), // →
    ARROW_DOWN((char) 0x2193), // ↓
    ARROW_LEFT_RIGHT((char) 0x2194), // ↔
    ARROW_UP_DOWN((char) 0x2195), // ↕
    ARROW_UP_LEFT((char) 0x2196), // ↖
    ARROW_UP_RIGHT((char) 0x2197), // ↗
    ARROW_DOWN_RIGHT((char) 0x2198), // ↘
    ARROW_DOWN_LEFT((char) 0x2199), // ↙
    FOR_ALL((char) 0x2200), // ∀
    COMPLEMENT((char) 0x2201), // ∁
    PARTIAL_DIFFERENTIAL((char) 0x2202), // ∂
    THERE_EXISTS((char) 0x2203), // ∃
    THERE_DOES_NOT_EXIST((char) 0x2204), // ∄
    EMPTY_SET((char) 0x2205), // ∅
    INCREMENT((char) 0x2206), // ∆
    NABLA((char) 0x2207), // ∇
    ELEMENT_OF((char) 0x2208), // ∈
    NOT_ELEMENT_OF((char) 0x2209), // ∉
    CONTAINS_MEMBER((char) 0x220B), // ∋
    MINUS_OR_PLUS((char) 0x2213), // ∓
    DOT_PLUS((char) 0x2214), // ∔
    ASTERISK_OPERATOR((char) 0x2217), // ∗
    RING_OPERATOR((char) 0x2218), // ∘
    BULLET_OPERATOR((char) 0x2219), // ∙
    SQUARE_ROOT((char) 0x221A), // √
    CUBE_ROOT((char) 0x221B), // ∛
    FOURTH_ROOT((char) 0x221C), // ∜
    INFINITY((char) 0x221E), // ∞
    LOGICAL_AND((char) 0x2227), // ∧
    LOGICAL_OR((char) 0x2228), // ∨
    INTERSECTION((char) 0x2229), // ∩
    UNION((char) 0x222A), // ∪
    INTEGRAL((char) 0x222B), // ∫
    LTE((char) 0x2264), // ≤
    GTE((char) 0x2265), // ≥
    DIAMOND_OPERATOR((char) 0x22C4), // ⋄
    DOT_OPERATOR((char) 0x22C5), // ⋅
    STAR_OPERATOR((char) 0x22C6), // ⋆
    WATCH((char) 0x231A), // ⌚
    HOURGLASS((char) 0x231B), // ⌛
    POWER((char) 0x23FB), // ⏻
    POWER_OFF((char) 0x23FC), // ⏼
    WHITE_BULLET((char) 0x25E6), // ◦
    SUN((char) 0x2600), // ☀
    CLOUD((char) 0x2601), // ☁
    STAR((char) 0x2605), // ★
    EMPTY_STAR((char) 0x2606), // ☆
    CHECKBOX((char) 0x2610), // ☐
    CHECKED_CHECKBOX((char) 0x2611), // ☑
    CROSSED_CHECKBOX((char) 0x2612), // ☒
    SHAMROCK((char) 0x2618), // ☘
    SKULL((char) 0x2620), // ☠
    RADIOACTIVE((char) 0x2622), // ☢
    BIOHAZARD((char) 0x2623), // ☣
    HAMMER_AND_SICKLE((char) 0x262D), // ☭
    PEACE((char) 0x262E), // ☮
    YIN_YANG((char) 0x262F), // ☯
    FROWN((char) 0x2639), // ☹
    SMILE((char) 0x263A), // ☺
    SMILE_INVERT((char) 0x263B), // ☻
    MOON((char) 0x263D), // ☽
    FEMALE((char) 0x2640), // ♀
    MALE((char) 0x2642), // ♂
    BLACK_SPADE_SUIT((char) 0x2660), // ♠
    WHITE_HEART_SUIT((char) 0x2661), // ♡
    WHITE_DIAMOND_SUIT((char) 0x2662), // ♢
    BLACK_CLUB_SUIT((char) 0x2663), // ♣
    WHITE_SPADE_SUIT((char) 0x2664), // ♤
    BLACK_HEART_SUIT((char) 0x2665), // ♥
    BLACK_DIAMOND_SUIT((char) 0x2666), // ♦
    WHITE_CLUB_SUIT((char) 0x2667), // ♧
    QUARTER_NOTE((char) 0x2669), // ♩
    EIGHTH_NOTE((char) 0x266A), // ♪
    BEAMED_EIGHTH_NOTES((char) 0x266B), // ♫
    BEAMED_SIXTEENTH_NOTES((char) 0x266C), // ♬
    DICE_1((char) 0x2680), // ⚀
    DICE_2((char) 0x2681), // ⚁
    DICE_3((char) 0x2682), // ⚂
    DICE_4((char) 0x2683), // ⚃
    DICE_5((char) 0x2684), // ⚄
    DICE_6((char) 0x2685), // ⚅
    HAMMER_AND_PICK((char) 0x2692), // ⚒
    CROSSED_SWORDS((char) 0x2694), // ⚔
    SCALES((char) 0x2696), // ⚖
    FLOWER((char) 0x2698), // ⚘
    ATOM((char) 0x269B), // ⚛
    DOUBLE_FEMALE((char) 0x26A2), // ⚢
    DOUBLE_MALE((char) 0x26A3), // ⚣
    CHECKERS_WHITE((char) 0x26C0), // ⛀
    CHECKERS_WHITE_KING((char) 0x26C1), // ⛁
    CHECKERS_BLACK((char) 0x26C2), // ⛂
    CHECKERS_BLACK_KING((char) 0x26C3), // ⛃
    CROSS_ON_SHIELD((char) 0x26E8), // ⛨
    CHECKMARK((char) 0x2714), // ✔
    CHECK_X2((char) 0x2717), // ✗
    CHECK_X((char) 0x2718), // ✘
    OUTLINED_GREEK_CROSS((char) 0x2719), // ✙
    HEAVY_GREEK_CROSS((char) 0x271A), // ✚
    LATIN_CROSS((char) 0x271D), // ✝
    SHADOWED_LATIN_CROSS((char) 0x271E), // ✞
    OUTLINED_LATIN_CROSS((char) 0x271F), // ✟
    BLACK_WHITE_FLORETTE((char) 0x273E), // ✾
    BLACK_FLORETTE((char) 0x273F), // ✿
    WHITE_FLORETTE((char) 0x2740), // ❀
    OUTLINED_FLORETTE((char) 0x2741), // ❁
    HEART((char) 0x2764), // ❤
    SMALLA((char) 0x1D00, Category.ALPHABET), // ᴀ
    SMALLB((char) 0x0299, Category.ALPHABET), // ʙ
    SMALLC((char) 0x1D04, Category.ALPHABET), // ᴄ
    SMALLD((char) 0x1D05, Category.ALPHABET), // ᴅ
    SMALLE((char) 0x1D07, Category.ALPHABET), // ᴇ
    SMALLF((char) 0xA730, Category.ALPHABET), // ꜰ
    SMALLG((char) 0x0262, Category.ALPHABET), // ɢ
    SMALLH((char) 0x029C, Category.ALPHABET), // ʜ
    SMALLI((char) 0x026A, Category.ALPHABET), // ɪ
    SMALLJ((char) 0x1D0A, Category.ALPHABET), // ᴊ
    SMALLK((char) 0x1D0B, Category.ALPHABET), // ᴋ
    SMALLL((char) 0x029F, Category.ALPHABET), // ʟ
    SMALLM((char) 0x1D0D, Category.ALPHABET), // ᴍ
    SMALLN((char) 0x0274, Category.ALPHABET), // ɴ
    SMALLO((char) 0x1D0F, Category.ALPHABET), // ᴏ
    SMALLP((char) 0x1D18, Category.ALPHABET), // ᴘ
    SMALLQ((char) 0x01EB, Category.ALPHABET), // ǫ
    SMALLR((char) 0x0280, Category.ALPHABET), // ʀ
    SMALLS((char) 0x0455, Category.ALPHABET), // ѕ
    SMALLT((char) 0x1D1B, Category.ALPHABET), // ᴛ
    SMALLU((char) 0x1D1C, Category.ALPHABET), // ᴜ
    SMALLV((char) 0x1D20, Category.ALPHABET), // ᴠ
    SMALLW((char) 0x1D21, Category.ALPHABET), // ᴡ
    SMALLX((char) 0x0445, Category.ALPHABET), // х
    SMALLY((char) 0x028F, Category.ALPHABET), // ʏ
    SMALLZ((char) 0x1D22, Category.ALPHABET), // ᴢ
    ;

    public final String key;
    public final char character;
    public final String string;
    public final Component component;
    public final Category category;

    /**
     * Categories exist to maintain the ordering of groups with
     * non-sequential hex codes.  Specifically, this was added for the
     * small letters, which are gathered from various Unicode tables.
     */
    public enum Category {
        DEFAULT,
        ALPHABET;
    }

    Unicode(final char character, final Category category) {
        this.key = name().toLowerCase();
        this.character = character;
        this.string = "" + character;
        this.component = Component.text(string);
        this.category = category;
    }

    Unicode(final char character) {
        this(character, Category.DEFAULT);
    }

    public static Unicode charToSuperscript(final char c) {
        switch (c) {
        case '0': return SUPER_0;
        case '1': return SUPER_1;
        case '2': return SUPER_2;
        case '3': return SUPER_3;
        case '4': return SUPER_4;
        case '5': return SUPER_5;
        case '6': return SUPER_6;
        case '7': return SUPER_7;
        case '8': return SUPER_8;
        case '9': return SUPER_9;
        case '-': return SUPER_MINUS;
        case '+': return SUPER_PLUS;
        case '=': return SUPER_EQ;
        case '(': return SUPER_LPAR;
        case ')': return SUPER_RPAR;
        default: return null;
        }
    }

    public static Unicode charToSubscript(final char c) {
        switch (c) {
        case '0': return SUB_0;
        case '1': return SUB_1;
        case '2': return SUB_2;
        case '3': return SUB_3;
        case '4': return SUB_4;
        case '5': return SUB_5;
        case '6': return SUB_6;
        case '7': return SUB_7;
        case '8': return SUB_8;
        case '9': return SUB_9;
        case '-': return SUB_MINUS;
        case '+': return SUB_PLUS;
        case '=': return SUB_EQ;
        case '(': return SUB_LPAR;
        case ')': return SUB_RPAR;
        default:
            if (c >= 'a' && c <= 'z') {
                return values()[(int) c - (int) 'a' + SMALLA.ordinal()];
            }
            return null;
        }
    }

    public static Unicode charToTinyFont(final char c) {
        return c >= 'a' && c <= 'z'
            ? values()[(int) c - (int) 'a' + SMALLA.ordinal()]
            : null;
    }

    public static String translate(String in, Function<Character, Unicode> translator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i += 1) {
            Unicode unicode = translator.apply(in.charAt(i));
            if (unicode != null) sb.append(unicode.character);
        }
        return sb.toString();
    }

    public static String translateOrKeep(String in, Function<Character, Unicode> translator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i += 1) {
            char c = in.charAt(i);
            if (c == '\u00A7') {
                sb.append(c);
                i += 1;
                if (i < in.length()) sb.append(in.charAt(i));
                continue;
            }
            Unicode unicode = translator.apply(in.charAt(i));
            sb.append(unicode != null ? unicode.character : c);
        }
        return sb.toString();
    }

    public static String superscript(int number) {
        return translate("" + number, Unicode::charToSuperscript);
    }

    public static String subscript(int number) {
        return translate("" + number, Unicode::charToSubscript);
    }

    public static String subscript(String in) {
        return translateOrKeep(in, Unicode::charToSubscript);
    }

    public static String tiny(String in) {
        return translateOrKeep(in, Unicode::charToTinyFont);
    }

    @Override
    public Component asComponent() {
        return component;
    }
}
