package com.example.block_game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

abstract class AbstractPiece extends Group {
    // ピースの色
    protected Paint paint;
    // キーピースとそのスロット位置
    protected Rectangle key;
    protected Slot slot;
    protected Drag drag = new Drag();

    protected Point selfPoint;
    protected Point selfPointOnPressed;
//    protected abstract T self();

    AbstractPiece(String resource) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    void initialize() {
        this.setOnMousePressed(event -> {
            this.requestFocus();
            System.out.println(this.isFocused());
            drag.setPressValue(event);
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
            slot = GameLogic.dragMove(this);
        });


        this.setOnKeyTyped(event -> {
            this.requestFocus();
            System.out.println(this.isFocused());
            System.out.print("pressed: ");
            switch (event.getCharacter()) {
                case "a" -> {
                    System.out.println("A");
                    this.setRotate(this.getRotate() - 90);
                }
                case "d" -> {
                    System.out.println("D");
                    this.setRotate(this.getRotate() + 90);
                }
                case "s" -> {
                    System.out.println("S");
                    this.setScaleX(this.getScaleX() * (-1));
                }
                case "w" -> {
                    System.out.println("W");
                    this.setScaleY(this.getScaleY() * (-1));
                }
                case "j" -> {
                    System.out.println("Left");
                    slot = GameLogic.keyMove(this, Slot.left(slot));
                }
                case "l" -> {
                    System.out.println("Right");
                    slot = GameLogic.keyMove(this, Slot.right(slot));
                }
                case "i" -> {
                    System.out.println("up");
                    slot = GameLogic.keyMove(this, Slot.up(slot));
                }
                case "k" -> {
                    System.out.println("Down");
                    slot = GameLogic.keyMove(this, Slot.down(slot));
                }
                default -> {
                }
            }
        });
    }

    public void setFill(Paint paint) {
        this.paint = paint;
        for(Node node: this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setFill(paint);
        }
    }
    public Slot getSlot() {
        return this.slot;
    }
    public Point getSelfPoint() {
        return this.selfPoint;
    }



}
