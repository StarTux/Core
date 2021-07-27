package com.cavetale.core.font;

public enum Unicode {
    CENT('\u00A2'), // ¢
    COPYRIGHT('\u00A9'), // ©
    SUPER_2('\u00B2'), // ²
    SUPER_3('\u00B3'), // ³
    PILCROW('\u00B6'), // ¶
    ONE_QUARTER('\u00BC'), // ¼
    ONE_HALF('\u00BD'), // ½
    THREE_QUARTERS('\u00BE'), // ¾
    MULTIPLICATION('\u00D7'), // ×
    PI('\u03C0'), // π
    BULLET_POINT('\u2022'), // •
    TRIANGULAR_BULLET('\u2023'), // ‣
    HYPHEN_BULLET('\u2043'), // ⁃
    REVERSE_PILCROW('\u204B'), // ⁋
    SUPER_4('\u2074'), // ⁴
    SUPER_5('\u2075'), // ⁵
    SUPER_6('\u2076'), // ⁶
    SUPER_7('\u2077'), // ⁷
    SUPER_8('\u2078'), // ⁸
    SUPER_9('\u2079'), // ⁹
    SUPER_PLUS('\u207A'), // ⁺
    SUPER_MINUS('\u207B'), // ⁻
    SUPER_EQ('\u207C'), // ⁼
    SUPER_L_PAR('\u207D'), // ⁽
    SUPER_R_PAR('\u207E'), // ⁾
    SUPER_N('\u207F'), // ⁿ
    EURO_CENT('\u20A0'), // ₠
    EURO('\u20AC'), // €
    ARROW_UP('\u2110'), // ℐ
    TRADEMARK('\u2122'), // ™
    ARROW_LEFT('\u2190'), // ←
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
    ATOM('\u269B'), // ⚛
    CHECKERS_WHITE('\u26C0'), // ⛀
    CHECKERS_WHITE_KING('\u26C1'), // ⛁
    CHECKERS_BLACK('\u26C2'), // ⛂
    CHECKERS_BLACK_KING('\u26C3'), // ⛃
    CROSS_ON_SHIELD('\u26E8'), // ⛨
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

    Unicode(final char character) {
        this.key = name().toLowerCase();
        this.character = character;
    }

    /**
     * Sort this enum and print it to stdout. Throw if there's a
     * duplicate.
     */
    public static void main(String[] args) {
        Unicode[] array = Unicode.values();
        java.util.Arrays.sort(array, (a, b) -> Integer.compare((int) a.character, (int) b.character));
        Unicode previous = null;
        for (Unicode u : array) {
            // Duplicate detection
            if (previous != null && u.character == previous.character) {
                throw new IllegalStateException(u + "=" + previous);
            }
            previous = u;
            // Print
            String show = Integer.toHexString((int) u.character).toUpperCase();
            while (show.length() < 4) show = "0" + show;
            System.out.println(u.name() + "('\\u" + show + "'), // " + u.character);
        }
    }
}
