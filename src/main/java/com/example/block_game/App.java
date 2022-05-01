package com.example.block_game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    static Point scene2board;
//    @FXML PieceA pieceA1;
//    @FXML PieceA pieceA2;
//    @FXML PieceB pieceB1;
//    @FXML PieceB pieceB2;
    @FXML private GridPane gridPane;
    @FXML StackPane stackPane;
    @FXML GridPane smallGridPane;
    @FXML Pane gamePane;

    @FXML void initialize() {
        scene2board = new Point(100, 100);

        List<Paint> colorPreference = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
        List<Player> players = new ArrayList<>();
        int playerNum = 1;
        for(int i=1; i<=playerNum; i++) {
            List<AbstractPiece> pieces = List.of(
                    new PieceA(i, colorPreference.get(i-1)),
                    new PieceB(i, colorPreference.get(i-1)),
                    new PieceC(i, colorPreference.get(i-1)),
                    new PieceD(i, colorPreference.get(i-1))
            );
            gamePane.getChildren().addAll(pieces);
            players.add(new Player(i, colorPreference.get(i-1), pieces));
        }
//        pieceA1.requestFocus();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("block_game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 810);
        scene.getStylesheets().add(getClass().getResource("block_game.css").toExternalForm());

        stage.setTitle("Block Game");
        stage.setScene(scene);
        stage.show();

        stage.widthProperty().addListener((obsVal, oldVal, newVal) -> {
            ((App)fxmlLoader.getController()).checkGameResize();
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void checkGameResize() {
        scene2board.setX(gridPane.getCellBounds(1,1).getMinX());
        scene2board.setY(gridPane.getCellBounds(1,1).getMinY());
        System.out.println(scene2board.x()+","+scene2board.y());
    }
}