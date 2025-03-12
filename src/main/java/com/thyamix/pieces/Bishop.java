package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class Bishop extends ChessPiece {
    public Bishop(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, int[] position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_Bishop.schem" : "src/main/resources/schematics/Black_Bishop.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {

    }
}
