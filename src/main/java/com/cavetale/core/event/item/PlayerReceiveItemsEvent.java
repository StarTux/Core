package com.cavetale.core.event.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Called when a player is to receive a number of items.  Clients
 * should use this opportunity to remove the items to place them into
 * their storage, such as MassStorage or Stash.
 *
 * Stored items must be removed from the list, or better, reduced in
 * amount via the subtract method.
 *
 * Remaining items will likely be dropped.  There is a utility method
 * to do so.
 */
@Getter
public final class PlayerReceiveItemsEvent extends Event {
    @NonNull private final Player player;
    @NonNull private final List<ItemStack> items;

    public PlayerReceiveItemsEvent(final Player player, final List<ItemStack> items) {
        this.player = player;
        this.items = new ArrayList<>(items);
    }

    /**
     * Convenience constructor where the items are stored in an
     * inventory.  All non-air items will be placed in the item list.
     * The inventory will not be cleared!
     */
    public PlayerReceiveItemsEvent(final Player player, final Inventory inventory) {
        this.player = player;
        this.items = new ArrayList<>();
        for (ItemStack item : inventory) {
            if (item == null || item.getType().isAir()) continue;
            items.add(item);
        }
    }

    /**
     * All-round carefree convenience method.
     * Call the event with all the items, then dro the remaining items.
     */
    public static void receiveItems(Player player, List<ItemStack> items) {
        PlayerReceiveItemsEvent event = new PlayerReceiveItemsEvent(player, items);
        event.callEvent();
        event.dropItems();
    }

    /**
     * All-round carefree convenience method.
     * Call the event with the inventory, then dro the remaining items.
     */
    public static void receiveInventory(Player player, Inventory inventory) {
        PlayerReceiveItemsEvent event = new PlayerReceiveItemsEvent(player, inventory);
        event.callEvent();
        event.dropItems();
    }

    /**
     * Attempt to add the items to the player inventory.  This will
     * remove or nullify items from the list, but will never drop
     * anything.
     * This could be used before calling the event to ensure that
     * items are preferrably added to the inventory, rather than
     * absorbed by MS.
     */
    public void giveItems() {
        for (int i = 0; i < items.size(); i += 1) {
            ItemStack itemStack = items.get(i);
            if (itemStack == null || itemStack.getType().isAir()) continue;
            Collection<ItemStack> remainder = player.getInventory().addItem(itemStack).values();
            items.set(i, !remainder.isEmpty() ? remainder.iterator().next() : null);
        }
    }

    /**
     * Drop all remaining items.  Items will be dropped with
     * ownership, invulerable, not able to be picked up by mobs, with
     * PlayerDropItemEvent.  The latter will cancel its cancellation
     * state.
     * Before dropping any item, attempt to add it to the player
     * inventory.
     */
    public void dropItems() {
        for (ItemStack itemStack : items) {
            if (itemStack == null || itemStack.getType().isAir()) continue;
            for (ItemStack drop : player.getInventory().addItem(itemStack).values()) {
                Item item = player.getWorld().dropItem(player.getLocation(), drop);
                item.setOwner(player.getUniqueId());
                item.setCanMobPickup(false);
                item.setOwner(player.getUniqueId());
                item.setPickupDelay(0);
                item.setInvulnerable(true);
                new PlayerDropItemEvent(player, item).callEvent();
            }
        }
        items.clear();
    }

    /**
     * Get the item list.  The returned list will be a modifiable
     * ArrayList.
     * Beware: Any entry may be null or an AIR item.
     */
    public List<ItemStack> getItems() {
        return items;
    }

    /**
     * Required by Event.
     */
    @Getter private static HandlerList handlerList = new HandlerList();

    /**
     * Required by Event.
     */
    @Override public HandlerList getHandlers() {
        return handlerList;
    }
}
