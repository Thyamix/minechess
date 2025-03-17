package com.thyamix.game;

import com.thyamix.pieces.ChessPiece;
import com.thyamix.utility.PiecePosition;
import com.thyamix.utility.RayTraceGridSelector;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerUseItemEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.Optional;

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
        handleSelect();
    }

    private void start() {
        player1.sendMessage("Starting...");
        refreshMoves();
    }


    public void refreshMoves() {
        for (ChessPiece piece : this.chessBoard.getChessPieces()) {
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

        player.getInventory().setItemStack(0, getSelectItem());
    }

    private ItemStack getSelectItem() {
        return ItemStack.builder(Material.STICK)
                .set(ItemComponent.ITEM_NAME, Component.text("Select"))
                .build();
    }

    private void handleSelect() {
        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerUseItemEvent.class, playerUseItemEvent -> {
            if (playerUseItemEvent.getItemStack().material() == Material.STICK) {
                PiecePosition pos = RayTraceGridSelector.select(playerUseItemEvent.getPlayer());
                Optional<ChessPiece> chessPiece = getPiece(pos);
                if (chessPiece.isPresent()) {
                    ChessPiece piece = chessPiece.orElseThrow();
                    if (playerUseItemEvent.getPlayer().equals(player1) && piece.getIsWhite() && isWhiteTurn) {
                        piece.select();
                        return;
                    } else if (playerUseItemEvent.getPlayer().equals(player2) && !piece.getIsWhite() && !isWhiteTurn) {
                        piece.select();
                        return;
                    }
                }
                if (this.chessBoard.getSelectedPiece().getPossibleMoves().stream().anyMatch(piecePosition -> {
                    return piecePosition.equals(pos);
                })) {
                    ChessPiece  piece = this.chessBoard.getSelectedPiece();
                    if (playerUseItemEvent.getPlayer().equals(player1) && piece.getIsWhite() && isWhiteTurn) {
                        piece.move(pos);
                        return;
                    } else if (playerUseItemEvent.getPlayer().equals(player2) && !piece.getIsWhite() && !isWhiteTurn) {
                        piece.move(pos);
                        return;
                    }
                }
            }
        });

    }

    private Optional<ChessPiece> getPiece(PiecePosition pos) {
        return this.chessBoard.getChessPieces().stream().filter(chessPiece -> chessPiece.getPosition().equals(pos)).findAny();
    }
}
