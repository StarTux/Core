package com.cavetale.core.money;

import com.cavetale.core.CorePlugin;
import java.util.UUID;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;

/**
 * Service for handling money.
 */
public interface Money {
    default void register() {
        Companion.money = this;
        CorePlugin.getInstance().getLogger().info("Money registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.money = Companion.DEFAULT;
    }

    static Money get() {
        return Companion.money;
    }

    Plugin getPlugin();

    /**
     * Check if a player has enough balance.
     * @param uuid the player uuid
     * @param the required balance
     * @return true if they have enough, false otherwise
     */
    boolean has(UUID uuid, double amount);

    /**
     * Get a player balance.
     * @param uuid the player uuid
     * @return the balance
     */
    double get(UUID uuid);

    /**
     * Get player balance asynchronously.
     * @param uuid the player uuid
     * @param callback a consumer accepting the balance
     */
    void get(UUID uuid, Consumer<Double> callback);

    /**
     * Add money to a player's bank account and log it.
     * @param uuid the player uuid
     * @param amount the money amount
     * @param plugin the issuing plugin
     * @param comment the comment
     * @return true if successful
     */
    boolean give(UUID uuid, double amount, Plugin plugin, String comment);

    /**
     * Add money to a player's bank account without logging.
     * @param uuid the player uuid
     * @param amount the money amount
     * @return true if successful
     */
    boolean give(UUID uuid, double amount);

    /**
     * Add money asynchronously to a player's bank account and log it.
     * @param uuid the player uuid
     * @param amount the money amount
     * @param plugin the issuing plugin
     * @param comment the comment
     * @param callback a consumer accepting the result
     */
    void give(UUID uuid, double amount, Plugin plugin, String comment, Consumer<Boolean> callback);

    /**
     * Add money asynchronously to a player's bank account without
     * logging.
     * @param uuid the player uuid
     * @param amount the money amount
     * @param callback a consumer accepting the result
     */
    void give(UUID uuid, double amount, Consumer<Boolean> callback);

    /**
     * Subtract money from a player's bank account and log it.
     * @param uuid the player uuid
     * @param amount the money amount
     * @param plugin the issuing plugin
     * @param comment the comment
     * @return true if successful, false if the player balance is
     *   insufficient
     */
    boolean take(UUID uuid, double amount, Plugin plugin, String comment);

    /**
     * Subtract money from a player's bank account without logging.
     * @param uuid the player uuid
     * @param amount the money amount
     * @return true if successful, false if the player balance is
     *   insufficient
     */
    boolean take(UUID uuid, double amount);

    /**
     * Subtract money asynchronously from a player's bank account and
     * log it.
     * @param uuid the player uuid
     * @param amount the money amount
     * @param plugin the issuing plugin
     * @param comment the comment
     * @param callback a consumer accepting the result
     */
    void take(UUID uuid, double amount, Plugin plugin, String comment, Consumer<Boolean> callback);

    /**
     * Subtract money asynchronously from a player's bank account
     * without logging.
     * @param uuid the player uuid
     * @param amount the money amount
     * @param callback a consumer accepting the result
     */
    void take(UUID uuid, double amount, Consumer<Boolean> callback);

    String format(double amount);

    Component toComponent(double amount);

    void log(UUID uuid, double amount, Plugin plugin, String comment);
}

final class Companion {
    protected static final Money DEFAULT = new DefaultMoney();
    protected static Money money = DEFAULT;

    private Companion() { }
}
