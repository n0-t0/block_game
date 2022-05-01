package com.example.block_game;

public record Slot(int x, int y){
    public static final int xlim = 11;
    public static final int ylim = 11;
    static Slot left(Slot tmpSlot) {
        if(-2 <= tmpSlot.x()-1) {
            return new Slot(tmpSlot.x()-1, tmpSlot.y());
        } else {
            return tmpSlot;
        }
    }
    static Slot right(Slot tmpSlot) {
        if(xlim-2 >= tmpSlot.x()+1) {
            return new Slot(tmpSlot.x()+1, tmpSlot.y());
        } else {
            return tmpSlot;
        }
    }
    static Slot up(Slot tmpSlot) {
        if(-2 <= tmpSlot.y()-1) {
            return new Slot(tmpSlot.x(), tmpSlot.y()-1);
        } else {
            return tmpSlot;
        }
    }
    static Slot down(Slot tmpSlot) {
        if(ylim-2 >= tmpSlot.y()+1) {
            return new Slot(tmpSlot.x(), tmpSlot.y()+1);
        } else {
            return tmpSlot;
        }
    }
}

