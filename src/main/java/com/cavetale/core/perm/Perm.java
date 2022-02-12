package com.cavetale.core.perm;

import com.cavetale.core.CorePlugin;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface Perm {
    default void register() {
        Holder.perm = this;
        CorePlugin.getInstance().getLogger().info("Perm registered: " + getClass().getName());
    }

    default void unregister() {
        Holder.perm = null;
    }

    static Perm get() {
        return Holder.perm;
    }

    boolean has(UUID uuid, String permission);

    boolean isInGroup(UUID uuid, String groupName);

    Collection<String> getGroups(UUID uuid);

    Collection<String> getAllGroups(UUID uuid);

    Map<String, Boolean> getPerms(UUID uuid);

    boolean removeGroup(UUID uuid, String group);

    boolean addGroup(UUID uuid, String group);

    boolean replaceGroup(UUID uuid, String oldGroup, String newGroup);
}

final class Holder {
    private Holder() { };

    protected static Perm perm;
}
