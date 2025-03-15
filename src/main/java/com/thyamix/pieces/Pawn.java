package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class Pawn extends ChessPiece {

    public Pawn(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, int[] position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_Pawn.schem" : "src/main/resources/schematics/Black_Pawn.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {
        int[] possibleMove = this.position.clone();
        possibleMove[1] = this.isWhite ? (possibleMove[1] + 1) : (possibleMove[1] - 1);
        if (this.chessBoard.isEmptyValidSquare(possibleMove)) {
            this.possibleMoves.add(possibleMove.clone());
        } else {
            return;
        }
        if (!this.hasMoved) {
            possibleMove[1] = this.isWhite ? possibleMove[1] + 1 : possibleMove[1] - 1;
            if (this.chessBoard.isEmptyValidSquare(possibleMove) && !this.hasMoved) {
                this.possibleMoves.add(possibleMove.clone());
            }
        }
    }
}

