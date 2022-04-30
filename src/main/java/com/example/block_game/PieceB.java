package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class PieceB extends AbstractPiece {
    @FXML private Rectangle a0;
    @FXML private Rectangle a1;
    @FXML private Rectangle a2;
    @FXML private Rectangle a3;
    @FXML private Rectangle a4;
    // コンストラクタ
    public PieceB() {
        super("pieceB.fxml");
        slot = new Slot(0, 0);
        key = a1;
        drag = new Drag();
    }
}