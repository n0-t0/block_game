package com.example.block_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.*;

public final class ButtonPanel extends GridPane {
    @FXML private Button aButton;
    @FXML private Button bButton;
    @FXML private Button cButton;
    @FXML private Button dButton;

    private List<Button> buttonList = new ArrayList<>();

    private Player player;
    private PieceSuite pieceSuite;

    private Map<Button, AbstractPiece> button2pieceMap = new HashMap<>();



    ButtonPanel(Player player, PieceSuite pieceSuite) {
        System.out.println("Button Panel instanced");
        this.player = player;
        this.pieceSuite = pieceSuite;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("buttonPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch(IOException exception) {
            System.out.println(this.getClass().getName()+"のロードで例外が発生");
            throw new RuntimeException(exception);
        }


    }

    List<Button> getButtonList() {
        return buttonList;
    }

    @FXML private void initialize() {
        System.out.println("button Panel initialize");

        this.buttonList = List.of(
                this.aButton,
                this.bButton,
                this.cButton,
                this.dButton
        );

        for(int i=0; i<buttonList.size(); i++) {
            AbstractPiece piece = pieceSuite.getPieceList().get(i);
            button2pieceMap.put(buttonList.get(i), piece);

            buttonList.get(i).setOnAction(event -> {
                for(AbstractPiece pieces: pieceSuite.getPieceList()) {
                    pieces.setDisable(true);
                    pieces.setOpacity(0);
                }
                piece.setDisable(false);
                piece.setOpacity(100);
                piece.requestFocus();
            });
        }

    }
}
