package com.thyamix.events;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSkinInitEvent;
import net.minestom.server.instance.InstanceContainer;

public class PlayJoinListener {

    public static void handleJoin(InstanceContainer instanceContainer) {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 2, 0));
            player.setGameMode(GameMode.CREATIVE);
        });
        globalEventHandler.addListener(PlayerSkinInitEvent.class, event -> {
            event.getPlayer().setSkin(PlayerSkin.fromUsername(event.getPlayer().getUsername()));
        });
    }
}
