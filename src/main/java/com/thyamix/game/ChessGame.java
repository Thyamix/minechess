package com.thyamix.game;

import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;

public class ChessGame {

    public InstanceContainer instanceContainer;
    ChessBoard chessBoard;
    Player player1;
    Player player2;
    public boolean running;
    public boolean compelete;

    public ChessGame() {
        this.instanceContainer = GameHandler.initInstanceContainer(false);
        this.chessBoard = new ChessBoard(this);
    }

    public void playerJoin(Player player) {
        if (this.running) {
            player.sendMessage("Game already full, wait for it to finish to join.");
            return;
        }
        if (this.player1 == null) {
            this.player1 = player;
        } else if (this.player2 == null) {
            this.player2 = player;
        } else {
            return;
        }

        if (this.player1 != null && this.player2 != null) {
            this.running = true;
            this.start();
        }
    }

    private void start() {

    }

    public void join(Player player, InstanceContainer instanceContainer) {
        player.setInstance(instanceContainer);
        player.sendMessage("Joining");
    }
}
