package com.example.project;


import javafx.animation.TranslateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;

public class Hero {
    //Singleton pattern
    private static Hero h1 = null;
    public static Hero getInstance(){
        if(h1==null){
            h1 = new Hero();
        }
        return h1;
    }


    private int currentScore;
    private ImageView i1;
    private ImageView i2;
    private ImageView i3;
    private double desired;
    private Text score;

    public void walk() {
        Pillar p1 = new Pillar();
        i1.setVisible(false);
        double time=50;
        double move=10;
        i2.setTranslateX(p1.getPillar1().getWidth()-i2.getFitWidth()-10);
        i3.setTranslateX(p1.getPillar1().getWidth()-i3.getFitWidth()-10);
        desired =(p1.getPillar2().getTranslateX()+p1.getPillar2().getWidth())-i2.getFitWidth()-20;
        i2.setVisible(true);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(time),i2);
        translateTransition1.setByX(move);
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(time),i3);
        translateTransition2.setByX(move);
        translateTransition1.play();
    }

    public void flipOnStick() {
        Pillar p1 = new Pillar();

        if((i2.getTranslateX()+10>p1.getPillar1().getWidth() || i3.getTranslateX()+10>p1.getPillar2().getWidth()) && i2.getTranslateX()<p1.getPillar2().getTranslateX()-10 && i3.getTranslateX()<p1.getPillar2().getTranslateX()-10 ){
            i2.setScaleY(-1);
            i3.setScaleY(-1);
            i2.setTranslateY(265);
            i3.setTranslateY(265);
        }
        else{
            i2.setScaleY(1);
            i3.setScaleY(1);
            i2.setTranslateY(220);
            i3.setTranslateY(220);

        }
    }

    public void updateScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/project/score.txt"));
            int currentValue = Integer.parseInt(reader.readLine());
            reader.close();
            if (Integer.parseInt(score.getText()) > currentValue) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/project/score.txt"));
                writer.write(score.getText());
                writer.close();
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }

    public int getScore() {
        return currentScore;
    }

    public void setScore(int score) {
    }
}
