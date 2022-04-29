package com.example.block_game;


import javafx.scene.paint.Color;

public class GameLogic {

    static Slot getSlot(double x, double y) {
        if(x%50.0 == 0.0 && y%50.0 == 0.0) {
            return new Slot((int)(x/50.0), (int)(y/50.0));
        } else {
            return new Slot((int)(GameLogic.calcNearByX(x)/50), (int)(GameLogic.calcNearByY(y)/50));
        }
    }
    static double calcNearByX(double x) {
        if(x - ((int)x/50)*50 > 25) {
            return ((int)(x/50))*50.0+50;
        } else {
            return ((int)(x/50))*50.0;
        }
    }
    static double calcNearByY(double y) {
        if(y - ((int)y/50)*50 > 25) {
            return ((int)(y/50))*50.0+50;
        } else {
            return ((int)(y/50))*50.0;
        }
    }

    static Slot move(PieceA piece, Slot slot) {
        // if(registerBoardMap) {node.setLayout} else {node.changeColor}
        if(GameLogic.registerBoardMap(piece, slot)) {
            System.out.println("Move to Slot"+"("+slot.x()+","+slot.y()+")");
            piece.setLayoutX(slot.x()*50);
            piece.setLayoutY(slot.y()*50);
            return slot;
        } else {
            piece.setFill(Color.DARKRED);
            return piece.getSlot();
        }
    }

    static boolean registerBoardMap(PieceA pieceA, Slot slot) {
        // 動けるかの判定
        // 盤面情報を登録
        return true; // debug
    }
}
