package com.example.project;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class Stick {
    // Attributes
    private Line mystick;
    private double stickLength;


    public void extendStick() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
            stickLength += 0.0008;
            mystick.setEndY(mystick.getEndY() - stickLength);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        mystick.setStartY(mystick.getEndY()-1.1);
    }

    public void resetStick() {
        Pillar p1 =new Pillar();
        Hero h1 = new Hero();
        Rotate rotate = new Rotate();
        rotate.setPivotX(mystick.getStartX());
        rotate.setPivotY(mystick.getStartY());
        mystick.getTransforms().add(rotate);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(400), new KeyValue(rotate.angleProperty(), 90)));
        timeline1.play();
        timeline1.setOnFinished(event -> {
            h1.walk();
        });
    }

    public double getStickLength() {
        double x1 = mystick.getStartX();
        double y1 = mystick.getStartY();
        double x2 = mystick.getEndX();
        double y2 = mystick.getEndY();
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }



    public boolean StickEnough(ImageView i1, boolean flip, boolean fall, boolean fall1 ){
        if(fall1) {
            Pillar p1= new Pillar();
            i1.setScaleY(-1);
            i1.setTranslateX(p1.getPillar2().getTranslateX());
        }
        else if(flip && fall){
            i1.setTranslateY(265);
            i1.setScaleY(-1);
            i1.setTranslateX(getStickLength());
        }
        else{
            i1.setTranslateX(getStickLength());
        }
        return fall1;
    }

    public void setStickLength(double length) {

        this.stickLength = length;
    }
}
