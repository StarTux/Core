package com.cavetale.core.event.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import static com.cavetale.core.CorePlugin.plugin;

/**
 * Called when a player is to receive a number of items.  Clients
 * should use this opportunity to remove the items to place them into
 * their storage.
 *
 * The expected responder to this event is MassStorage.
 *
 * All items that were removed must have their amount updated.
 * Editing the list is not enough because the update will not carry
 * over to a backing inventory.
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
            if (item == null || item.isEmpty()) continue;
            items.add(item);
        }
    }

    /**
     * All-round carefree convenience method which implements our
     * desired policy.
     *
     * First, we try to stack the new items with existing ones in the
     * player inventory.
     *
     * Then, we try to put them in empty hotbar slots, if the player
     * does not already have such an item in their inventory.
     *
     * Then, the event is called. Here, we expect our plugins
     * (especially MassStorage) to do their magic.
     *
     * Then, we try to add all the remaining items to the player
     * inventory.
     *
     * Finally, as a last resort, items are dropped to the ground near
     * the player.
     */
    public static void receiveItems(Player player, List<ItemStack> items) {
        PlayerReceiveItemsEvent event = new PlayerReceiveItemsEvent(player, items);
        event.stackWithPlayerInventory();
        event.putInEmptyHotbar();
        event.callEvent();
        event.giveItems();
        event.dropItems();
    }

    /**
     * All-round carefree convenience method.
     * Call the event with the inventory, then drop the remaining items.
     */
    public static void receiveInventory(Player player, Inventory inventory) {
        final List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : inventory) {
            if (item == null || item.isEmpty()) continue;
            items.add(item);
        }
        receiveItems(player, items);
    }

    /**
     * Attempt to add the items to the player inventory.  This will
     * remove or nullify items from the list, but will never drop
     * anything.
     *
     * This could be used before calling the event to ensure that
     * items are preferrably added to the inventory, rather than
     * absorbed by MS.
     */
    public void giveItems() {
        for (int i = 0; i < items.size(); i += 1) {
            ItemStack itemStack = items.get(i);
            if (itemStack == null || itemStack.isEmpty()) continue;
            Collection<ItemStack> remainder = player.getInventory().addItem(itemStack).values();
            final ItemStack retour = !remainder.isEmpty() ? remainder.iterator().next() : null;
            itemStack.setAmount(retour != null ? retour.getAmount() : 0);
        }
    }

    /**
     * Attempt to stack all items with already existing items in the
     * player inventory.
     */
    public void stackWithPlayerInventory() {
        for (ItemStack itemStack : items) {
            if (itemStack == null || itemStack.isEmpty()) continue;
            for (int i = 0; i < 40 && itemStack.getAmount() > 0; i += 1) {
                if (i >= 36 && i <= 39) continue; // armor slots
                final ItemStack slot = player.getInventory().getItem(i);
                if (slot == null || slot.isEmpty()) {
                    continue;
                }
                if (!itemStack.isSimilar(slot)) continue;
                final int stacking = Math.min(itemStack.getAmount(), slot.getMaxStackSize() - slot.getAmount());
                if (stacking <= 0) continue;
                itemStack.subtract(stacking);
                slot.add(stacking);
            }
        }
    }

    /**
     * Attempt to place all items in the empty hotbar slot, provided
     * that item does not already exist in the player inventory.
     */
    public void putInEmptyHotbar() {
        for (ItemStack itemStack : items) {
            if (itemStack == null || itemStack.isEmpty()) continue;
            if (player.getInventory().containsAtLeast(itemStack, 1)) continue;
            for (int i = 0; i < 9; i += 1) {
                final ItemStack slot = player.getInventory().getItem(i);
                if (slot != null && !slot.isEmpty()) continue;
                player.getInventory().setItem(i, itemStack.clone());
                itemStack.setAmount(0);
            }
        }
    }

    /**
     * Drop all remaining items.  Items will be dropped with
     * ownership, invulerable, not able to be picked up by mobs.
     * PlayerDropItemEvent, which ignores its own cancellation state.
     *
     * All dropping happens on the next game ticks in order to avoid
     * infinite loops in case this event is the result of an item
     * drop.
     */
    public void dropItems() {
        for (int i = 0; i < items.size(); i += 1) {
            final ItemStack itemStack = items.get(i);
            if (itemStack == null || itemStack.isEmpty()) continue;
            final ItemStack drop = itemStack.clone();
            itemStack.setAmount(0);
            Bukkit.getScheduler().runTask(plugin(), () -> {
                    final Item item = player.getWorld().dropItem(
                        player.getLocation(),
                        drop,
                        it -> {
                            it.setOwner(player.getUniqueId());
                            it.setCanMobPickup(false);
                            it.setOwner(player.getUniqueId());
                            it.setPickupDelay(0);
                            it.setInvulnerable(true);
                        }
                    );
                    new PlayerDropItemEvent(player, item).callEvent();
                });
        }
    }

    /**
     * Get the unmodifiable item list.
     */
    public List<ItemStack> getItems() {
        final List<ItemStack> result = new ArrayList<>();
        for (ItemStack itemStack : items) {
            if (itemStack == null || itemStack.isEmpty()) continue;
            result.add(itemStack);
        }
        return result;
    }

    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (item != null && !item.isEmpty()) return false;
        }
        return true;
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
