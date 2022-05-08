package com.example.block_game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class Player {
    private int id;
    private Paint color;
    private PieceSuite pieceSuite;

    @FXML private ButtonPanel buttonPanel;
    private Map<Button, AbstractPiece> button2pieceMap = new HashMap<>();

    @FXML private GridPane gridPane;
    @FXML private Pane gamePane;

    public Player(int id, Paint color, GridPane gridPane, Pane gamePane) {
        this.id = id;
        this.color = color;

        pieceSuite = new PieceSuite(this);
        gamePane.getChildren().addAll(pieceSuite.getPieceList());

        buttonPanel = new ButtonPanel(this, pieceSuite);
        switch (this.id) {
            case 0:
                gridPane.add(buttonPanel, 0, 0);
                break;
            case 1:
                gridPane.add(buttonPanel, 2,0);
                break;
            case 2:
                gridPane.add(buttonPanel, 0,2);
                break;
            case 3:
                gridPane.add(buttonPanel, 2,2);
                break;
            default:
                throw new IllegalArgumentException("Invalid id: "+this.id);
        }


    }

    int getID() {
        return id;
    }
    Paint getColor() { return color; }
    List<AbstractPiece> getPieces() { return pieceSuite.getPieceList(); }

    Map<Button, AbstractPiece> getButton2pieceMap() {
        return button2pieceMap;
    }
}
