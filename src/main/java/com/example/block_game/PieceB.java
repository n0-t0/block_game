package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public final class PieceB extends AbstractPiece {
    @FXML private Rectangle b0;
    @FXML private Rectangle b1;
    @FXML private Rectangle b2;
    @FXML private Rectangle b3;
    @FXML private Rectangle b4;
    @FXML private Rectangle br;
    @FXML private Rectangle bl;
    // コンストラクタ
    public PieceB(Player player) {
        super("pieceB.fxml", player);
        disableAnkerRect();
    }
    private void disableAnkerRect() {
        this.bl.setDisable(true);
        this.br.setDisable(true);
        this.bl.setOpacity(0);
        this.br.setOpacity(0);
    }
}