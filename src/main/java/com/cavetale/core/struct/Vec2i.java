package com.cavetale.core.struct;

import lombok.Value;
import org.bukkit.Chunk;

@Value
public final class Vec2i {
    public static final Vec2i ZERO = new Vec2i(0, 0);
    public final int x;
    public final int z;

    public static Vec2i of(int nx, int nz) {
        return new Vec2i(nx, nz);
    }

    public static Vec2i of(Chunk chunk) {
        return new Vec2i(chunk.getX(), chunk.getZ());
    }

    public Vec2i add(int dx, int dz) {
        return new Vec2i(x + dx, z + dz);
    }

    public Vec2i add(Vec2i b) {
        return new Vec2i(x + b.x, z + b.z);
    }

    @Override
    public String toString() {
        return "" + x + "," + z;
    }
}
