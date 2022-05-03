package com.example.block_game;

import javafx.scene.paint.Paint;

import java.util.List;

public class Player {
    private int id;
    private Paint color;
    private List<AbstractPiece> pieces;

    public Player(int id, Paint color, List<AbstractPiece> pieces) {
        this.id = id;
        this.color = color;
        this.pieces = pieces;
    }
    public int getID() {
        return id;
    }
    public Paint getColor() { return color; }
    public List<AbstractPiece> getPieces() { return pieces; }
}
