package com.thyamix.events;

import com.thyamix.game.GameHandler;

public class EventHandler {
    public static void handleListeners(GameHandler gameHandler) {
        PlayJoinListener.handleJoin(gameHandler.getLobby());
        BreakBlockListener.handleBlockBreak();
        PlaceBlockListener.handleBlockPlace();
        InventoryListener.inventoryPreClickListeners();
        InventoryListener.inventoryDropListeners();
    }
}
