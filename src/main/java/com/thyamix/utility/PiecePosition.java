package com.thyamix.utility;

public class PiecePosition {
    private int x;
    private int y;

    public PiecePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals (PiecePosition pos) {
        return this.x == pos.x && this.y == pos.y;
    }

    public PiecePosition clone() {
        return new PiecePosition(getX(), getY());
    }

    public void set(PiecePosition pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incX() {
        this.x++;
    }

    public void decX() {
        this.x--;
    }

    public void incY() {
        this.y++;
    }

    public void decY() {
        this.y--;
    }
}
