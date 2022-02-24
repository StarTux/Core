package com.cavetale.core.perm;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class DefaultPerm implements Perm {
    protected static final DefaultPerm INSTANCE = new DefaultPerm();

    @Override
    public boolean has(UUID uuid, String permission) {
        return false;
    }

    @Override
    public boolean isInGroup(UUID uuid, String groupName) {
        return false;
    }

    @Override
    public Collection<String> getGroups(UUID uuid) {
        return List.of();
    }

    @Override
    public Collection<String> getAllGroups(UUID uuid) {
        return List.of();
    }

    @Override
    public Map<String, Boolean> getPerms(UUID uuid) {
        return Map.of();
    }

    @Override
    public boolean removeGroup(UUID uuid, String group) {
        return false;
    }

    @Override
    public boolean addGroup(UUID uuid, String group) {
        return false;
    }

    @Override
    public boolean replaceGroup(UUID uuid, String oldGroup, String newGroup) {
        return false;
    }
}
