package com.example.block_game;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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

    private List<Player> players = new ArrayList<>();
    final int PLAYER_NUM = 4;
    List<Paint> colorPreference = List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);



    private @FXML void initialize() {
        for(int i=0; i<PLAYER_NUM; i++) {
            players.add(new Player(i, colorPreference.get(i), this.gridPane, this.gamePane));
        }
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