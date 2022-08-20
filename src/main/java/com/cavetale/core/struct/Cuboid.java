package com.cavetale.core.struct;

import com.cavetale.core.command.CommandWarn;
import com.cavetale.core.selection.SelectionProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.Value;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Value
public final class Cuboid {
    public static final Cuboid ZERO = new Cuboid(0, 0, 0, 0, 0, 0);
    public final int ax;
    public final int ay;
    public final int az;
    public final int bx;
    public final int by;
    public final int bz;

    public static Cuboid selectionOf(Player player) {
        return SelectionProvider.get().getCuboidSelection(player);
    }

    public static Cuboid requireSelectionOf(Player player) {
        Cuboid result = selectionOf(player);
        if (result == null) throw new CommandWarn("Make a selection first!");
        return result;
    }

    public List<Integer> toList() {
        return List.of(ax, ay, az, bx, by, bz);
    }

    public static Cuboid ofList(List<Number> list) {
        if (list.size() != 6) throw new IllegalArgumentException("" + list);
        return new Cuboid(list.get(0).intValue(), list.get(1).intValue(), list.get(2).intValue(),
                          list.get(3).intValue(), list.get(4).intValue(), list.get(5).intValue());
    }

    public boolean contains(int x, int y, int z) {
        return x >= ax && x <= bx
            && y >= ay && y <= by
            && z >= az && z <= bz;
    }

    public boolean contains(Block block) {
        return contains(block.getX(), block.getY(), block.getZ());
    }

    public boolean contains(Vec3i v) {
        return contains(v.x, v.y, v.z);
    }

    public boolean contains(Location location) {
        return contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean contains(Cuboid other) {
        return ax <= other.ax && bx >= other.bx
            && ay <= other.ay && by >= other.by
            && az <= other.az && bz >= other.bz;
    }

    @Override
    public String toString() {
        return ax + "," + ay + "," + az + "-" + bx + "," + by + "," + bz;
    }

    public Vec3i getSize() {
        return new Vec3i(getSizeX(), getSizeY(), getSizeZ());
    }

    public int getSizeX() {
        return bx - ax + 1;
    }

    public int getSizeY() {
        return by - ay + 1;
    }

    public int getSizeZ() {
        return bz - az + 1;
    }

    public int getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    /**
     * Highlight this cuboid. This may be called every tick and with
     * the provided arguments will do the rest. A balanced interval
     * and scale are required to make the highlight contiguous while
     * reducint lag.
     * @param the current time in ticks
     * @param interval interval between ticks
     * @param scale how many inbetween dots to make over time
     * @param callback method to call for every point
     * @return true if the callback was called (probably many times),
     *   false if we're waiting for the interval.
     */
    public boolean highlight(World world, int timer, int interval, int scale, Consumer<Location> callback) {
        if (timer % interval != 0) return false;
        double offset = (double) ((timer / interval) % scale) / (double) scale;
        return highlight(world, offset, callback);
    }

    /**
     * Highlight this cuboid. This is a utility function for the other highlight method but may be called on its own, probably with an offset of 0.
     * @param world the world
     * @param offset the offset to highlight, between each corner point and the next
     * @param callback will be called for each point
     */
    public boolean highlight(World world, double offset, Consumer<Location> callback) {
        if (!world.isChunkLoaded(ax >> 4, az >> 4)) return false;
        Block origin = world.getBlockAt(ax, ay, az);
        Location loc = origin.getLocation();
        int sizeX = getSizeX();
        int sizeY = getSizeY();
        int sizeZ = getSizeZ();
        for (int y = 0; y < sizeY; y += 1) {
            double dy = (double) y + offset;
            callback.accept(loc.clone().add(0, dy, 0));
            callback.accept(loc.clone().add(0, dy, sizeZ));
            callback.accept(loc.clone().add(sizeX, dy, 0));
            callback.accept(loc.clone().add(sizeX, dy, sizeZ));
        }
        for (int z = 0; z < sizeZ; z += 1) {
            double dz = (double) z + offset;
            callback.accept(loc.clone().add(0, 0, dz));
            callback.accept(loc.clone().add(0, sizeY, dz));
            callback.accept(loc.clone().add(sizeX, 0, dz));
            callback.accept(loc.clone().add(sizeX, sizeY, dz));
        }
        for (int x = 0; x < sizeX; x += 1) {
            double dx = (double) x + offset;
            callback.accept(loc.clone().add(dx, 0, 0));
            callback.accept(loc.clone().add(dx, 0, sizeZ));
            callback.accept(loc.clone().add(dx, sizeY, 0));
            callback.accept(loc.clone().add(dx, sizeY, sizeZ));
        }
        return true;
    }

    public Vec3i getMin() {
        return new Vec3i(ax, ay, az);
    }

    public Vec3i getMax() {
        return new Vec3i(bx, by, bz);
    }

    public List<Vec3i> enumerate() {
        List<Vec3i> result = new ArrayList<>(getVolume());
        for (int y = ay; y <= by; y += 1) {
            for (int z = az; z <= bz; z += 1) {
                for (int x = ax; x <= bx; x += 1) {
                    result.add(new Vec3i(x, y, z));
                }
            }
        }
        return result;
    }

    public boolean overlaps(Cuboid other) {
        boolean x = other.ax <= this.bx && other.bx >= this.ax;
        boolean y = other.ay <= this.by && other.by >= this.ay;
        boolean z = other.az <= this.bz && other.bz >= this.az;
        return x && y && z;
    }

    /**
     * Move the entire box.
     */
    public Cuboid shift(int dx, int dy, int dz) {
        return new Cuboid(ax + dx, ay + dy, az + dz,
                          bx + dx, by + dy, bz + dz);
    }

    public Cuboid shift(Vec3i d) {
        return shift(d.x, d.y, d.z);
    }

    /**
     * Grow in one direction per axis.
     */
    public Cuboid expand(int dx, int dy, int dz) {
        return new Cuboid(ax + Math.min(0, dx), ay + Math.min(0, dy), az + Math.min(0, dz),
                          bx + Math.max(0, dx), by + Math.max(0, dy), bz + Math.max(0, dz));
    }

    /**
     * Grow along all axes.
     */
    public Cuboid outset(int dx, int dy, int dz) {
        return new Cuboid(ax - dx, ay - dy, az - dz,
                          bx + dx, by + dy, bz + dz);
    }

    /**
     * Apply the same operation to all coordinates.
     */
    public Cuboid applyCoords(Function<Integer, Integer> fun) {
        return new Cuboid(fun.apply(ax), fun.apply(ay), fun.apply(az),
                          fun.apply(bx), fun.apply(by), fun.apply(bz));
    }

    /**
     * Apply an operation to the lower coords and another to the upper
     * coords.
     */
    public Cuboid applyCoords(Function<Integer, Integer> lfun, Function<Integer, Integer> ufun) {
        return new Cuboid(lfun.apply(ax), lfun.apply(ay), lfun.apply(az),
                          ufun.apply(bx), ufun.apply(by), ufun.apply(bz));
    }

    /**
     * Turn all block coordinates into chunk coordinates.
     */
    public Cuboid blockToChunk() {
        return new Cuboid(ax >> 4, ay >> 4, az >> 4,
                          bx >> 4, by >> 4, bz >> 4);
    }

    public Vec3i getCenter() {
        return new Vec3i((ax + bx) / 2, (ay + by) / 2, (az + bz) / 2);
    }
}
