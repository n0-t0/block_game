package com.example.block_game;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

abstract class AbstractPiece extends Group {
    protected int playerID;
    protected Paint color;
    // ピースの色
    protected Paint paint;
    // キーピースとそのスロット位置
    protected Rectangle key;
    protected Slot slot;
    protected Drag drag = new Drag();

    protected Point selfPoint;
    protected Point selfPointOnPressed;

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }
    public void selColor(Paint color) {
        this.color = color;
    }



    AbstractPiece(String resource, int playerID, Paint color) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch(IOException exception) {
            throw new RuntimeException(exception);
        }
        this.playerID = playerID;
        this.color = color;
        this.setFill(color);
    }

    @FXML
    void initialize() {
        this.setFill(color);

        this.setOnMousePressed(event -> {

            getPointSlot();////

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
                    slot = GameLogic.move(this, Slot.left(slot));
                }
                case "l" -> {
                    System.out.println("Right");
                    slot = GameLogic.move(this, Slot.right(slot));
                }
                case "i" -> {
                    System.out.println("up");
                    slot = GameLogic.move(this, Slot.up(slot));
                }
                case "k" -> {
                    System.out.println("Down");
                    slot = GameLogic.move(this, Slot.down(slot));
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


    ///////////////////////////////////////////
    // キーピースが正立状態で左上に対してどこにあるのか
    // キーピースが回転込みでどこにあるのか
    // 正立状態でのキーピース周りの配置
    // 回転込みでのキーピース周りの配置
    // 左上スロット座標＋回転込みキーピースオフセットでキーピースのスロットを返す
    // 左上スロット座標＋回転込みキーピースオフセット+回転込みキーピースに対する双対位置で全てのピース位置を返す

    private Boolean[][] getPieceSlot() {
        int xLim = 7;
        int yLim = 7;
        ObservableList<Node> children = this.getChildren();

        Boolean[][] rectArrayXY = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] rectArrayX = new Boolean[xLim];
            Arrays.fill(rectArrayX, false);
            rectArrayXY [i] = rectArrayX;
        }

        for(Node child: children) {
            rectArrayXY[(int)child.getLayoutY()/50+1][(int)child.getLayoutX()/50+1] = true;
        }

        print2D(rectArrayXY);



        return rectArrayXY;
    }

    static void print2D(Boolean[][] rectArrayXY) {
        Arrays.stream(rectArrayXY).forEach((arrayX) ->{
            Arrays.stream(arrayX).forEach((elem) ->{
                if(elem.toString().equals("true")) {
                    System.out.print("\u001b[00;31m"+elem.toString()+" "+"\u001b[00m"+",");
                } else {
                    System.out.print(elem.toString()+",");
                }
            });
            System.out.println("");
        });
    }

    private Boolean[][] getEdgeSlot() {
        int xLim = 7;
        int yLim = 7;

        Boolean[][] rectArrayXY = getPieceSlot();


        Boolean[][] rectEdgeXY = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] rectEdgeX = new Boolean[xLim];
            Arrays.fill(rectEdgeX, false);
            rectEdgeXY [i] = rectEdgeX;
        }

        for(int i=0+1; i<yLim-1; i++) {
            for(int j=0+1; j<xLim-1; j++) {
                if(rectArrayXY[i][j]==true) {
                    System.out.println("("+i+","+j+"): ");
                    if(rectArrayXY[i+1][j]==false) {
                        System.out.println("DOWN_EDGE: ("+(i+1)+", "+j+")");
                        rectEdgeXY[i+1][j] = true;
                    }
                    if(rectArrayXY[i-1][j]==false) {
                        System.out.println("UP_EDGE: ("+(i-1)+", "+j+")");
                        rectEdgeXY[i-1][j] = true;
                    }
                    if(rectArrayXY[i][j+1]==false) {
                        System.out.println("RIGHT_EDGE: ("+i+", "+(j+1)+")");
                        rectEdgeXY[i][j+1] = true;
                    }
                    if(rectArrayXY[i][j-1]==false) {
                        System.out.println("LEFT_EDGE: ("+i+", "+(j-1)+")");
                        rectEdgeXY[i][j-1] = true;
                    }
                    System.out.println("");
                }
            }
        }


        print2D(rectEdgeXY);
        return rectEdgeXY;

    }
    public void getPointSlot() {
        int xLim = 7;
        int yLim = 7;
        Boolean[][] rectArrayXY = getPieceSlot();
        Boolean[][] rectEdgeXY = getEdgeSlot();
        System.out.println("");


        Boolean[][] rectPointXY = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] rectPointX = new Boolean[xLim];
            Arrays.fill(rectPointX, false);
            rectPointXY [i] = rectPointX;
        }

        for(int i=0; i<yLim-1; i++) {
            for(int j=0; j<xLim-1; j++) {
                if(!(rectArrayXY[i][j] || rectEdgeXY[i][j])) {
                    if(rectArrayXY[i+1][j+1]) {
                        System.out.println((i+1)+","+(j+1));
                        System.out.println("LEFT_UP: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                    if(j>0 && rectArrayXY[i+1][j-1]) {
                        System.out.println((i+1)+","+(j-1));
                        System.out.println("RIGHT_UP: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                    if (i>0 && rectArrayXY[i-1][j+1]) {
                        System.out.println((i-1)+","+(j+1));
                        System.out.println("LEFT_DOWN: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                    if(i>0 && j>0 && rectArrayXY[i-1][j-1]) {
                        System.out.println((i-1)+","+(j-1));
                        System.out.println("RIGHT_DOWN: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                }
            }
        }
        print2D(rectArrayXY);
        System.out.println("");
        print2D(rectPointXY);

    }
}














