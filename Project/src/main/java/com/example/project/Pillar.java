package com.example.project;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Pillar {
    // Attributes
    private Rectangle pillar1;
    private Rectangle pillar2;
    private Rectangle center2;


    public void createPillar() {
        Random random= new Random();
        Random random1= new Random();
        pillar2.setTranslateX(200 + random.nextDouble() *  225);
        pillar2.setWidth(35 +random1.nextDouble() *  110);
        center2.setTranslateX(pillar2.getTranslateX()+pillar2.getWidth()/2-center2.getWidth()/2);
        pillar2.setVisible(true);
        center2.setVisible(true);
    }

    public boolean isStickonDot(double stickX, double stickY) {
        Stick s1= new Stick();
        boolean b = pillar1.getWidth() + s1.getStickLength() > center2.getTranslateX() && pillar1.getWidth() + s1.getStickLength() < center2.getTranslateX() + center2.getWidth() && stickY > center2.getTranslateY() && stickY < center2.getTranslateY() + center2.getHeight();
        return b;
    }
    public Rectangle getPillar1() {
        return pillar1;
    }

    public Rectangle getPillar2() {
        return pillar2;
    }
}