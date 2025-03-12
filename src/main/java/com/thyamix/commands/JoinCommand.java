package com.thyamix.commands;

import com.thyamix.game.GameHandler;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;

public class JoinCommand extends Command {

    public JoinCommand() {
        super("join");

        setDefaultExecutor(((sender, context) -> {
            if (sender instanceof Player player) {
                player.sendMessage("Finding a game.");
                GameHandler.gameJoinRequest(player);
            } else {
                sender.sendMessage("Only players can join game.");
            }
        }));
    }
}
