package com.example.block_game;

import com.example.block_game.App;
import com.example.block_game.Point;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import static javafx.scene.input.MouseEvent.*;

// Dragはストローク一回分の情報を持つ
public class Drag {
    Point press = new Point( - 1.0, -1.0);
    Point drag = new Point( - 1.0, -1.0);
    Point release = new Point( - 1.0, -1.0);

    public void setPressValue(MouseEvent event) {
        if(event.getEventType() == MOUSE_PRESSED) {
            this.press = new Point(event.getSceneX()- App.scene2board.x(), event.getSceneY()-App.scene2board.y());
            System.out.println("offset: "+App.scene2board.x());
            System.out.println("press: "+this.press.toString());
        }
    }
    public Point getPressValue() {
        return press;
    }

    public void setDragValue(MouseEvent event) {
        if(event.getEventType() == MOUSE_DRAGGED) {
            this.drag = new Point(event.getSceneX()-App.scene2board.x(), event.getSceneY()-App.scene2board.y());
            System.out.println("drag: "+this.drag.toString());
        }
    }
    public Point getDragValue() {
        return drag;
    }



    public void setReleaseValue(MouseEvent event) {
        if(event.getEventType() == MOUSE_RELEASED) {
            this.release = new Point(event.getSceneX()-App.scene2board.x(), event.getSceneY()-App.scene2board.y());
            System.out.println("release: "+this.release.toString());
        }
    }
    public Point getReleaseValue() {
        return release;
    }

    // マウスでドラッグするときのノードの整合位置
    public Point getDragDistance() {
        return new Point(drag.x() - press.x(), drag.y() - press.y());
    }
    public  Point getReleaseDistance() {
        return new Point(release.x() - press.x(), release.y() - press.y());
    }

    public  void reset() {
        press = new Point( - 1.0, -1.0);
        drag = new Point( - 1.0, -1.0);
        release = new Point( - 1.0, -1.0);
    }
//    public Point getPieceLayout() {
//        return  new Point(press.x() + getDragDistance().x(), press.y() + getDragDistance().y());
//    }

}
