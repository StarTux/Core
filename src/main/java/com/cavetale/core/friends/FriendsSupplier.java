package com.cavetale.core.friends;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface FriendsSupplier {
    FriendsSupplier DEFAULT = new FriendsSupplier() {
            @Override
            public ItemStack getDailyFriendshipGift() {
                return new ItemStack(Material.AIR);
            }
        };

    ItemStack getDailyFriendshipGift();

    default void register() {
        Friends.supplier = this;
    }

    default void unregister() {
        if (Friends.supplier != this) return;
        Friends.supplier = DEFAULT;
    }
}
