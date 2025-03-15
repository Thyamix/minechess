package com.thyamix.commands;

import com.thyamix.game.GameHandler;
import net.minestom.server.MinecraftServer;

public class CommandHandler {

    public static void initCommands (GameHandler gameHandler) {
        MinecraftServer.getCommandManager().register(new JoinCommand(gameHandler));
    }
}
