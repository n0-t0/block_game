package com.example.block_game;

public class GameLogic {

    private static Slot getSlot(double x, double y) {
        if(x%50.0 == 0.0 && y%50.0 == 0.0) {
            return new Slot((int)(x/50.0), (int)(y/50.0));
        } else {
            return new Slot((int)(GameLogic.calcNearByX(x)/50), (int)(GameLogic.calcNearByY(y)/50));
        }
    }
    private static double calcNearByX(double x) {
        if(x - ((int)x/50)*50 > 25) {
            return ((int)(x/50))*50.0+50;
        } else {
            return ((int)(x/50))*50.0;
        }
    }
    private static double calcNearByY(double y) {
        if(y - ((int)y/50)*50 > 25) {
            return ((int)(y/50))*50.0+50;
        } else {
            return ((int)(y/50))*50.0;
        }
    }

    static Slot dragMove(AbstractPiece piece) { // ドラッグ用
        //  落ちた位置の最近傍論理スロットの取得
        Point movePoint = piece.getSelfPoint();
        Slot moveSlot = GameLogic.getSlot(movePoint.x(), movePoint.y());
        return GameLogic.move(piece, moveSlot);
    }


    static  Slot move(AbstractPiece piece, Slot moveSlot) {
        // スロットにおいて良いかチェックする
        if(GameLogic.registerBoardMap(piece)) {
            System.out.println("Move to Slot"+"("+moveSlot.x()+","+moveSlot.y()+")");
            piece.setLayoutX(moveSlot.x()*50);
            piece.setLayoutY(moveSlot.y()*50);
            return moveSlot;
        } else {
//            piece.setFill(Color.DARKRED);
            return piece.getSlot();
        }

    }

    static boolean registerBoardMap(AbstractPiece pieceA) {
        // 動けるかの判定
        // 盤面情報を登録
        return true; // debug
    }
}
