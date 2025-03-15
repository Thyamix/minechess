package com.thyamix.game;

import com.thyamix.pieces.*;
import net.hollowcube.schem.SchematicReader;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessBoard {
    public List<ChessPiece> chessPieces = new ArrayList<>();
    public final ChessGame chessGame;

    public ChessBoard(ChessGame chessGame) {
        this.chessGame = chessGame;
        generatePieces();
        resetBoard();
    }

    public void resetBoard() {
        for (int x = 0; x < 8; x++) {
            for (int z = 0; z < 8; z++) {
                resetSquare(x, z);
            }
        }
    }


    private void generatePieces() {
        // White Pawns
        for (int x = 0; x < 8; x++) {
            this.chessPieces.add(new Pawn(new SchematicReader(), this, true, new int[]{x, 1}));
        }
        // White Rooks
        this.chessPieces.add(new Rook(new SchematicReader(), this, true, new int[]{0, 0}));
        this.chessPieces.add(new Rook(new SchematicReader(), this, true, new int[]{7, 0}));
        // White Knights
        this.chessPieces.add(new Knight(new SchematicReader(), this, true, new int[]{1, 0}));
        this.chessPieces.add(new Knight(new SchematicReader(), this, true, new int[]{6, 0}));
        // White Bishops
        this.chessPieces.add(new Bishop(new SchematicReader(), this, true, new int[]{2, 0}));
        this.chessPieces.add(new Bishop(new SchematicReader(), this, true, new int[]{5, 0}));
        // White Queen
        this.chessPieces.add(new Queen(new SchematicReader(), this, true, new int[]{4, 0}));
        // White King
        this.chessPieces.add(new King(new SchematicReader(), this, true, new int[]{3, 0}));

        // Black Pawns
        for (int x = 0; x < 8; x++) {
            this.chessPieces.add(new Pawn(new SchematicReader(), this, false, new int[]{x, 6}));
        }
        // Black Rooks
        this.chessPieces.add(new Rook(new SchematicReader(), this, false, new int[]{0, 7}));
        this.chessPieces.add(new Rook(new SchematicReader(), this, false, new int[]{7, 7}));
        // Black Knights
        this.chessPieces.add(new Knight(new SchematicReader(), this, false, new int[]{1, 7}));
        this.chessPieces.add(new Knight(new SchematicReader(), this, false, new int[]{6, 7}));
        // Black Bishops
        this.chessPieces.add(new Bishop(new SchematicReader(), this, false, new int[]{2, 7}));
        this.chessPieces.add(new Bishop(new SchematicReader(), this, false, new int[]{5, 7}));
        // Black Queen
        this.chessPieces.add(new Queen(new SchematicReader(), this, false, new int[]{4, 7}));
        // Black King
        this.chessPieces.add(new King(new SchematicReader(), this, false, new int[]{3, 7}));
    }

    public boolean isEmptyValidSquare(int[] position) {
        if (position[0] < 0 || position[0] > 7 || position[1] < 0 || position[1] > 7) {
            return false;
        }
        return this.chessPieces.stream().noneMatch(chessPiece -> Arrays.equals(chessPiece.position, position));
    }

    public void resetSquare(int x, int z) {
        int gridSquareSize = 4;

        for (int i = 0; i < gridSquareSize; i++) {
            for (int j = 0; j < gridSquareSize; j++) {
                if (x % 2 != z % 2) {
                    this.chessGame.instanceContainer.setBlock(x * gridSquareSize + i, -1, z * gridSquareSize + j, Block.BLACKSTONE);
                } else {
                    this.chessGame.instanceContainer.setBlock(x * gridSquareSize + i, -1, z * gridSquareSize + j, Block.CALCITE);
                }
            }
        }
    }

    public void setSquare(int x, int z, Block block) {
        int gridSquareSize = 4;

        for (int i = 0; i < gridSquareSize; i++) {
            for (int j = 0; j < gridSquareSize; j++) {
                this.chessGame.instanceContainer.setBlock(x * gridSquareSize + i, -1, z * gridSquareSize + j, block);
            }
        }
    }
}