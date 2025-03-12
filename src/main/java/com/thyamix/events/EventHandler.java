package com.thyamix.events;

import com.thyamix.game.GameHandler;

public class EventHandler {

    public static void handleListeners() {
        PlayJoinListener.handleJoin(GameHandler.getLobby());
        BreakBlockListener.handleBlockBreak();
        PlaceBlockListener.handleBlockPlace();
    }
}
