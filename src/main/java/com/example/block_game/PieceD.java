package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public final class PieceD extends AbstractPiece {
    @FXML private Rectangle d0;
    @FXML private Rectangle d1;
    @FXML private Rectangle d2;
    @FXML private Rectangle d3;
    @FXML private Rectangle d4;
    @FXML private Rectangle dr;
    @FXML private Rectangle dl;

    // コンストラクタ
    public PieceD(int playerID, Paint color) {
        super("pieceD.fxml", playerID, color);
        disableAnkerRect();
    }
    private void disableAnkerRect() {
        this.dl.setDisable(true);
        this.dr.setDisable(true);
        this.dl.setOpacity(0);
        this.dr.setOpacity(0);
    }
}