package com.cavetale.core.friends;

import org.bukkit.inventory.ItemStack;

public final class Friends {
    public static ItemStack getDailyFriendshipGift() {
        return supplier.getDailyFriendshipGift();
    }

    private Friends() { }

    protected static FriendsSupplier supplier = FriendsSupplier.DEFAULT;
}
