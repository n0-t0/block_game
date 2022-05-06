package com.example.block_game;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.Arrays;

/**
 * 抽象化されたピースを表すクラスで、
 * PieceXの親クラスであり、ブロックの空間的配置を除くほぼ全ての振る舞いを規定します。
 * 状態として現在の左上論理位置を持ち、それを自分の形を考慮して論理配置として外部に公開します。
 * 左上位置(ビューでのみ使用)、左上論理位置、キーとなる四角形(小クラスでピースの中心)の論理位置は全て異なる概念であることに注意してください。
 * 継承したクラスは1マス50、5*5の大きさのピースでかつ中心に実体の四角形を持つことを要求します。
 * そうでない場合、正しい論理配置の提供は保証されません。
 */

abstract class AbstractPiece extends Group {
    ///////////////////////////////////
    // 定数
    final int PIECE_LIM_X = 7;
    final int PIECE_LIM_Y = 7;
    final int BOARD_LIM_X = 18;
    final int BOARD_LIM_Y = 18;
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
    private DragView dragView = new DragView();
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
        this.setFill(paint);
    }

    ///////////////////////////////////////////
    // 座標関係ユーティリティ //

    /**
     * ゲーム盤上の座標系でのマウスの押下位置を返却します。
     * 呼び出し位置によって値が変化することに注意してください。
     * @param event マウスイベントオブジェクト
     * @return ゲーム盤上の座標系でのマウスの押下位置
     */
    //
    private Point getMousePointInBoard(MouseEvent event) {
        Point mousePointInScene = new Point(event.getSceneX(), event.getSceneY());
        return mousePointInScene.minus(App.getScene2Board());
    }

    /**
     * ゲーム盤上の座標系での自分(ピース)の左上位置を返却します。
     * 呼び出し位置によって値が変化することに注意してください。
     * このメソッドは単にJavafx.scene.Node.getLayoutX(), YのPoint型ラッパーです。
     * @return ゲーム盤上の座標系でのピースの押下位置
     */
    private Point getSelfPointInBoard() {
        return new Point(this.getLayoutX(), this.getLayoutY());
    }

    /**
     * ゲーム盤上の座標系でのピース左上位置を指定して移動します。
     * このメソッドは単にJavafx.scene.Node.relocate()のPoint型ラッパーです。
     * @param point ゲーム盤上の座標系でのピース左上位置
     */
    private void relocate(Point point) {
        this.relocate(point.getX(), point.getY());
    }
    /////////////////////////////////////////////
    /**
     * ドラッグ中のビューの更新にのみ使うクラス
     * ドラッグ開始位置でのマウスの座標と現在(ドラッグ中)のマウスの座標の差を取り、ドラッグ開始時のピースの座標に加算して返却する役割を担います。
     * ゲーム盤上の座標系において、ピースの左上位置とマウスの押下位置は一致しませんが、移動距離は整合しなければならないことに注意してください。
     */
    private final static class DragView {
        private Point mousePress = new Point( 0.0, 0.0);
        private Point mouseDrag = new Point( 0.0, 0.0);
        private Point piecePress = new Point(0.0, 0.0);
        private Point getView() {
            Point moveDistance = mouseDrag.minus(mousePress);
            return piecePress.plus(moveDistance);
        }
    }
    ////////////////////////////////////////
    // initialize //
    /**
     * initializeはコンストラクタ、子コンポーネントidの変数へのバインド後に自動的に呼ばれ、各種イベントハンドラの登録を行います。
     */
    private @FXML void initialize() {
        this.setOnMousePressed(event -> {

            // クリックされたらフォーカスする
            this.requestFocus();

            // ドラッグ中の一時的なビューの更新のため、この時のマウス・自分(ピース)の位置をゲーム盤常の座標で覚えておく
            dragView.mousePress = getMousePointInBoard(event);
            dragView.piecePress = getSelfPointInBoard();

        });

        this.setOnMouseDragged(event -> {

            // ドラッグ中の一時的なビューの更新のため、この時のマウスの位置をゲーム盤常の座標で覚えておく
            dragView.mouseDrag = getMousePointInBoard(event);

            // 自分の位置のビューだけマウスに追随して移動する(モデルはリリース時にチェックを受けてから更新される)
            this.relocate(dragView.getView());

        });

        this.setOnMouseReleased(event -> {

            // この時の自分(ピース)の位置をゲーム盤常の座標で取得
            // モデル・ビューの更新可否チェックを外部に投げ、結果をモデルに入れる
            slot =  GameLogic.move(this, getSelfPointInBoard().getSlot());

            // 帰ってきたモデルの通りにビューを更新する
            this.relocate(slot.getPoint());

            testPrint(); // test
        });

        this.setOnKeyTyped(event -> {
            this.requestFocus();
            System.out.println(this.isFocused());
            System.out.print("pressed: ");
            switch (event.getCharacter()) {
                case "a" -> {
                    this.setRotate(this.getRotate() - 90);
                }
                case "d" -> {
                    this.setRotate(this.getRotate() + 90);
                }
                case "s" -> {
                    this.setScaleX(this.getScaleX() * (-1));
                }
                case "w" -> {
                    this.setScaleY(this.getScaleY() * (-1));
                }
                case "j" -> {
                    slot = GameLogic.move(this, slot.getLeft());
                    this.relocate(slot.getPoint());
                }
                case "l" -> {
                    slot = GameLogic.move(this, slot.getRight());
                    this.relocate(slot.getPoint());
                }
                case "i" -> {
                    slot = GameLogic.move(this, slot.getUp());
                    this.relocate(slot.getPoint());
                }
                case "k" -> {
                    slot = GameLogic.move(this, slot.getDown());
                    this.relocate(slot.getPoint());
                }
                default -> { }
            }
            testPrint(); // test
        });
    }

    private void testPrint() {
        System.out.println("");
        System.out.println("Fill: ");
        print2D(getFillSlotInBoard());
        System.out.println("");
//        System.out.println("Edge");
//        print2D(getEdgeSlotInBoard());
//        System.out.println("");
//        System.out.println("Diagonal");
//        print2D(getDiagonalSlotInBoard());
//        System.out.println("");
    }
    //////////////////////////////////////////////
    // 色変え

    /**
     * コンストラクタで呼ばれ、色を設定します。
     * @param paint ピースの色
     */
    private void setFill(Paint paint) {
        for(Node node: this.getChildren()) {
            Rectangle rectangle = (Rectangle) node;
            rectangle.setFill(paint);
        }
    }

    /**
     * 外部に現在の自分の左上論理位置の状態を公開します。
     * @return
     */
    public Slot getSlot() {
        return this.slot;
    }

    ///////////////////////////////////////////
    ///////////////////////////////////////////
    // モデル更新用メソッド //

    // デバッグ用プリント
    private static void print2D(Boolean[][] rectArrayXY) {
        for(int i=0; i<rectArrayXY.length; i++) {
            for(int j=0; j<rectArrayXY[i].length; j++) {
                if(i<3 || j<3 || i>14 || j>14) System.out.print("\u001b[43m");
                if(rectArrayXY[i][j].toString().equals("true")) {
                    System.out.print("\u001b[31m"+rectArrayXY[i][j].toString()+" "+","+"\u001b[00m");
                } else {
                    System.out.print(rectArrayXY[i][j].toString()+",");
                }
                if(i<3 || j<3 || i>14 || j>14) System.out.print("\u001b[00m");
            }
            System.out.println("");
        }
    }

    // このピースで埋められているスロットをキースロットの周囲7*7で返す
    private Boolean[][] getFillSlot() {
        ObservableList<Node> children = this.getChildren();

        Boolean[][] rectArrayXY = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] rectArrayX = new Boolean[PIECE_LIM_X];
            Arrays.fill(rectArrayX, false);
            rectArrayXY [i] = rectArrayX;
        }

        for(Node child: children) {
            if(!child.isDisabled()) rectArrayXY[(int)child.getLayoutY()/50+1][(int)child.getLayoutX()/50+1] = true;
        }
//
//
//        System.out.println(setRotateMapping(setReverseMappingY(setReverseMappingX(rectArrayXY))));

        return setRotateMapping(setReverseMappingY(setReverseMappingX(rectArrayXY)));
    }

    // このピースに面するスロットをキースロットの周囲7*7で返す
    private Boolean[][] getEdgeSlot() {

        Boolean[][] rectArrayXY = getFillSlot();

        Boolean[][] rectEdgeXY = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] rectEdgeX = new Boolean[PIECE_LIM_X];
            Arrays.fill(rectEdgeX, false);
            rectEdgeXY [i] = rectEdgeX;
        }

        for(int i=0+1; i<PIECE_LIM_Y-1; i++) {
            for(int j=0+1; j<PIECE_LIM_X-1; j++) {
                if(rectArrayXY[i][j]==true) {
                    if(rectArrayXY[i+1][j]==false) { rectEdgeXY[i+1][j] = true; }
                    if(rectArrayXY[i-1][j]==false) { rectEdgeXY[i-1][j] = true; }
                    if(rectArrayXY[i][j+1]==false) { rectEdgeXY[i][j+1] = true; }
                    if(rectArrayXY[i][j-1]==false) { rectEdgeXY[i][j-1] = true; }
                }
            }
        }
        return rectEdgeXY;
    }

    // このピースと対角配置にあるスロットをキースロットの周囲7*7で返す
    private Boolean[][] getDiagonalSlot() {

        Boolean[][] rectArrayXY = getFillSlot();
        Boolean[][] rectEdgeXY = getEdgeSlot();

        Boolean[][] rectPointXY = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] rectPointX = new Boolean[PIECE_LIM_X];
            Arrays.fill(rectPointX, false);
            rectPointXY [i] = rectPointX;
        }

        for(int i=0; i<PIECE_LIM_Y; i++) {
            for(int j=0; j<PIECE_LIM_X; j++) {
                if(!(rectArrayXY[i][j] || rectEdgeXY[i][j])) {
                    if(i+1<PIECE_LIM_Y   && j+1<PIECE_LIM_X   && rectArrayXY[i+1][j+1]) { rectPointXY[i][j] = true; }
                    if(i+1<PIECE_LIM_Y   && j>0        && rectArrayXY[i+1][j-1]) { rectPointXY[i][j] = true; }
                    if(i>0        && j+1<PIECE_LIM_X   && rectArrayXY[i-1][j+1]) { rectPointXY[i][j] = true; }
                    if(i>0        && j>0        && rectArrayXY[i-1][j-1]) { rectPointXY[i][j] = true; }
                }
            }
        }
        return rectPointXY;
    }


    // ピースのキースロットを中心とした7*7スロットを、盤面12*12とキーピースからのはみ出し分3スロットを考慮した18スロットに埋め込む
    private Boolean[][] getInBoard(Boolean[][] mapping, int PIECE_LIM_X, int PIECE_LIM_Y ,int s, int t) {



        Boolean[][] board = new Boolean[BOARD_LIM_Y][];
        for(int i=0; i<BOARD_LIM_Y; i++) {
            Boolean[] boardX = new Boolean[BOARD_LIM_X];
            Arrays.fill(boardX, false);
            board [i] = boardX;
        }
        for(int i=0; i<PIECE_LIM_Y; i++) {
            for(int j=0; j<PIECE_LIM_X; j++) {
                if( i+t+2>=0 && j+s+2>=0 && (i+t+2)<BOARD_LIM_Y && (j+s+2)<BOARD_LIM_X ) board[i+t+2][j+s+2] = mapping[i][j];
            }
        }
        return board;
    }

    // モデル出力API
    public Boolean[][] getFillSlotInBoard() {
        return getInBoard(getFillSlot(),7 ,7 ,slot.getX(), slot.getY());
    }
    public Boolean[][] getEdgeSlotInBoard() {
        return  getInBoard(getEdgeSlot(), 7, 7, slot.getX(), slot.getY());
    }
    public Boolean[][] getDiagonalSlotInBoard() {
        return getInBoard(getDiagonalSlot(), 7, 7, slot.getX(), slot.getY());
    }

    // 90度回転
    private Boolean[][] rotate(Boolean[][] mapping) {
        Boolean[][] rotateXY = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] rotateX = new Boolean[PIECE_LIM_X];
            Arrays.fill(rotateX, false);
            rotateXY [i] = rotateX;
        }
        for(int i=0; i<PIECE_LIM_Y; i++) {
            for(int j=0; j<PIECE_LIM_X; j++) {
                rotateXY[j][PIECE_LIM_Y-i-1] = mapping[i][j];
            }
        }
        return rotateXY;
    }
    // 回転を加える
    private Boolean[][] setRotateMapping(Boolean[][] mapping) {
        int rot = (int)this.getRotate()/90;

        Boolean[][] rotateMapping = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] rotateMappingX = new Boolean[PIECE_LIM_X];
            Arrays.fill(rotateMappingX, false);
            rotateMapping [i] = rotateMappingX;
        }
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
    // x反転
    private Boolean[][] reverseX(Boolean[][] mapping) {
        Boolean[][] reverseXY = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] reverseX = new Boolean[PIECE_LIM_X];
            Arrays.fill(reverseX, false);
            reverseXY[i] = reverseX;
        }
        for(int i=0; i<PIECE_LIM_Y; i++) {
            for(int j=0; j<PIECE_LIM_X; j++) {
                reverseXY[i][PIECE_LIM_X-1-j] = mapping[i][j];
            }
        }
        return reverseXY;
    }
    // y反転
    private Boolean[][] reverseY(Boolean[][] mapping) {
        Boolean[][] reverseXY = new Boolean[PIECE_LIM_Y][];
        for(int i=0; i<PIECE_LIM_Y; i++) {
            Boolean[] reverseX = new Boolean[PIECE_LIM_X];
            Arrays.fill(reverseX, false);
            reverseXY[i] = reverseX;
        }
        for(int i=0; i<PIECE_LIM_Y; i++) {
            for(int j=0; j<PIECE_LIM_X; j++) {
                reverseXY[PIECE_LIM_Y-1-i][j] = mapping[i][j];
            }
        }
        return reverseXY;
    }
    // x反転を設定
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
    // y反転を設定
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
    ///////////////////////////////////

}














