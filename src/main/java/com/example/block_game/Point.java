package com.example.block_game;

public class Point {
    private double x;
    private double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    double x() {
        return x;
    }
    double y() {
        return y;
    }
    void setX(double x) { this.x=x; }
    void setY(double y) { this.y=y; }
    @Override public String toString() {
        return "("+x+","+y+")";
    }
}
