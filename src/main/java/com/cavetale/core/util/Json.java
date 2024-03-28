package com.cavetale.core.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public final class Json {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public static final Gson PRETTY = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
    private final JavaPlugin plugin;

    public static <T> T load(final File file, Class<T> type, Supplier<T> dfl) {
        if (!file.exists()) {
            return dfl.get();
        }
        try (FileReader fr = new FileReader(file)) {
            final T result = GSON.fromJson(fr, type);
            return result != null ? result : dfl.get();
        } catch (FileNotFoundException fnfr) {
            return dfl.get();
        } catch (IOException ioe) {
            System.err.println("File: " + file);
            ioe.printStackTrace();
            return dfl.get();
        }
    }

    public static <T> T load(final File file, Class<T> type) {
        return load(file, type, () -> null);
    }

    public static void save(final File file, Object obj, boolean pretty) {
        try (FileWriter fw = new FileWriter(file)) {
            Gson gs = pretty ? PRETTY : GSON;
            gs.toJson(obj, fw);
        } catch (IOException ioe) {
            throw new IllegalStateException("Saving " + file, ioe);
        }
    }

    public static void save(final File file, Object obj) {
        save(file, obj, false);
    }

    public static String serialize(Object obj) {
        return GSON.toJson(obj);
    }

    public static String prettyPrint(Object obj) {
        return PRETTY.toJson(obj);
    }

    public static <T> T deserialize(String json, Class<T> type) {
        return deserialize(json, type, () -> null);
    }

    public static <T> T deserialize(String json, Class<T> type, Supplier<T> dfl) {
        if (json == null) return dfl.get();
        try {
            final T result = GSON.fromJson(json, type);
            return result != null
                ? result
                : dfl.get();
        } catch (Exception e) {
            System.err.println("Input: " + json);
            e.printStackTrace();
            return dfl.get();
        }
    }

    public <T> T load(String path, Class<T> type, Supplier<T> dfl) {
        File file = new File(plugin.getDataFolder(), path);
        return load(file, type, dfl);
    }

    public void save(String path, Object obj, boolean pretty) {
        File file = new File(plugin.getDataFolder(), path);
        save(file, obj, pretty);
    }

    public static String serializeComponent(Component component) {
        try {
            return GsonComponentSerializer.gson().serialize(component);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Component deserializeComponent(String in) {
        try {
            return in != null
                ? GsonComponentSerializer.gson().deserialize(in)
                : Component.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Component.empty();
        }
    }

    public static <T> T clone(T object, Class<T> type) {
        return deserialize(serialize(object), type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T object) {
        return clone(object, (Class<T>) object.getClass());
    }
}
