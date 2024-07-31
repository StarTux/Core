package com.cavetale.core.structure;

import com.cavetale.core.struct.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;

public interface Structure {
    int getInternalId();

    String getWorldName();

    default World getWorld() {
        return Bukkit.getWorld(getWorldName());
    }

    NamespacedKey getKey();

    Cuboid getBoundingBox();

    boolean isDiscovered();

    void setDiscovered(boolean value);
}
