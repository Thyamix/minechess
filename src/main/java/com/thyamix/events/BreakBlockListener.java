package com.thyamix.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockBreakEvent;

public class BreakBlockListener {

    public static void handleBlockBreak() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerBlockBreakEvent.class, event -> {
            event.setCancelled(true);
        });
    }
}
