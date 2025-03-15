package com.thyamix;

import com.thyamix.commands.CommandHandler;
import com.thyamix.events.EventHandler;
import com.thyamix.game.GameHandler;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;

public class Server {
    private final MinecraftServer minecraftServer = MinecraftServer.init();
    private final GameHandler gameHandler = new GameHandler();

    public void startServer() {
        MojangAuth.init();

        EventHandler.handleListeners(this.gameHandler);

        this.minecraftServer.start("0.0.0.0", 25565);

        CommandHandler.initCommands(this.gameHandler);
    }
}
