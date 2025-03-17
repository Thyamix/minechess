package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import com.thyamix.utility.PiecePosition;
import net.hollowcube.schem.Rotation;
import net.hollowcube.schem.Schematic;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece {
    private final Schematic schematic;
    protected final InstanceContainer instanceContainer;
    protected final ChessBoard chessBoard;

    private boolean hasMoved = false;
    private PiecePosition position;
    private boolean isWhite;
    private List<PiecePosition> possibleMoves = new ArrayList<>();

    ChessPiece(Schematic schematic, ChessBoard chessBoard, boolean isWhite, PiecePosition position) {
        this.schematic = schematic;
        this.instanceContainer = chessBoard.getChessGame().instanceContainer;
        this.chessBoard = chessBoard;
        this.isWhite = isWhite;
        this.position = position;
        this.placeBlocks();
    }

    public abstract void getMoves();

    public boolean canMoveTo(PiecePosition move) {
        return this.possibleMoves.stream().anyMatch(possibleMove -> possibleMove.equals(move));
    }

    public void move(PiecePosition move) {
        if (!canMoveTo(move)) {
            return;
        }
        clearBlocks();

        this.position.set(move);

        placeBlocks();
        this.unselect();
        this.hasMoved = true;

        this.chessBoard.getChessGame().refreshMoves();
    }

    protected void placeBlocks() {
        Pos position = new Pos(this.position.getX() * 4, 0, this.position.getY() * 4);
        this.schematic.apply(Rotation.NONE, ((point, block) -> {
            Pos pos = position.add(point);
            instanceContainer.setBlock(pos, block);
        }));
    }

    protected void clearBlocks() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 8; y++) {
                for (int z = 0; z < 4; z++) {
                    this.instanceContainer.setBlock(x + this.getPosition().getX() * 4, y, z + this.getPosition().getY() * 4, Block.AIR);
                }
            }
        }
    }

    public void displayPossibleMoves() {
        this.possibleMoves.forEach(possibleMove -> {
            this.chessBoard.setSquare(possibleMove.getX(), possibleMove.getY(), Block.GREEN_CONCRETE);
        });
    }

    public PiecePosition getPosition() {
        return this.position;
    }

    public List<PiecePosition> getPossibleMoves() {
        return this.possibleMoves;
    }

    public boolean getIsWhite() {
        return this.isWhite;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public void select() {
        this.chessBoard.selectPiece(this);
    }

    public void unselect() {
        this.chessBoard.unselectPiece();
    }

    protected void addPossibleMoves(PiecePosition possibleMove) {
        this.possibleMoves.add(possibleMove);
    }

    protected void clearPossibleMoves() {
        this.possibleMoves = new ArrayList<PiecePosition>();
    }
}

