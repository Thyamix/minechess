package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import com.thyamix.utility.PiecePosition;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class Knight extends  ChessPiece{
    public Knight(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, PiecePosition position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_Knight.schem" : "src/main/resources/schematics/Black_Knight.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {
        this.clearMoves();
        PiecePosition possibleMove = this.getPosition().clone();
        possibleMove.incX();
        possibleMove.incX();
        possibleMove.incY();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }
        possibleMove.decY();
        possibleMove.decY();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }

        possibleMove = this.getPosition().clone();
        possibleMove.decX();
        possibleMove.decX();
        possibleMove.incY();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }
        possibleMove.decY();
        possibleMove.decY();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }

        possibleMove = this.getPosition().clone();
        possibleMove.incY();
        possibleMove.incY();
        possibleMove.incX();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }
        possibleMove.decX();
        possibleMove.decX();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }

        possibleMove = this.getPosition().clone();
        possibleMove.decY();
        possibleMove.decY();
        possibleMove.incX();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }
        possibleMove.decX();
        possibleMove.decX();
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            checkAndAddPossibleMoves(possibleMove.clone());
        } else if (this.chessBoard.isValidSquare(possibleMove)) {
            this.checkAndAddPossibleTakes(possibleMove.clone(), possibleMove.clone());
        }
    }
}
