package com.example.block_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class PieceA extends Group {
    @FXML private Rectangle a0;
    @FXML private Rectangle a1;
    @FXML private Rectangle a2;
    @FXML private Rectangle a3;
    @FXML private Rectangle a4;
    private Paint paint;
    private Slot slot = new Slot(0, 0);
    private Rectangle key;
    private Drag drag = new Drag();

    private Point selfPoint;
    private Point selfPointOnPressed;

    // コンストラクタ
    public PieceA() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pieceA.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // ハンドラ
    @FXML void initialize() {
        this.setOnMousePressed(event -> {
            drag.setPressValue(event);
            requestFocus();
            selfPointOnPressed = new Point(this.getLayoutX(), this.getLayoutY());
        });

        this.setOnMouseDragged(event -> {
            drag.setDragValue(event);
            selfPoint = new Point(
                    selfPointOnPressed.x() + drag.getDragDistance().x(),
                    selfPointOnPressed.y() + drag.getDragDistance().y()
            );
            this.relocate(selfPoint.x(), selfPoint.y());
        });

        this.setOnMouseReleased(event -> {
            drag.setReleaseValue(event);
            selfPoint = new Point(
                    selfPointOnPressed.x() + drag.getReleaseDistance().x(),
                    selfPointOnPressed.y() + drag.getReleaseDistance().y()
            );
            GameLogic.move(this, GameLogic.getSlot(selfPoint.x(), selfPoint.y()));
        });

        this.setOnKeyPressed(event -> {
            System.out.print("pressed: ");
            switch(event.getCode()) {
                case ENTER:
                    System.out.println("Enter");
                    break;
                case A:
                    System.out.println("A");
                    this.setRotate(this.getRotate()-90);
                    break;
                case D:
                    System.out.println("D");
                    this.setRotate(this.getRotate()+90);
                    break;
                case S:
                    System.out.println("S");
                    this.setScaleX(this.getScaleX()*(-1));
                    break;
                case W:
                    System.out.println("W");
                    this.setScaleY(this.getScaleY()*(-1));
                    break;
                case LEFT:
                    System.out.println("Left");
                    slot = GameLogic.move(this, Slot.left(this, slot));
                    break;
                case RIGHT:
                    System.out.println("Right");
                    slot = GameLogic.move(this, Slot.right(this, slot));
                    break;
                case UP:
                    System.out.println("up");
                    slot = GameLogic.move(this, Slot.up(this, slot));
                    break;
                case DOWN:
                    System.out.println("Down");
                    slot = GameLogic.move(this, Slot.down(this, slot));
                    break;
                default:
                    break;
            }
        });
    }
    // 色変えメソッド
    public void setFill(Paint paint) {
        this.paint = paint;
        for(Node node: this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setFill(paint);
        }
    }

    //　キーのスロット位置を返す
    public Slot getSlot() {
        return this.slot;
    }
}
