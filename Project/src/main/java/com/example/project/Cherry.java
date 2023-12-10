package com.example.project;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Random;

public class Cherry {
    private String cherryImagePath="src/main/resources/com/example/project/cherry.png";
    private ImageView cherry;
    private Text cherryscore;

    private boolean cherrybool=false;
    public void incrementToFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cherryImagePath));
            int currentValue = Integer.parseInt(reader.readLine());
            reader.close();
            int newValue = currentValue + 1;
            BufferedWriter writer = new BufferedWriter(new FileWriter(cherryImagePath));
            writer.write(Integer.toString(newValue));
            writer.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }
    public int readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cherryImagePath));
            int currentValue = Integer.parseInt(reader.readLine());
            reader.close();
            return currentValue;
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
        return 0;
    }

    public void checkCherryCollection() {
        incrementToFile();
        cherryscore.setText(String.valueOf(readFromFile()));
        cherrybool=false;
    }
    public void generateCherry() {
        Pillar p1= new Pillar();
        Random random3= new Random();
        cherry.setTranslateX(p1.getPillar1().getWidth()+10 + random3.nextDouble() * (p1.getPillar2().getTranslateX()-p1.getPillar1().getWidth()-cherry.getFitWidth()-5));
        cherry.setVisible(true);
    }
}
