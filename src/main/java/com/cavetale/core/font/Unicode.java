package com.cavetale.core.font;

import java.util.function.Function;
import net.kyori.adventure.text.Component;

public enum Unicode {
    CENT('\u00A2'), // ¢
    COPYRIGHT('\u00A9'), // ©
    SUPER_2('\u00B2'), // ²
    SUPER_3('\u00B3'), // ³
    PILCROW('\u00B6'), // ¶
    SUPER_1('\u00B9'), // ¹
    ONE_QUARTER('\u00BC'), // ¼
    ONE_HALF('\u00BD'), // ½
    THREE_QUARTERS('\u00BE'), // ¾
    MULTIPLICATION('\u00D7'), // ×
    ALPHA('\u03B1'), // α
    BETA('\u03B2'), // β
    GAMMA('\u03B3'), // γ
    DELTA('\u03B4'), // δ
    EPSILON('\u03B5'), // ε
    ZETA('\u03B6'), // ζ
    ETA('\u03B7'), // η
    THETA('\u03B8'), // θ
    IOTA('\u03B9'), // ι
    KAPPA('\u03BA'), // κ
    LAMBDA('\u03BB'), // λ
    MU('\u03BC'), // μ
    NU('\u03BD'), // ν
    XI('\u03BE'), // ξ
    OMICRON('\u03BF'), // ο
    PI('\u03C0'), // π
    RHO('\u03C1'), // ρ
    FINAL_SIGMA('\u03C2'), // ς
    SIGMA('\u03C3'), // σ
    TAU('\u03C4'), // τ
    UPSILON('\u03C5'), // υ
    PHI('\u03C6'), // φ
    CHI('\u03C7'), // χ
    PSI('\u03C8'), // ψ
    OMEGA('\u03C9'), // ω
    ENDASH('\u2013'), // –
    EMDASH('\u2014'), // —
    BULLET_POINT('\u2022'), // •
    TRIANGULAR_BULLET('\u2023'), // ‣
    HYPHEN_BULLET('\u2043'), // ⁃
    REVERSE_PILCROW('\u204B'), // ⁋
    SUPER_0('\u2070'), // ⁰
    SUPER_4('\u2074'), // ⁴
    SUPER_5('\u2075'), // ⁵
    SUPER_6('\u2076'), // ⁶
    SUPER_7('\u2077'), // ⁷
    SUPER_8('\u2078'), // ⁸
    SUPER_9('\u2079'), // ⁹
    SUPER_PLUS('\u207A'), // ⁺
    SUPER_MINUS('\u207B'), // ⁻
    SUPER_EQ('\u207C'), // ⁼
    SUPER_LPAR('\u207D'), // ⁽
    SUPER_RPAR('\u207E'), // ⁾
    SUPER_N('\u207F'), // ⁿ
    SUB_0('\u2080'), // ₀
    SUB_1('\u2081'), // ₁
    SUB_2('\u2082'), // ₂
    SUB_3('\u2083'), // ₃
    SUB_4('\u2084'), // ₄
    SUB_5('\u2085'), // ₅
    SUB_6('\u2086'), // ₆
    SUB_7('\u2087'), // ₇
    SUB_8('\u2088'), // ₈
    SUB_9('\u2089'), // ₉
    SUB_PLUS('\u208A'), // ₊
    SUB_MINUS('\u208B'), // ₋
    SUB_EQ('\u208C'), // ₌
    SUB_LPAR('\u208D'), // ₍
    SUB_RPAR('\u208E'), // ₎
    EURO_CENT('\u20A0'), // ₠
    EURO('\u20AC'), // €
    TRADEMARK('\u2122'), // ™
    ARROW_LEFT('\u2190'), // ←
    ARROW_UP('\u2191'), // ↑
    ARROW_RIGHT('\u2192'), // →
    ARROW_DOWN('\u2193'), // ↓
    ARROW_LEFT_RIGHT('\u2194'), // ↔
    ARROW_UP_DOWN('\u2195'), // ↕
    ARROW_UP_LEFT('\u2196'), // ↖
    ARROW_UP_RIGHT('\u2197'), // ↗
    ARROW_DOWN_RIGHT('\u2198'), // ↘
    ARROW_DOWN_LEFT('\u2199'), // ↙
    FOR_ALL('\u2200'), // ∀
    COMPLEMENT('\u2201'), // ∁
    PARTIAL_DIFFERENTIAL('\u2202'), // ∂
    THERE_EXISTS('\u2203'), // ∃
    THERE_DOES_NOT_EXIST('\u2204'), // ∄
    EMPTY_SET('\u2205'), // ∅
    INCREMENT('\u2206'), // ∆
    NABLA('\u2207'), // ∇
    ELEMENT_OF('\u2208'), // ∈
    NOT_ELEMENT_OF('\u2209'), // ∉
    CONTAINS_MEMBER('\u220B'), // ∋
    MINUS_OR_PLUS('\u2213'), // ∓
    DOT_PLUS('\u2214'), // ∔
    ASTERISK_OPERATOR('\u2217'), // ∗
    RING_OPERATOR('\u2218'), // ∘
    BULLET_OPERATOR('\u2219'), // ∙
    SQUARE_ROOT('\u221A'), // √
    CUBE_ROOT('\u221B'), // ∛
    FOURTH_ROOT('\u221C'), // ∜
    INFINITY('\u221E'), // ∞
    LOGICAL_AND('\u2227'), // ∧
    LOGICAL_OR('\u2228'), // ∨
    INTERSECTION('\u2229'), // ∩
    UNION('\u222A'), // ∪
    INTEGRAL('\u222B'), // ∫
    LTE('\u2264'), // ≤
    GTE('\u2265'), // ≥
    DIAMOND_OPERATOR('\u22C4'), // ⋄
    DOT_OPERATOR('\u22C5'), // ⋅
    STAR_OPERATOR('\u22C6'), // ⋆
    WATCH('\u231A'), // ⌚
    HOURGLASS('\u231B'), // ⌛
    POWER('\u23FB'), // ⏻
    POWER_OFF('\u23FC'), // ⏼
    WHITE_BULLET('\u25E6'), // ◦
    SUN('\u2600'), // ☀
    CLOUD('\u2601'), // ☁
    STAR('\u2605'), // ★
    EMPTY_STAR('\u2606'), // ☆
    CHECKBOX('\u2610'), // ☐
    CHECKED_CHECKBOX('\u2611'), // ☑
    CROSSED_CHECKBOX('\u2612'), // ☒
    SHAMROCK('\u2618'), // ☘
    SKULL('\u2620'), // ☠
    RADIOACTIVE('\u2622'), // ☢
    BIOHAZARD('\u2623'), // ☣
    HAMMER_AND_SICKLE('\u262D'), // ☭
    PEACE('\u262E'), // ☮
    YIN_YANG('\u262F'), // ☯
    FROWN('\u2639'), // ☹
    SMILE('\u263A'), // ☺
    SMILE_INVERT('\u263B'), // ☻
    MOON('\u263D'), // ☽
    FEMALE('\u2640'), // ♀
    MALE('\u2642'), // ♂
    BLACK_SPADE_SUIT('\u2660'), // ♠
    WHITE_HEART_SUIT('\u2661'), // ♡
    WHITE_DIAMOND_SUIT('\u2662'), // ♢
    BLACK_CLUB_SUIT('\u2663'), // ♣
    WHITE_SPADE_SUIT('\u2664'), // ♤
    BLACK_HEART_SUIT('\u2665'), // ♥
    BLACK_DIAMOND_SUIT('\u2666'), // ♦
    WHITE_CLUB_SUIT('\u2667'), // ♧
    QUARTER_NOTE('\u2669'), // ♩
    EIGHTH_NOTE('\u266A'), // ♪
    BEAMED_EIGHTH_NOTES('\u266B'), // ♫
    BEAMED_SIXTEENTH_NOTES('\u266C'), // ♬
    DICE_1('\u2680'), // ⚀
    DICE_2('\u2681'), // ⚁
    DICE_3('\u2682'), // ⚂
    DICE_4('\u2683'), // ⚃
    DICE_5('\u2684'), // ⚄
    DICE_6('\u2685'), // ⚅
    HAMMER_AND_PICK('\u2692'), // ⚒
    CROSSED_SWORDS('\u2694'), // ⚔
    SCALES('\u2696'), // ⚖
    FLOWER('\u2698'), // ⚘
    ATOM('\u269B'), // ⚛
    CHECKERS_WHITE('\u26C0'), // ⛀
    CHECKERS_WHITE_KING('\u26C1'), // ⛁
    CHECKERS_BLACK('\u26C2'), // ⛂
    CHECKERS_BLACK_KING('\u26C3'), // ⛃
    CROSS_ON_SHIELD('\u26E8'), // ⛨
    CHECKMARK('\u2714'), // ✔
    CHECK_X2('\u2717'), // ✗
    CHECK_X('\u2718'), // ✘
    OUTLINED_GREEK_CROSS('\u2719'), // ✙
    HEAVY_GREEK_CROSS('\u271A'), // ✚
    LATIN_CROSS('\u271D'), // ✝
    SHADOWED_LATIN_CROSS('\u271E'), // ✞
    OUTLINED_LATIN_CROSS('\u271F'), // ✟
    BLACK_WHITE_FLORETTE('\u273E'), // ✾
    BLACK_FLORETTE('\u273F'), // ✿
    WHITE_FLORETTE('\u2740'), // ❀
    OUTLINED_FLORETTE('\u2741'), // ❁
    HEART('\u2764'); // ❤

    public final String key;
    public final char character;
    public final String string;
    public final Component component;

    Unicode(final char character) {
        this.key = name().toLowerCase();
        this.character = character;
        this.string = "" + character;
        this.component = Component.text(string);
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
        default: return null;
        }
    }

    public static String translate(String in, Function<Character, Unicode> translator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < in.length(); i += 1) {
            Unicode unicode = translator.apply(in.charAt(i));
            if (unicode != null) sb.append(unicode.character);
        }
        return sb.toString();
    }

    public static String superscript(int number) {
        return translate("" + number, Unicode::charToSuperscript);
    }

    public static String subscript(int number) {
        return translate("" + number, Unicode::charToSubscript);
    }

    /**
     * Sort this enum and print it to stdout. Throw if there's a
     * duplicate.
     */
    public static void main(String[] args) {
        Unicode[] array = Unicode.values();
        java.util.Arrays.sort(array, (a, b) -> Integer.compare((int) a.character, (int) b.character));
        Unicode previous = null;
        for (int i = 0; i < array.length; i += 1) {
            Unicode u = array[i];
            // Duplicate detection
            if (previous != null && u.character == previous.character) {
                throw new IllegalStateException(u + "=" + previous);
            }
            previous = u;
            // Print
            String show = Integer.toHexString((int) u.character).toUpperCase();
            while (show.length() < 4) show = "0" + show;
            System.out.print(u.name() + "('\\u" + show + "')");
            System.out.print(i < array.length - 1 ? "," : ";");
            System.out.println(" // " + u.character);
        }
    }
}
