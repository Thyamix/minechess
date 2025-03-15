package com.thyamix.pieces;

import com.thyamix.game.ChessBoard;
import net.hollowcube.schem.Rotation;
import net.hollowcube.schem.Schematic;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ChessPiece {
    private final Schematic schematic;
    protected final InstanceContainer instanceContainer;
    protected final ChessBoard chessBoard;

    public boolean hasMoved = false;
    public boolean isSelected = false;
    public int[] position;
    public boolean isWhite;
    public List<int[]> possibleMoves = new ArrayList<int[]>();

    ChessPiece(Schematic schematic, ChessBoard chessBoard, boolean isWhite, int[] position) {
        this.schematic = schematic;
        this.instanceContainer = chessBoard.chessGame.instanceContainer;
        this.chessBoard = chessBoard;
        this.isWhite = isWhite;
        this.position = position.clone();
        this.placeBlocks();
    }

    public abstract void getMoves();

    public boolean canMoveTo(int[] move) {
        return possibleMoves.stream().anyMatch(possibleMove -> Arrays.equals(possibleMove, move));
    }

    public boolean move(int[] move) {
        if (!canMoveTo(move)) {
            return false;
        }
        clearBlocks();

        this.position = move.clone();

        placeBlocks();
        this.hasMoved = true;
        return true;
    }

    protected void placeBlocks() {
        Pos position = new Pos(this.position[0] * 4, 0, this.position[1] * 4);
        this.schematic.apply(Rotation.NONE, ((point, block) -> {
            Pos pos = position.add(point);
            instanceContainer.setBlock(pos, block);
        }));
    }

    protected void clearBlocks() {
        for (int x = 0; x < 4; x++) {
            for (int y = 1; y < 8; y++) {
                for (int z = 0; z < 4; z++) {
                    this.instanceContainer.setBlock(x, y, z, Block.AIR);
                }
            }
        }
    }

    public void displayPossibleMoves() {
        this.possibleMoves.forEach(possibleMove -> {
            this.chessBoard.setSquare(possibleMove[0], possibleMove[1], Block.GREEN_CONCRETE);
        });
    }
}

