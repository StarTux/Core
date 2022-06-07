package com.cavetale.core.item;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public interface ItemFinder {
    default void register() {
        Companion.ITEM_FINDER_LIST.add(this);
    }

    default void unregister() {
        Companion.ITEM_FINDER_LIST.remove(this);
    }

    ItemKind findItem(ItemStack itemStack);

    ItemKind findItem(NamespacedKey key);

    static ItemFinder get() {
        return Companion.DEFAULT_ITEM_FINDER;
    }
}

final class Companion {
    static final ArrayList<ItemFinder> ITEM_FINDER_LIST = new ArrayList<>();
    static final ItemFinder DEFAULT_ITEM_FINDER = new ItemFinder() {
            @Override
            public void register() {
                throw new IllegalStateException("DefaultItemFinder");
            }

            @Override
            public void unregister() {
                throw new IllegalStateException("DefaultItemFinder");
            }

            @Override
            public ItemKind findItem(ItemStack itemStack) {
                for (ItemFinder itemFinder : Companion.ITEM_FINDER_LIST) {
                    ItemKind result = itemFinder.findItem(itemStack);
                    if (result != null) return result;
                }
                return new MinecraftItemKind(itemStack.getType());
            }

            @Override
            public ItemKind findItem(NamespacedKey key) {
                for (ItemFinder itemFinder : Companion.ITEM_FINDER_LIST) {
                    ItemKind result = itemFinder.findItem(key);
                    if (result != null) return result;
                }
                if (key.getNamespace().equals(NamespacedKey.MINECRAFT)) {
                    try {
                        return new MinecraftItemKind(Material.valueOf(key.getKey().toUpperCase()));
                    } catch (IllegalArgumentException iae) { }
                }
                return null;
            }
        };

    private Companion() { }
}
