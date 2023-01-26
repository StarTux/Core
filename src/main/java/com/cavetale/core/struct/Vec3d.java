package com.cavetale.core.struct;

import lombok.Value;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

@Value
public final class Vec3d {
    public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
    public static final Vec3d ONE = new Vec3d(1, 1, 1);
    public final double x;
    public final double y;
    public final double z;

    public static Vec3d of(double nx, double ny, double nz) {
        return new Vec3d(nx, ny, nz);
    }

    public static Vec3d of(Block block) {
        return new Vec3d(block.getX(), block.getY(), block.getZ());
    }

    public static Vec3d of(Location location) {
        return new Vec3d(location.getX(), location.getY(), location.getZ());
    }

    public Vec3i toVec3i() {
        return new Vec3i((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public Vec3d add(double dx, double dy, double dz) {
        return new Vec3d(x + dx, y + dy, z + dz);
    }

    public Vec3d add(Vec3d b) {
        return new Vec3d(x + b.x, y + b.y, z + b.z);
    }

    public Vec3d subtract(Vec3d b) {
        return new Vec3d(x - b.x, y - b.y, z - b.z);
    }

    public Vec3d multiply(double mul) {
        return new Vec3d(x * mul, y * mul, z * mul);
    }

    public Vec3d negate() {
        return new Vec3d(-x, -y, -z);
    }

    public double maxDistance(Vec3d other) {
        return Math.max(Math.abs(other.x - x),
                        Math.max(Math.abs(other.y - y),
                                 Math.abs(other.z - z)));
    }

    public double maxHorizontalDistance(Vec3d other) {
        return Math.max(Math.abs(other.x - x),
                        Math.abs(other.z - z));
    }

    public double distanceSquared(Vec3d other) {
        double dx = other.x - x;
        double dy = other.y - y;
        double dz = other.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    @Override
    public String toString() {
        return String.format("%.2f,%.2f,%.2f", x, y, z);
    }
}
