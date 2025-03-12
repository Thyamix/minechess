package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class King extends ChessPiece{
    public King(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, int[] position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_King.schem" : "src/main/resources/schematics/Black_King.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {

    }
}
