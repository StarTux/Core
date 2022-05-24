package com.cavetale.core.money;

import com.cavetale.core.CorePlugin;
import java.util.UUID;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.bukkit.plugin.Plugin;

final class DefaultMoney implements Money {
    @Override
    public Plugin getPlugin() {
        return CorePlugin.getInstance();
    }

    @Override
    public boolean has(UUID uuid, double amount) {
        return false;
    }

    @Override
    public double get(UUID uuid) {
        return 0.0;
    }

    @Override
    public void get(UUID uuid, Consumer<Double> callback) {
        callback.accept(0.0);
    }

    @Override
    public boolean give(UUID uuid, double amount, Plugin plugin, String comment) {
        return false;
    }

    @Override
    public boolean give(UUID uuid, double amount) {
        return false;
    }

    @Override
    public void give(UUID uuid, double amount, Plugin plugin, String comment, Consumer<Boolean> callback) {
        callback.accept(false);
    }

    @Override
    public void give(UUID uuid, double amount, Consumer<Boolean> callback) {
        callback.accept(false);
    }

    @Override
    public boolean take(UUID uuid, double amount, Plugin plugin, String comment) {
        return false;
    }

    @Override
    public boolean take(UUID uuid, double amount) {
        return false;
    }

    @Override
    public void take(UUID uuid, double amount, Plugin plugin, String comment, Consumer<Boolean> callback) {
        callback.accept(false);
    }

    @Override
    public void take(UUID uuid, double amount, Consumer<Boolean> callback) {
        callback.accept(false);
    }

    @Override
    public String format(double amount) {
        return "" + amount;
    }

    @Override
    public Component toComponent(double amount) {
        return Component.text(format(amount));
    }

    @Override
    public void log(UUID uuid, double amount, Plugin plugin, String comment) {
    }
}
