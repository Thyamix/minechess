package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import com.thyamix.utility.PiecePosition;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class Pawn extends ChessPiece {

    public Pawn(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, PiecePosition position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_Pawn.schem" : "src/main/resources/schematics/Black_Pawn.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {
        this.clearMoves();
        PiecePosition possibleMove = this.getPosition().clone();
        possibleMove.setY(this.getIsWhite() ? (possibleMove.getY() + 1) : (possibleMove.getY() - 1));
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            this.checkAndAddPossibleMoves(possibleMove.clone());
        }
        if (this.hasNotMoved()) {
            possibleMove.setY(this.getIsWhite() ? possibleMove.getY() + 1 : possibleMove.getY() - 1);
            if (this.chessBoard.isEmptyValidSquare(possibleMove) && this.hasNotMoved()) {
                this.checkAndAddPossibleMoves(possibleMove.clone());
            }
        }

        possibleMove = this.getPosition().clone();
        possibleMove.setY(this.getIsWhite() ? (possibleMove.getY() + 1) : (possibleMove.getY() - 1));
        possibleMove.decX();
        if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }
        possibleMove.incX();
        possibleMove.incX();
        if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }

        if (this.getPosition().getY() == 4 || this.getPosition().getY() == 3) {
            this.chessBoard.getChessGame().player1 .sendMessage("test 0");
            possibleMove = this.getPosition().clone();
            possibleMove.incX();
            if (this.chessBoard.getChessGame().getPiece(possibleMove).isPresent()) {
                this.chessBoard.getChessGame().player1 .sendMessage("test 1");
                ChessPiece piece = this.chessBoard.getChessGame().getPiece(possibleMove).get();
                if (piece instanceof Pawn && piece.getIsWhite() != this.getIsWhite()) {
                    this.chessBoard.getChessGame().player1 .sendMessage("test 2");
                    if (this.chessBoard.getChessGame().getLastMove().getChessPiece().equals(piece)) {
                        this.chessBoard.getChessGame().player1 .sendMessage("test 3" + this.chessBoard.getChessGame().getLastMove().getPosition().getY());
                        if (this.chessBoard.getChessGame().getLastMove().getPosition().getY() == 1 || this.chessBoard.getChessGame().getLastMove().getPosition().getY() == 6) {
                            this.chessBoard.getChessGame().player1 .sendMessage("test 4");
                            PiecePosition  possibleTake = possibleMove.clone();
                            if (this.getIsWhite()) {
                                possibleMove.incY();
                            } else {
                                possibleMove.decY();
                            }
                            this.checkAndAddPossibleTakes(possibleTake.clone(), possibleMove.clone());
                            this.chessBoard.getChessGame().player1 .sendMessage("test 5");
                        }
                    }
                }
            }
            possibleMove = this.getPosition().clone();
            possibleMove.decX();
            if (this.chessBoard.getChessGame().getPiece(possibleMove).isPresent()) {
                this.chessBoard.getChessGame().player1 .sendMessage("test 1");
                ChessPiece piece = this.chessBoard.getChessGame().getPiece(possibleMove).get();
                if (piece instanceof Pawn && piece.getIsWhite() != this.getIsWhite()) {
                    this.chessBoard.getChessGame().player1 .sendMessage("test 2");
                    if (this.chessBoard.getChessGame().getLastMove().getChessPiece().equals(piece)) {
                        this.chessBoard.getChessGame().player1 .sendMessage("test 3" + this.chessBoard.getChessGame().getLastMove().getPosition().getY());
                        if (this.chessBoard.getChessGame().getLastMove().getPosition().getY() == 1 || this.chessBoard.getChessGame().getLastMove().getPosition().getY() == 6) {
                            this.chessBoard.getChessGame().player1 .sendMessage("test 4");
                            PiecePosition  possibleTake = possibleMove.clone();
                            if (this.getIsWhite()) {
                                possibleMove.incY();
                            } else {
                                possibleMove.decY();
                            }
                            this.checkAndAddPossibleTakes(possibleTake.clone(), possibleMove.clone());
                            this.chessBoard.getChessGame().player1 .sendMessage("test 5");
                        }
                    }
                }
            }
        }

    }
}

