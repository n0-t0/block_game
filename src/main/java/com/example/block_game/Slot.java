package com.example.block_game;

class Slot {
    int x;
    int y;
    /*
    キーとなる四角形はピースが占有する5*5のマス目の中心であり、論理座標モデルにおいて回転・並行移動はこれを中心に行う。
    Slotクラスはビューにおける座標を表すクラスであり、ビューでは左上の座標を使っている。
    KEY＿OFFSETはその変換を表す
    */
    public static final int KEY_OFFSET = 2;
    public static final int LIMIT_MIN_X = 0 - KEY_OFFSET;
    public static final int LIMIT_MIN_Y = 0 - KEY_OFFSET;
    public static final int LIMIT_MAX_X = 11 - KEY_OFFSET;
    public static final int LIMIT_MAX_Y = 11 - KEY_OFFSET;
    Slot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Slot getLeft() { return (LIMIT_MIN_X < x) ? new Slot(x-1, y) : this; }
    Slot getRight() { return (LIMIT_MAX_X > x) ? new Slot(x+1, y) : this; }
    Slot getUp() { return (LIMIT_MIN_Y < y) ? new Slot(x, y-1) : this; }
    Slot getDown() { return (LIMIT_MAX_Y > y) ? new Slot(x, y+1) : this; }
}

