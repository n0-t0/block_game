package com.example.block_game;

public record Slot(int x, int y){
    public static final int xlim = 11;
    public static final int ylim = 11;
    static Slot left(PieceA piece, Slot tmpSlot) {
        if(0 <= tmpSlot.x()-1) {
            return new Slot(tmpSlot.x()-1, tmpSlot.y());
        } else {
            return tmpSlot;
        }
    }
    static Slot right(PieceA piece, Slot tmpSlot) {
        if(xlim >= tmpSlot.x()+1) {
            return new Slot(tmpSlot.x()+1, tmpSlot.y());
        } else {
            return tmpSlot;
        }
    }
    static Slot up(PieceA piece, Slot tmpSlot) {
        if(0 <= tmpSlot.y()-1) {
            return new Slot(tmpSlot.x(), tmpSlot.y()-1);
        } else {
            return tmpSlot;
        }
    }
    static Slot down(PieceA piece, Slot tmpSlot) {
        if(ylim >= tmpSlot.y()+1) {
            return new Slot(tmpSlot.x(), tmpSlot.y()+1);
        } else {
            return tmpSlot;
        }
    }
}

