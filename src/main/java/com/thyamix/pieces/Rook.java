package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import com.thyamix.utility.PiecePosition;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class Rook extends ChessPiece {

    public Rook(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, PiecePosition position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_Rook.schem" : "src/main/resources/schematics/Black_Rook.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {
        this.clearMoves();
        PiecePosition possibleMove = this.getPosition().clone();
        while (true) {
            possibleMove.incX();
            if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
                this.checkAndAddPossibleMoves(possibleMove.clone());
            } else {
                if (this.chessBoard.isValidSquare(possibleMove)) {
                    this.checkAndAddPossibleTakes(possibleMove.clone());
                }
                break;
            }
        }
        possibleMove = this.getPosition().clone();
        while (true) {
            possibleMove.decX();
            if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
                this.checkAndAddPossibleMoves(possibleMove.clone());
            } else {
                if (this.chessBoard.isValidSquare(possibleMove)) {
                    this.checkAndAddPossibleTakes(possibleMove.clone());
                }
                break;
            }
        }
        possibleMove = this.getPosition().clone();
        while (true){
            possibleMove.incY();
            if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
                this.checkAndAddPossibleMoves(possibleMove.clone());
            } else {
                if (this.chessBoard.isValidSquare(possibleMove)) {
                    this.checkAndAddPossibleTakes(possibleMove.clone());
                }
                break;
            }
        }
        possibleMove = this.getPosition().clone();
        while (true){
            possibleMove.decY();
            if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
                this.checkAndAddPossibleMoves(possibleMove.clone());
            } else {
                if (this.chessBoard.isValidSquare(possibleMove)) {
                    this.checkAndAddPossibleTakes(possibleMove.clone());
                }
                break;
            }
        }
    }
}
