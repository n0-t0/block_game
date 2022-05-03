package com.example.block_game;

public class Point {
    private double x;
    private double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point plus(Point point) {
        return new Point(x+point.x, y+ point.y);
    }
    public Point minus(Point point) {
        return new Point(x- point.x, y- point.y);
    }
    double getX() { return x; }
    double getY() {
        return y;
    }
    @Override public String toString() {
        return "("+x+","+y+")";
    }

    Slot getSlot() { return ToSlot.getSlot(x, y); }

    static class ToSlot {
        private static Slot getSlot(double x, double y) {
            if(x%50.0 == 0.0 && y%50.0 == 0.0) {
                return new Slot((int)(x/50.0), (int)(y/50.0));
            } else {
                return new Slot((int)(Point.ToSlot.calcNearByX(x)/50), (int)(Point.ToSlot.calcNearByY(y)/50));
            }
        }
        private static double calcNearByX(double x) {
            if(x - ((int)x/50)*50 > 25) {
                return ((int)(x/50))*50.0+50;
            } else {
                return ((int)(x/50))*50.0;
            }
        }
        private static double calcNearByY(double y) {
            if(y - ((int)y/50)*50 > 25) {
                return ((int)(y/50))*50.0+50;
            } else {
                return ((int)(y/50))*50.0;
            }
        }
    }
}
