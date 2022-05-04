package com.example.block_game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class App extends Application {
    // ウィンドウ座標とゲーム盤座標の変換用オフセット
    private static Point scene2board = new Point(100, 100);
    private @FXML Pane gamePane;
    private @FXML GridPane gridPane;

    private @FXML Button a1Button;
    private @FXML Button b1Button;
    private @FXML Button c1Button;
    private @FXML Button d1Button;

    private List<Player> players = new ArrayList<>();
    final int PLAYER_NUM = 1;
    List<Paint> colorPreference = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);

    private @FXML void initialize() {
        for(int i=1; i<=PLAYER_NUM; i++) {
            List<AbstractPiece> pieces = List.of(
                    new PieceA(i, colorPreference.get(i-1)),
                    new PieceB(i, colorPreference.get(i-1)),
                    new PieceC(i, colorPreference.get(i-1)),
                    new PieceD(i, colorPreference.get(i-1))
            );
            gamePane.getChildren().addAll(pieces);
            players.add(new Player(i, colorPreference.get(i-1), pieces));
        }

        for(int i=0; i<players.get(0).getPieces().size(); i++) {
            players.get(0).getPieces().get(i).setDisable(true);
            players.get(0).getPieces().get(i).setOpacity(0);
        }

        a1Button.setOnAction(event -> {
            for(int i=0; i<players.get(0).getPieces().size(); i++) {
                players.get(0).getPieces().get(i).setDisable(true);
                players.get(0).getPieces().get(i).setOpacity(0);
            }
            players.get(0).getPieces().get(0).setDisable(false);
            players.get(0).getPieces().get(0).setOpacity(100);
        });

        b1Button.setOnAction(event -> {
            for(int i=0; i<players.get(0).getPieces().size(); i++) {
                players.get(0).getPieces().get(i).setDisable(true);
                players.get(0).getPieces().get(i).setOpacity(0);
            }
            players.get(0).getPieces().get(1).setDisable(false);
            players.get(0).getPieces().get(1).setOpacity(100);
        });
        c1Button.setOnAction(event -> {
            for(int i=0; i<players.get(0).getPieces().size(); i++) {
                players.get(0).getPieces().get(i).setDisable(true);
                players.get(0).getPieces().get(i).setOpacity(0);
            }
            players.get(0).getPieces().get(2).setDisable(false);
            players.get(0).getPieces().get(2).setOpacity(100);
        });
        d1Button.setOnAction(event -> {
            for(int i=0; i<players.get(0).getPieces().size(); i++) {
                players.get(0).getPieces().get(i).setDisable(true);
                players.get(0).getPieces().get(i).setOpacity(0);
            }
            players.get(0).getPieces().get(3).setDisable(false);
            players.get(0).getPieces().get(3).setOpacity(100);
        });

    }

    @Override public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("block_game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 830);
        scene.getStylesheets().add(getClass().getResource("block_game.css").toExternalForm());

        stage.setTitle("Block Game");
        stage.setScene(scene);
        stage.show();
        // リサイズリスナーの登録
        stage.widthProperty().addListener((obsVal, oldVal, newVal) -> {
            ((App)fxmlLoader.getController()).checkGameResize();
        });
    }

    public static void main(String[] args) {
        launch();
    }
    // ウィンドウサイズのリスナー
    private void checkGameResize() {
        scene2board = new Point(
                gridPane.getCellBounds(1,1).getMinX(),
                gridPane.getCellBounds(1,1).getMinY()
        );
    }

    static Point getScene2Board() {
        return scene2board;
    }
}