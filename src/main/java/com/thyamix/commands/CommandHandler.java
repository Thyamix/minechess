package com.thyamix.commands;

import net.minestom.server.MinecraftServer;

public class CommandHandler {

    public static void initCommands () {
        MinecraftServer.getCommandManager().register(new JoinCommand());
    }
}
