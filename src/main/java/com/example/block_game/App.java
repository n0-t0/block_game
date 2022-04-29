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
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    static Point scene2board;
    @FXML PieceA pieceA1;
    @FXML PieceA pieceA2;
    @FXML private GridPane gridPane;
    @FXML StackPane stackPane;
    @FXML GridPane smallGridPane;
    @FXML Pane gamePane;

    @FXML void initialize() {
        scene2board = new Point(100, 100);
        pieceA1.setFill(Color.CYAN);
        pieceA2.setFill(Color.CORAL);
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