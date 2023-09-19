package com.cavetale.core.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import static com.cavetale.core.CorePlugin.plugin;

/**
 * Utility to load all chunks within simulation distance, and keep
 * them loaded, then running the callback.
 * The standard use may look like this (See RemotePlayerWrapper):
 * The cleanup function must be called once the chunks are no longer
 * needed.
 *
 * PlayerTeleportUtil.loadNearbyChunks(location, util -> {
 *     player.teleport(location);
 *     util.cleanup();
 *   });
 *
 * This class will try to clean up after itself in case the cleanup
 * method is never called.
 */
@RequiredArgsConstructor
public final class PlayerTeleportUtil {
    private final Location location;
    private final Consumer<PlayerTeleportUtil> callback;
    private int lock;
    private final ArrayList<Chunk> chunks = new ArrayList<>();

    private static final record ChunkVector(String world, int x, int z) { }

    private static final HashMap<ChunkVector, Integer> CHUNK_LOAD_MAP = new HashMap<>();

    private static void increaseChunkLoad(Chunk chunk) {
        final ChunkVector cv = new ChunkVector(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
        final int value = CHUNK_LOAD_MAP.getOrDefault(cv, 0);
        CHUNK_LOAD_MAP.put(cv, value + 1);
        if (value == 0) {
            chunk.addPluginChunkTicket(plugin());
        }
    }

    private static void decreaseChunkLoad(Chunk chunk) {
        final ChunkVector cv = new ChunkVector(chunk.getWorld().getName(), chunk.getX(), chunk.getZ());
        final int value = CHUNK_LOAD_MAP.getOrDefault(cv, 0);
        if (value == 0) return;
        if (value <= 1) {
            CHUNK_LOAD_MAP.remove(cv);
            chunk.removePluginChunkTicket(plugin());
        } else {
            CHUNK_LOAD_MAP.put(cv, value - 1);
        }
    }

    public static void loadNearbyChunks(Location location, Consumer<PlayerTeleportUtil> callback) {
        new PlayerTeleportUtil(location, callback).start();
    }

    private void start() {
        final World world = location.getWorld();
        final int d = world.getSimulationDistance(); // off by one?
        final int dd = d * d;
        final int cx = location.getBlockX() >> 4;
        final int cz = location.getBlockZ() >> 4;
        lock = 1;
        for (int dz = -d; dz <= d; dz += 1) {
            for (int dx = -d; dx <= d; dx += 1) {
                if (dx * dx + dz * dz > dd) continue; // circular
                final int x = cx + dx;
                final int z = dz + dz;
                lock++;
                world.getChunkAtAsync(x, z, (Consumer<Chunk>) chunk -> {
                        increaseChunkLoad(chunk);
                        chunks.add(chunk);
                        if ((--lock) == 0) onAllChunksLoaded();
                    });
            }
        }
        if ((--lock) == 0) onAllChunksLoaded();
        Bukkit.getScheduler().runTaskLater(plugin(), this::cleanup, 20L * 10L);
    }

    private void onAllChunksLoaded() {
        callback.accept(this);
    }

    public void cleanup() {
        if (chunks.isEmpty()) return;
        Bukkit.getScheduler().runTask(plugin(), () -> {
                for (Chunk chunk : chunks) {
                    decreaseChunkLoad(chunk);
                }
                chunks.clear();
            });
    }

    public static void debug(CommandSender sender) {
        sender.sendMessage("" + CHUNK_LOAD_MAP);
        sender.sendMessage("Total " + CHUNK_LOAD_MAP.size() + " loaded via ticket");
    }
}
