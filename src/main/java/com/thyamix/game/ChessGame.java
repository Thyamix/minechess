package com.thyamix.game;

import com.thyamix.pieces.ChessPiece;
import net.minestom.server.entity.Player;
import net.minestom.server.instance.InstanceContainer;

public class ChessGame {

    public final InstanceContainer instanceContainer;
    private final ChessBoard chessBoard;
    public Player player1;
    public Player player2;
    private boolean isWhiteTurn = true;
    public boolean running;
    public boolean complete;

    public ChessGame(GameHandler gameHandler) {
        this.instanceContainer = gameHandler.initInstanceContainer(false);
        this.chessBoard = new ChessBoard(this);
    }

    private void start() {
        player1.sendMessage("Starting...");
        refreshMoves();
    }


    private void refreshMoves() {
        for (ChessPiece piece : this.chessBoard.chessPieces) {
            piece.getMoves();
        }
    }


    public void join(Player player, InstanceContainer instanceContainer) {
        player.setInstance(instanceContainer);
        player.sendMessage("Joining");
        if (this.player1 == null) {
            this.player1 = player;
            player.sendMessage("You are playing white.");
            this.start();
        } else if (this.player2 == null) {
            this.player2 = player;
            player.sendMessage("You are playing black.");

            this.start();
        }
    }
}
