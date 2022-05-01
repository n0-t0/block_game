package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class PieceD extends AbstractPiece {
    @FXML private Rectangle d02;
    @FXML private Rectangle d12;
    @FXML private Rectangle d22;
    @FXML private Rectangle d32;
    @FXML private Rectangle d42;

    @FXML private Rectangle d00;
    @FXML private Rectangle d10;
    @FXML private Rectangle d20;
    @FXML private Rectangle d30;
    @FXML private Rectangle d40;

    @FXML private Rectangle d01;
    @FXML private Rectangle d11;
    @FXML private Rectangle d21;
    @FXML private Rectangle d31;
    @FXML private Rectangle d41;

    @FXML private Rectangle d03;
    @FXML private Rectangle d13;
    @FXML private Rectangle d23;
    @FXML private Rectangle d33;
    @FXML private Rectangle d43;

    @FXML private Rectangle d04;
    @FXML private Rectangle d14;
    @FXML private Rectangle d24;
    @FXML private Rectangle d34;
    @FXML private Rectangle d44;

    // コンストラクタ
    public PieceD(int playerID, Paint color) {
        super("pieceD.fxml", playerID, color);
        slot = new Slot(0, 0);
//        key = a1;
        drag = new Drag();
    }
}