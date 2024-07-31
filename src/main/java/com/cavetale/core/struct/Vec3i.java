package com.cavetale.core.struct;

import lombok.Value;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

@Value
public final class Vec3i {
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    public static final Vec3i ONE = new Vec3i(1, 1, 1);
    public final int x;
    public final int y;
    public final int z;

    public static Vec3i of(int nx, int ny, int nz) {
        return new Vec3i(nx, ny, nz);
    }

    public static Vec3i of(Block block) {
        return new Vec3i(block.getX(), block.getY(), block.getZ());
    }

    public static Vec3i of(Location location) {
        return new Vec3i(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Vec3i of(BlockFace blockFace) {
        return new Vec3i(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
    }

    public static Vec3i of(Vector vector) {
        return new Vec3i(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public Block toBlock(World world) {
        return world.getBlockAt(x, y, z);
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public Location toCenterLocation(World world) {
        return new Location(world, x, y, z).add(0.5, 0.5, 0.5);
    }

    public Location toCenterFloorLocation(World world) {
        return new Location(world, x, y, z).add(0.5, 0.0, 0.5);
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    public boolean equals(final int ox, final int oy, final int oz) {
        return this.x == ox
            && this.y == oy
            && this.z == oz;
    }

    public Vec3i add(int dx, int dy, int dz) {
        return of(x + dx, y + dy, z + dz);
    }

    public Vec3i add(Vec3i b) {
        return of(x + b.x, y + b.y, z + b.z);
    }

    public Vec3i subtract(Vec3i b) {
        return of(x - b.x, y - b.y, z - b.z);
    }

    public Vec3i multiply(int mul) {
        return of(x * mul, y * mul, z * mul);
    }

    public Vec3i negate() {
        return of(-x, -y, -z);
    }

    public int maxDistance(Vec3i other) {
        return Math.max(Math.abs(other.x - x),
                        Math.max(Math.abs(other.y - y),
                                 Math.abs(other.z - z)));
    }

    public int maxHorizontalDistance(Vec3i other) {
        return Math.max(Math.abs(other.x - x),
                        Math.abs(other.z - z));
    }

    public Vec2i blockToChunk() {
        return new Vec2i(x >> 4, z >> 4);
    }

    public boolean contains(Location loc) {
        return x == loc.getBlockX() && y == loc.getBlockY() && z == loc.getBlockZ();
    }

    public int distanceSquared(Vec3i other) {
        final int dx = other.x - x;
        final int dy = other.y - y;
        final int dz = other.z - z;
        return dx * dx + dy * dy + dz * dz;
    }

    public double distance(Vec3i other) {
        return Math.sqrt(distanceSquared(other));
    }

    public int roundedDistance(Vec3i other) {
        return (int) Math.round(distance(other));
    }

    public int lengthSquared() {
        return x * x + y * y + z * z;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public int roundedLength() {
        return (int) Math.round(length());
    }

    public Vec2i toVec2i() {
        return Vec2i.of(x, z);
    }

    @Override
    public String toString() {
        return "" + x + "," + y + "," + z;
    }
}
