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
        return of(x + dx, z + dz);
    }

    public Vec2i add(Vec2i b) {
        return of(x + b.x, z + b.z);
    }

    public Vec2i subtract(Vec2i b) {
        return of(x - b.x, z - b.z);
    }

    public int lengthSquared() {
        return x * x + z * z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public int roundedLength() {
        return (int) Math.round(length());
    }

    public int distanceSquared(Vec2i other) {
        final int dx = other.x - x;
        final int dz = other.z - z;
        return dx * dx + dz * dz;
    }

    public double distance(Vec2i other) {
        return Math.sqrt(distanceSquared(other));
    }

    public int roundedDistance(Vec2i other) {
        return (int) Math.round(distance(other));
    }

    @Override
    public String toString() {
        return "" + x + "," + z;
    }

    public boolean equals(final int ox, final int oz) {
        return x == ox
            && z == oz;
    }
}
