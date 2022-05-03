package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public final class PieceA extends AbstractPiece {
    @FXML private Rectangle a0;
    @FXML private Rectangle a1;
    @FXML private Rectangle a2;
    @FXML private Rectangle a3;
    @FXML private Rectangle a4;
    @FXML private Rectangle ar;
    @FXML private Rectangle al;
    public PieceA(int playerID, Paint color) {
        super("pieceA.fxml", playerID, color);
        disableAnkerRect();
    }
    private void disableAnkerRect() {
        this.al.setDisable(true);
        this.ar.setDisable(true);
        this.al.setOpacity(0);
        this.ar.setOpacity(0);
    }
}
