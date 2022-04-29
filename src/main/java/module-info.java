module com.example.block_game {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.block_game to javafx.fxml;
    exports com.example.block_game;
}