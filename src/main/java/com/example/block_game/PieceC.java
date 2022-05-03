package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public final class PieceC extends AbstractPiece {
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
        disableAnkerRect();
    }
    private void disableAnkerRect() {
        this.cl.setDisable(true);
        this.cr.setDisable(true);
        this.cl.setOpacity(0);
        this.cr.setOpacity(0);
    }
}