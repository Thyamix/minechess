package com.thyamix.utility;

import com.thyamix.pieces.ChessPiece;

public class PieceMove {
    private final ChessPiece chessPiece;
    private final PiecePosition position;
    private final PiecePosition move;

    public PieceMove(ChessPiece chessPiece, PiecePosition position, PiecePosition move) {
        this.chessPiece = chessPiece;
        this.position = position;
        this.move = move;
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public PiecePosition getMove() {
        return move;
    }

    public PiecePosition getPosition() {
        return position;
    }
}
