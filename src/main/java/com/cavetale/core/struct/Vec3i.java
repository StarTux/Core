package com.cavetale.core.struct;

import lombok.Value;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

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

    public static Vec3i of(BlockFace blockFace) {
        return new Vec3i(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
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

    public Vec3i add(int dx, int dy, int dz) {
        return new Vec3i(x + dx, y + dy, z + dz);
    }

    public Vec3i add(Vec3i b) {
        return new Vec3i(x + b.x, y + b.y, z + b.z);
    }

    public Vec3i subtract(Vec3i b) {
        return new Vec3i(x - b.x, y - b.y, z - b.z);
    }

    public Vec3i multiply(int mul) {
        return new Vec3i(x * mul, y * mul, z * mul);
    }

    public Vec3i negate() {
        return new Vec3i(-x, -y, -z);
    }

    @Override
    public String toString() {
        return "" + x + "," + y + "," + z;
    }
}
