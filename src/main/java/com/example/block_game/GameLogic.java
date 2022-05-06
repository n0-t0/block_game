package com.example.block_game;

public class GameLogic {
    static  Slot move(AbstractPiece piece, Slot moveSlot) {
        if(GameLogic.registerBoardMap(piece)) {
            return moveSlot;
        } else {
            return piece.getSlot();
        }
    }

    static boolean registerBoardMap(AbstractPiece pieceA) {
        // 動けるかの判定
        // 盤面情報を登録
        return true; // debug
    }
}
