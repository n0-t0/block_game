package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

public class PieceA extends AbstractPiece {
    @FXML private Rectangle a0;
    @FXML private Rectangle a1;
    @FXML private Rectangle a2;
    @FXML private Rectangle a3;
    @FXML private Rectangle a4;
    // コンストラクタ
    public PieceA() {
        super("pieceA.fxml");
        slot = new Slot(0, 0);
        key = a2;
        drag = new Drag();
    }
}