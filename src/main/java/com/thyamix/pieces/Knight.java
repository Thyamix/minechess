package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import net.hollowcube.schem.SchematicReader;

import java.nio.file.Path;

public class Knight extends  ChessPiece{
    public Knight(SchematicReader schematicReader, ChessBoard chessBoard, boolean isWhite, int[] position) {
        super(schematicReader.read(Path.of(isWhite ? "src/main/resources/schematics/White_Knight.schem" : "src/main/resources/schematics/Black_Knight.schem")), chessBoard, isWhite, position);
    }

    @Override
    public void getMoves() {

    }
}
