package com.example.block_game;

import javafx.fxml.FXML;

import java.util.List;

/**
 * AbstractPieceの小クラスのコンポジション
 */
final class PieceSuite {
    @FXML private PieceA pieceA;
    @FXML private PieceB pieceB;
    @FXML private PieceC pieceC;
    @FXML private PieceD pieceD;
    private List<AbstractPiece> pieceList;

    private Player player;

    PieceSuite(Player player) {
        this.player = player;
        pieceList = List.of(
                this.pieceA = new PieceA(player),
                this.pieceB = new PieceB(player),
                this.pieceC = new PieceC(player),
                this.pieceD = new PieceD(player)
        );
        for (AbstractPiece piece: pieceList) {
            piece.setDisable(true);
            piece.setOpacity(0);
        }
    }
    List<AbstractPiece> getPieceList() {
        return pieceList;
    }
}
