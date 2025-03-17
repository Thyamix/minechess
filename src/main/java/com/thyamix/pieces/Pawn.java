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
        this.clearPossibleMoves();
        PiecePosition possibleMove = this.getPosition().clone();
        possibleMove.setY(this.getIsWhite() ? (possibleMove.getY() + 1) : (possibleMove.getY() - 1));
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            this.addPossibleMoves(possibleMove.clone());
        } else {
            return;
        }
        if (!this.hasMoved()) {
            possibleMove.setY(this.getIsWhite() ? possibleMove.getY() + 1 : possibleMove.getY() - 1);
            if (this.chessBoard.isEmptyValidSquare(possibleMove) && !this.hasMoved()) {
                this.addPossibleMoves(possibleMove.clone());
            }
        }
    }
}

