package com.thyamix.game;

import com.thyamix.pieces.*;
import com.thyamix.utility.PiecePosition;
import net.hollowcube.schem.SchematicReader;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private List<ChessPiece> chessPieces = new ArrayList<>();
    private final ChessGame chessGame;
    private ChessPiece selectedPiece;

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
            this.chessPieces.add(new Pawn(new SchematicReader(), this, true, new PiecePosition(x, 1)));
        }
        // White Rooks
        this.chessPieces.add(new Rook(new SchematicReader(), this, true, new PiecePosition(0, 0)));
        this.chessPieces.add(new Rook(new SchematicReader(), this, true, new PiecePosition(7, 0)));
        // White Knights
        this.chessPieces.add(new Knight(new SchematicReader(), this, true, new PiecePosition(1, 0)));
        this.chessPieces.add(new Knight(new SchematicReader(), this, true, new PiecePosition(6, 0)));
        // White Bishops
        this.chessPieces.add(new Bishop(new SchematicReader(), this, true, new PiecePosition(2, 0)));
        this.chessPieces.add(new Bishop(new SchematicReader(), this, true, new PiecePosition(5, 0)));
        // White Queen
        this.chessPieces.add(new Queen(new SchematicReader(), this, true, new PiecePosition(4, 0)));
        // White King
        this.chessPieces.add(new King(new SchematicReader(), this, true, new PiecePosition(3, 0)));

        // Black Pawns
        for (int x = 0; x < 8; x++) {
            this.chessPieces.add(new Pawn(new SchematicReader(), this, false, new PiecePosition(x, 6)));
        }
        // Black Rooks
        this.chessPieces.add(new Rook(new SchematicReader(), this, false, new PiecePosition(0, 7)));
        this.chessPieces.add(new Rook(new SchematicReader(), this, false, new PiecePosition(7, 7)));
        // Black Knights
        this.chessPieces.add(new Knight(new SchematicReader(), this, false, new PiecePosition(1, 7)));
        this.chessPieces.add(new Knight(new SchematicReader(), this, false, new PiecePosition(6, 7)));
        // Black Bishops
        this.chessPieces.add(new Bishop(new SchematicReader(), this, false, new PiecePosition(2, 7)));
        this.chessPieces.add(new Bishop(new SchematicReader(), this, false, new PiecePosition(5, 7)));
        // Black Queen
        this.chessPieces.add(new Queen(new SchematicReader(), this, false, new PiecePosition(4, 7)));
        // Black King
        this.chessPieces.add(new King(new SchematicReader(), this, false, new PiecePosition(3, 7)));
    }

    public boolean isEmptyValidSquare(PiecePosition position) {
        if (isValidSquare(position)) {
            return this.chessPieces.stream().noneMatch(chessPiece -> chessPiece.getPosition().equals(position));
        }
        return false;
    }

    public boolean isValidSquare(PiecePosition position) {
        return position.getX() >= 0 && position.getX() <= 7 && position.getY() >= 0 && position.getY() <= 7;
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

    public List<ChessPiece> getChessPieces() {
        return this.chessPieces;
    }

    public ChessGame getChessGame() {
        return this.chessGame;
    }

    public ChessPiece getSelectedPiece() {
        return this.selectedPiece;
    }

    public void selectPiece(ChessPiece selectedPiece) {
        this.resetBoard();
        if (!(this.selectedPiece == selectedPiece)) {
            this.selectedPiece = selectedPiece;
            this.selectedPiece.displayPossibleMoves();
        } else {
            this.selectedPiece = null;
        }
    }

    public void unselectPiece() {
        this.selectedPiece = null;
        this.resetBoard();
    }
}