package com.thyamix.events;

import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.item.ItemDropEvent;

public class InventoryListener {
    public static void inventoryDropListeners() {
        GlobalEventHandler globalEventHandler = new GlobalEventHandler();
        globalEventHandler.addListener(ItemDropEvent.class, itemDropEvent -> {
            itemDropEvent.setCancelled(true);
        });
    }

    public static void inventoryPreClickListeners() {
        GlobalEventHandler globalEventHandler = new GlobalEventHandler();
        globalEventHandler.addListener(InventoryPreClickEvent.class, inventoryPreClickEvent -> {
            inventoryPreClickEvent.setCancelled(true);
        });
    }

    public static void inventoryClickListeners() {
        GlobalEventHandler globalEventHandler = new GlobalEventHandler();
        globalEventHandler.addListener(InventoryItemChangeEvent.class, inventoryItemChangeEvent -> {
        });
    }
}
