package com.example.block_game;
/* キーとなる四角形はピースが占有する5*5のマス目の中心であり、論理座標モデルにおいて回転・並行移動はこれを中心に行う。
Slotクラスはビューにおける座標を表すクラスであり、ビューでは左上の座標を使っている。
KEY＿OFFSETは左上から中心(キースロット)への変換を表す。*/

public class Slot {
    private int x;
    private int y;

    private static final int KEY_OFFSET = 2;
    private static final int LIMIT_MIN_X = 0 - KEY_OFFSET;
    private static final int LIMIT_MIN_Y = 0 - KEY_OFFSET;
    private static final int LIMIT_MAX_X = 11 - KEY_OFFSET;
    private static final int LIMIT_MAX_Y = 11 - KEY_OFFSET;
    Slot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public Point getPoint() {
        return new Point(x*50, y*50);
    }

    @Override public String toString() {
        return "("+x+", "+y+")";
    }

    public Slot getLeft() { return (LIMIT_MIN_X < x) ? new Slot(x-1, y) : this; }
    public Slot getRight() { return (LIMIT_MAX_X > x) ? new Slot(x+1, y) : this; }
    public Slot getUp() { return (LIMIT_MIN_Y < y) ? new Slot(x, y-1) : this; }
    public Slot getDown() { return (LIMIT_MAX_Y > y) ? new Slot(x, y+1) : this; }
}

