package com.cavetale.core.perm;

import com.cavetale.core.CorePlugin;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.Plugin;

public interface Perm {
    default void register() {
        Companion.perm = this;
        CorePlugin.getInstance().getLogger().info("Perm registered: " + getClass().getName());
    }

    default void unregister() {
        Companion.perm = DefaultPerm.INSTANCE;
    }

    static Perm get() {
        return Companion.perm;
    }

    Plugin getPlugin();

    boolean has(UUID uuid, String permission);

    boolean set(UUID uuid, String permission, boolean value);

    boolean unset(UUID uuid, String permission);

    boolean isInGroup(UUID uuid, String groupName);

    Collection<String> getGroups(UUID uuid);

    Collection<String> getAllGroups(UUID uuid);

    Map<String, Boolean> getPerms(UUID uuid);

    boolean removeGroup(UUID uuid, String group);

    boolean addGroup(UUID uuid, String group);

    boolean replaceGroup(UUID uuid, String oldGroup, String newGroup);

    int getLevel(UUID uuid);

    int getLevelProgress(UUID uuid);

    void addLevelProgress(UUID uuid);

    List<String> getGroupNames();
}

final class Companion {
    private Companion() { };

    protected static Perm perm = DefaultPerm.INSTANCE;
}
