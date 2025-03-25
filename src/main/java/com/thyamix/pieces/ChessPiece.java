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
    private final PiecePosition position;
    private final PiecePosition testPosition;
    private final boolean isWhite;
    private List<PiecePosition> possibleMoves = new ArrayList<>();
    private List<PiecePosition> illegalMoves = new ArrayList<>();
    private List<PiecePosition[]> possibleTakes = new ArrayList<>();

    ChessPiece(Schematic schematic, ChessBoard chessBoard, boolean isWhite, PiecePosition position) {
        this.schematic = schematic;
        this.instanceContainer = chessBoard.getChessGame().instanceContainer;
        this.chessBoard = chessBoard;
        this.isWhite = isWhite;
        this.position = position;
        this.testPosition = position.clone();
        this.placeBlocks();
    }

    public abstract void getMoves();

    public void move(PiecePosition move) {
        clearBlocks();

        this.chessBoard.getChessGame().addMove(this, this.position.clone(), move.clone());

        this.position.set(move);
        this.testPosition.set(move);

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

    public void clearBlocks() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 8; y++) {
                for (int z = 0; z < 4; z++) {
                    this.instanceContainer.setBlock(x + this.getPosition().getX() * 4, y, z + this.getPosition().getY() * 4, Block.AIR);
                }
            }
        }
    }

    public void displayPossibleMoves() {
        this.possibleMoves.forEach(possibleMove -> this.chessBoard.setSquare(possibleMove.getX(), possibleMove.getY(), Block.GREEN_CONCRETE));
        this.possibleTakes.forEach(possibleTake -> this.chessBoard.setSquare(possibleTake[1].getX(), possibleTake[1].getY(), Block.RED_CONCRETE));
        this.illegalMoves.forEach(illegalMove -> this.chessBoard.setSquare(illegalMove.getX(), illegalMove.getY(), Block.ORANGE_CONCRETE));
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

    public boolean hasNotMoved() {
        return !this.hasMoved;
    }

    public void select() {
        this.chessBoard.selectPiece(this);
    }

    public void unselect() {
        this.chessBoard.unselectPiece();
    }

    protected void checkAndAddPossibleMoves(PiecePosition possibleMove) {
        if (isLegal(possibleMove)) {
            this.possibleMoves.add(possibleMove);
        } else {
            this.illegalMoves.add(possibleMove);
        }
    }

    protected void clearMoves() {
        this.possibleMoves = new ArrayList<>();
        this.illegalMoves = new ArrayList<>();
        this.possibleTakes = new ArrayList<>();
    }

    protected void checkAndAddPossibleTakes(PiecePosition possibleTake ,PiecePosition possibleMove) {
        if (this.chessBoard.getChessGame().getPiece(possibleTake).isPresent()) {
            if (this.isWhite != this.chessBoard.getChessGame().getPiece(possibleTake).get().isWhite) {
                if (isLegal(possibleMove)) {
                    this.possibleTakes.add(new PiecePosition[]{possibleTake, possibleMove});
                } else {
                    this.illegalMoves.add(possibleMove);
                }
            }
        }
    }

    public List<PiecePosition[]> getPossibleTakes() {
        return this.possibleTakes;
    }

    public void take(PiecePosition[] take) {
        ChessPiece chessPiece;
        if (this.chessBoard.getChessGame().getPiece(take[0]).isPresent()) {
            chessPiece = this.chessBoard.getChessGame().getPiece(take[0]).get();
        } else {
            return;
        }

        this.chessBoard.killPiece(chessPiece);

        this.move(take[1]);
    }

    public PiecePosition getTestPosition() {
        return this.testPosition;
    }

    private boolean isLegal(PiecePosition position) {
        King king = this.isWhite ? this.chessBoard.getWhiteKing() : this.chessBoard.getBlackKing();

        this.testPosition.set(position);

        // Check Pawns
        PiecePosition testPos = new PiecePosition(0, 0);
        if (this.isWhite) {
            testPos.set(king.getTestPosition());
            testPos.incX();
            testPos.incY();
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Pawn && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                }
            }
            testPos.set(king.getTestPosition());
            testPos.decX();
            testPos.incY();
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Pawn && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                }
            }
        } else {
            testPos.set(king.getTestPosition());
            testPos.incX();
            testPos.decY();
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Pawn && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                }
            }
            testPos.set(king.getTestPosition());
            testPos.decX();
            testPos.decY();
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Pawn && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                }
            }
        }

        // Check King
        testPos.set(king.getTestPosition());
        testPos.incX();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.incY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decX();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decX();
        testPos.decY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.incX();
        testPos.decY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decX();
        testPos.incY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.incX();
        testPos.incY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof King && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }

        // Check Knights
        testPos.set(king.getTestPosition());
        testPos.incX();
        testPos.incX();
        testPos.incY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.incX();
        testPos.incX();
        testPos.decY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decX();
        testPos.decX();
        testPos.incY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decX();
        testPos.decX();
        testPos.decY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }

        testPos.set(king.getTestPosition());
        testPos.incY();
        testPos.incY();
        testPos.incX();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.incY();
        testPos.incY();
        testPos.decX();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decY();
        testPos.decY();
        testPos.incX();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }
        testPos.set(king.getTestPosition());
        testPos.decY();
        testPos.decY();
        testPos.decY();
        if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
            if (this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Knight && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                this.testPosition.set(this.position);
                return false;
            }
        }

        // Check Bishop / Queen
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.incX();
            testPos.incY();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Bishop) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.decX();
            testPos.incY();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Bishop) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.incX();
            testPos.decY();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Bishop) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.decX();
            testPos.decY();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Bishop) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }

        // Check Rook / Queen

        testPos.set(king.getTestPosition());
        while (true) {
            testPos.incX();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Rook) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.decX();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Rook) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.incY();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Rook) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }
        testPos.set(king.getTestPosition());
        while (true) {
            testPos.decY();
            if (!this.chessBoard.isValidSquare(testPos)) {
                break;
            }
            if (this.chessBoard.getChessGame().getPiece(testPos).isPresent()) {
                if ((this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Queen || this.chessBoard.getChessGame().getPiece(testPos).get() instanceof Rook) && (this.chessBoard.getChessGame().getPiece(testPos).get().getIsWhite()) != this.getIsWhite()) {
                    this.testPosition.set(this.position);
                    return false;
                } else {
                    break;
                }
            }
        }

        this.testPosition.set(this.position);
        return true;
    }
}

