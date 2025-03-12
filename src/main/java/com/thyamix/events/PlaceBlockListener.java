package com.thyamix.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;

public class PlaceBlockListener {
    public static void handleBlockPlace() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockPlaceEvent.class, event -> {
            event.setCancelled(true);
        });
    }
}
