package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PieceC extends AbstractPiece {
    @FXML private Rectangle a0;
    @FXML private Rectangle c1;
    @FXML private Rectangle c2;
    @FXML private Rectangle c3;
    @FXML private Rectangle c4;

    @FXML private Rectangle cl;
    @FXML private Rectangle cr;
    // コンストラクタ
    public PieceC(int playerID, Paint color) {
        super("pieceC.fxml", playerID, color);
        slot = new Slot(0, 0);
        key = c4;
        drag = new Drag();
    }
    @FXML @Override void initialize() {
        super.initialize();
        this.cl.setDisable(true);
        this.cr.setDisable(true);
    }
    @Override public void setFill(Paint paint) {
        super.setFill(paint);
        this.cl.setFill(Color.BLACK);
        this.cr.setFill(Color.BLACK);
        this.cl.setOpacity(0);
        this.cr.setOpacity(0);
    }
}