package com.thyamix.commands;

import com.thyamix.game.GameHandler;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class JoinCommand extends Command {

    public JoinCommand(GameHandler gameHandler) {
        super("join");

        setDefaultExecutor(((sender, context) -> {
            if (sender instanceof Player player) {
                if (player.getInstance() == gameHandler.getLobby()) {
                    player.sendMessage("Finding a game.");
                    gameHandler.gameJoinRequest(player);
                } else {
                    player.sendMessage("Already in game.");
                }
            } else {
                sender.sendMessage("Only players can join game.");
            }
        }));
    }
}
