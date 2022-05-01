package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PieceD extends AbstractPiece {
    @FXML private Rectangle a0;
    @FXML private Rectangle a1;
    @FXML private Rectangle a2;
    @FXML private Rectangle a3;
    @FXML private Rectangle a4;
    // コンストラクタ
    public PieceD(int playerID, Paint color) {
        super("pieceD.fxml", playerID, color);
        slot = new Slot(0, 0);
        key = a1;
        drag = new Drag();
    }
}