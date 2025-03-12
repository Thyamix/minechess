package com.thyamix;

import com.thyamix.commands.CommandHandler;
import com.thyamix.events.EventHandler;
import com.thyamix.game.GameHandler;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.InstanceContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);
    static MinecraftServer minecraftServer;
    static InstanceContainer Lobby;

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        minecraftServer = MinecraftServer.init();

        MojangAuth.init();

        Lobby = GameHandler.initInstanceContainer(true);

        EventHandler.handleListeners();

        minecraftServer.start("0.0.0.0", 25565);

        CommandHandler.initCommands();
    }
}