package com.cavetale.core.structure;

import com.cavetale.core.struct.Cuboid;
import org.bukkit.NamespacedKey;

public interface Structure {
    NamespacedKey getKey();

    Cuboid getBoundingBox();

    boolean isDiscovered();

    void setDiscovered(boolean value);
}
