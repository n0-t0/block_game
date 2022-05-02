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
import java.util.Objects;

abstract class AbstractPiece extends Group {
    ////////////////////////////////////
    // コンストラクタで初期化されたら変化しない値
    private final int playerID;
    // ピースの色
    private final Paint paint;
    /////////////////////////////////////
    // 状態
    // スロット位置
    private Slot slot = new Slot(0,0);
    // ドラッグ用一時変数
    private Drag drag = new Drag();
    private Point selfPoint;
    private Point selfPointOnPressed;
    /////////////////////////////////////

    protected AbstractPiece(String resource, int playerID, Paint paint) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch(IOException exception) {
            System.out.println(this.getClass().getName()+"のロードで例外が発生");
            throw new RuntimeException(exception);
        }
        this.playerID = playerID;
        this.paint = paint;

        // オーバーライド可能なメソッドをコンストラクタで呼ぶのはまずい！！
        this.setFill(paint);
    }

    private @FXML void initialize() {
        this.setOnMousePressed(event -> {
            this.requestFocus();
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


            print2D(getFillSlotInBoard());
//            System.out.println("");
//            print2D(getEdgeSlotInBoard());
//            System.out.println("");
//            print2D(getPointSlotInBoard());
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
                    slot = GameLogic.move(this, slot.getLeft());
                }
                case "l" -> {
                    System.out.println("Right");
                    slot = GameLogic.move(this, slot.getRight());
                }
                case "i" -> {
                    System.out.println("up");
                    slot = GameLogic.move(this, slot.getUp());
                }
                case "k" -> {
                    System.out.println("Down");
                    slot = GameLogic.move(this, slot.getDown());
                }
                default -> {
                }
            }


            print2D(getFillSlotInBoard());/////
//            print2D(getEdgeSlotInBoard());
//            print2D(getPointSlotInBoard());
        });
    }

    private void setFill(Paint paint) {
        for(Node node: this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setFill(paint);
        }
    }

    Slot getSlot() {
        return this.slot;
    }
    Point getSelfPoint() {
        return this.selfPoint;
    }


    ///////////////////////////////////////////
    // キーピースが正立状態で左上に対してどこにあるのか
    // キーピースが回転込みでどこにあるのか
    // 正立状態でのキーピース周りの配置
    // 回転込みでのキーピース周りの配置
    // 左上スロット座標＋回転込みキーピースオフセットでキーピースのスロットを返す
    // 左上スロット座標＋回転込みキーピースオフセット+回転込みキーピースに対する双対位置で全てのピース位置を返す

    private static void print2D(Boolean[][] rectArrayXY) {
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
            if(!child.isDisabled()) rectArrayXY[(int)child.getLayoutY()/50+1][(int)child.getLayoutX()/50+1] = true;
        }

        return setRotateMapping(setReverseMappingY(setReverseMappingX(rectArrayXY)));
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
//                    System.out.println("("+i+","+j+"): ");
                    if(rectArrayXY[i+1][j]==false) {
//                        System.out.println("DOWN_EDGE: ("+(i+1)+", "+j+")");
                        rectEdgeXY[i+1][j] = true;
                    }
                    if(rectArrayXY[i-1][j]==false) {
//                        System.out.println("UP_EDGE: ("+(i-1)+", "+j+")");
                        rectEdgeXY[i-1][j] = true;
                    }
                    if(rectArrayXY[i][j+1]==false) {
//                        System.out.println("RIGHT_EDGE: ("+i+", "+(j+1)+")");
                        rectEdgeXY[i][j+1] = true;
                    }
                    if(rectArrayXY[i][j-1]==false) {
//                        System.out.println("LEFT_EDGE: ("+i+", "+(j-1)+")");
                        rectEdgeXY[i][j-1] = true;
                    }
                    System.out.println("");
                }
            }
        }

        return setRotateMapping(setReverseMappingY(setReverseMappingX(rectEdgeXY)));

    }
    private Boolean[][] getPointSlot() {
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

        for(int i=0; i<yLim; i++) {
            for(int j=0; j<xLim; j++) {
                if(!(rectArrayXY[i][j] || rectEdgeXY[i][j])) {
                    if((i+1)<yLim &&(j+1)<xLim && rectArrayXY[i+1][j+1]) {
                        System.out.println((i+1)+","+(j+1));
//                        System.out.println("LEFT_UP: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                    if((i+1)<yLim && j>0 && rectArrayXY[i+1][j-1]) {
                        System.out.println((i+1)+","+(j-1));
//                        System.out.println("RIGHT_UP: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                    if (i>0 && (j+1)<xLim && rectArrayXY[i-1][j+1]) {
                        System.out.println((i-1)+","+(j+1));
//                        System.out.println("LEFT_DOWN: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                    if(i>0 && j>0 && rectArrayXY[i-1][j-1]) {
                        System.out.println((i-1)+","+(j-1));
//                        System.out.println("RIGHT_DOWN: "+i+","+j);
                        rectPointXY[i][j] = true;
                    }
                }
            }
        }
        return setRotateMapping(setReverseMappingY(setReverseMappingX(rectPointXY)));
    }



    private Boolean[][] getInBoard(Boolean[][] mapping, int xlim, int ylim ,int s, int t) {

        int xLim = 18;
        int yLim = 18;
        Boolean[][] board = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] boardX = new Boolean[xLim];
            Arrays.fill(boardX, false);
            board [i] = boardX;
        }
        for(int i=0; i<ylim; i++) {
            for(int j=0; j<xlim; j++) {
                if( i+t+2>=0 && j+s+2>=0 && (i+t+2)<yLim && (j+s+2)<xLim ) board[i+t+2][j+s+2] = mapping[i][j];
            }
        }
        return board;
    }

    private Boolean[][] getFillSlotInBoard() {
        return getInBoard(getPieceSlot(),7 ,7 ,this.getSlot().x, this.getSlot().y);
    }
    private Boolean[][] getEdgeSlotInBoard() {
        return  getInBoard(getEdgeSlot(), 7, 7, this.getSlot().x, this.getSlot().y);
    }
    private Boolean[][] getPointSlotInBoard() {
        return getInBoard(getPointSlot(), 7, 7, this.getSlot().x, this.getSlot().y);
    }

    //////////////////////////////

    private Boolean[][] rotate(Boolean[][] mapping) {
        int yLim = 7;
        int xLim = 7;
        Boolean[][] rotateXY = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] rotateX = new Boolean[xLim];
            Arrays.fill(rotateX, false);
            rotateXY [i] = rotateX;
        }
        for(int i=0; i<yLim; i++) {
            for(int j=0; j<xLim; j++) {
                rotateXY[j][yLim-i-1] = mapping[i][j];
            }
        }
        return rotateXY;
    }

    private Boolean[][] setRotateMapping(Boolean[][] mapping) {
        int rot = (int)this.getRotate()/90;
        Boolean[][] rotateMapping = new Boolean[0][];
//        System.out.println(rot);
        switch (rot%4) {
            case 0:
                rotateMapping = mapping;
                break;
            case 1:
                rotateMapping = rotate(mapping);
                break;
            case 2:
                rotateMapping = rotate(rotate(mapping));
                break;
            case 3:
                rotateMapping = rotate(rotate(rotate(mapping)));
                break;
            default:
                break;
        }
        return rotateMapping;
    }

    private Boolean[][] reverseX(Boolean[][] mapping) {
        int yLim = 7;
        int xLim = 7;
        Boolean[][] reverseXY = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] reverseX = new Boolean[xLim];
            Arrays.fill(reverseX, false);
            reverseXY[i] = reverseX;
        }
        for(int i=0; i<yLim; i++) {
            for(int j=0; j<xLim; j++) {
                reverseXY[i][xLim-1-j] = mapping[i][j];
            }
        }
        return reverseXY;
    }

    private Boolean[][] reverseY(Boolean[][] mapping) {
        int yLim = 7;
        int xLim = 7;
        Boolean[][] reverseXY = new Boolean[yLim][];
        for(int i=0; i<yLim; i++) {
            Boolean[] reverseX = new Boolean[xLim];
            Arrays.fill(reverseX, false);
            reverseXY[i] = reverseX;
        }
        for(int i=0; i<yLim; i++) {
            for(int j=0; j<xLim; j++) {
                reverseXY[yLim-1-i][j] = mapping[i][j];
            }
        }
        return reverseXY;
    }

    private Boolean[][] setReverseMappingX(Boolean[][] mapping) {
        Boolean[][] reverseMapping = new Boolean[0][];
        switch ((int)this.getScaleX()) {
            case -1:
                reverseMapping = reverseX(mapping);
                break;
            case 1:
                reverseMapping = mapping;
                break;
            default:
                break;

        }
        return reverseMapping;
    }
    private Boolean[][] setReverseMappingY(Boolean[][] mapping) {
        Boolean[][] reverseMapping = new Boolean[0][];
        switch ((int)this.getScaleY()) {
            case -1:
                reverseMapping = reverseY(mapping);
                break;
            case 1:
                reverseMapping = mapping;
                break;
            default:
                break;
        }
        return reverseMapping;
    }





}














