package com.cavetale.core.structure;

import com.cavetale.core.struct.Cuboid;
import lombok.Value;
import org.bukkit.NamespacedKey;

@Value
public final class Structure {
    private final NamespacedKey key;
    private final Cuboid boundingBox;
}
