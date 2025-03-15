package com.thyamix.events;

import com.thyamix.game.ChessBoard;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.item.ItemStack;


public class PlayerLeftClickListener {
    public static void handleLeftClick(ChessBoard chessBoard) {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerUseItemEvent.class, playerUseItemEvent -> {
           if (playerUseItemEvent.getItemStack() == ItemStack.AIR) {
               return;
           }
        });

    }
}
