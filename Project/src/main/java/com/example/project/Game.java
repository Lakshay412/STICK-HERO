package com.example.project;

import java.io.*;
import java.util.Random;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.util.Duration;
public class Game implements Initializable,Serializable{
    @FXML
    private Line mystick;
    @FXML
    private Text score;
    private  boolean revive1=false;
    @FXML
    private Stage stage;
    @FXML
    private Rectangle pillar1;
    @FXML
    private Rectangle pillar2;
    @FXML
    private Rectangle center2;
    @FXML
    private ImageView cherry;
    @FXML
    private Text cherryscore;
    @FXML
    ImageView i1;
    @FXML
    ImageView i2;
    @FXML
    ImageView i3;
    @FXML
    private Rectangle pillar3;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private Scene scene;
    private double desired;
    @FXML
    private Parent root;
    public boolean check=true;
    public static int difficulty;
    @FXML
    private Timeline timeline;
    @FXML
    private double stickLength;
    private boolean check2=true;
    @FXML
    private static final double STICK_SPEED=0.00005;
    @FXML
    private Text high;
    @FXML
    private Text low;
    @FXML
    private AnchorPane anchorpane1;

public static int HighScore;
    private boolean flip=true;
    private boolean lak=true;

    private boolean check1=true;
    private boolean touch=false;
    private boolean release=false;
    private boolean fall1=false;
    private boolean fall;
    private boolean cherrybool=false;
    public static boolean song1=false;
    private Hero h1;


    public void initialize(URL url, ResourceBundle rb) {
        Scene1Controller s= new Scene1Controller();
        if(s.getSong()){
            song1=true;
        }
        else{
            song1=false;
        }
        cherryscore.setText(String.valueOf(readFromFile()));
        if(checkdiff()==1){
            Random random = new Random();
            Random random1 = new Random();
            pillar2.setTranslateX(130 + random.nextDouble()* 150);
            pillar2.setWidth(50 + random1.nextDouble()*80);
            center2.setTranslateX(pillar2.getTranslateX() + pillar2.getWidth() / 2 - center2.getWidth() / 2);
        }
        else if(checkdiff()==3){
            Random random = new Random();
            Random random1 = new Random();
            pillar2.setTranslateX(250 + random.nextDouble()*220 );
            pillar2.setWidth(30 +  random1.nextDouble()*40);
            center2.setTranslateX(pillar2.getTranslateX() + pillar2.getWidth() / 2 - center2.getWidth() / 2);
        }
        else{
            Random random = new Random();
            Random random1 = new Random();
            pillar2.setTranslateX(180 + random.nextDouble()* 225);
            pillar2.setWidth(30 + random1.nextDouble()* 100);
            center2.setTranslateX(pillar2.getTranslateX() + pillar2.getWidth() / 2 - center2.getWidth() / 2);
        }
        pillar2.setVisible(true);
        center2.setVisible(true);
    }

    public void checkHighLow() {
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(new FileReader("src/main/resources/com/example/project/score.txt"));//decorator design pattern in bufferd reader and scanner
            int currentValue = Integer.parseInt(in.nextLine());
            in.close();
            if(!check2){
                h1 = Hero.getInstance();
                h1.setScore(currentValue);
                h1.updateScore();
            }
            if (Integer.parseInt(score.getText()) > currentValue) {
                out = new PrintWriter(new FileWriter("src/main/resources/com/example/project/score.txt"));
                out.write(score.getText());
                out.close();
            }
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }
    @FXML
    public void releaseStick() throws InterruptedException{
        if (timeline != null) {
            timeline.stop();
        }
        if(!release) {
            Rotate rotate = new Rotate();
            rotate.setPivotX(mystick.getStartX());
            rotate.setPivotY(mystick.getStartY());
            mystick.getTransforms().add(rotate);
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                    new KeyFrame(Duration.millis(400), new KeyValue(rotate.angleProperty(), 90)));
            timeline1.play();
            release=true;
            timeline1.setOnFinished(event -> {
                if(!check1){
                    Pillar p1= new Pillar();
                    p1.isStickonDot(mystick.getEndX(),mystick.getEndY());
                }
                if(!check2){
                    h1 = Hero.getInstance();
                    h1.walk();
                }
                if(pillar1.getWidth()+stickLength()>center2.getTranslateX() && pillar1.getWidth()+stickLength()<center2.getTranslateX()+center2.getWidth()){
                    score.setText(String.valueOf(Integer.parseInt(score.getText())+1));
                }
                runhero();
            });
        }
        if(!check){
            Stick stick = new Stick();
            stick.setStickLength(stickLength());

            stick.resetStick();
        }
    }
    public  void serialize() throws IOException{
        h1= Hero.getInstance();
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("out.txt"));
            out.writeObject(h1);
        }
        finally {
            out.close();
        }
    }
    public int readFromFile1() {
        Scanner in = null;
        PrintWriter out = null;//decorator design pattern in buffered reader and scanner
        try {
            in = new Scanner(new FileReader("src/main/resources/com/example/project/score.txt"));
            int currentValue = Integer.parseInt(in.nextLine());
            HighScore = currentValue;
            in.close();
            if(!check2){
                h1 = Hero.getInstance();
                h1.setScore(currentValue);
                h1.updateScore();
            }
            return currentValue;
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
        return 0;
    }
    public int checkdiff() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/project/diff.txt"));
            int currentValue = Integer.parseInt(reader.readLine());
            reader.close();
            return currentValue;
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
        return 0;
    }
    public void incrementToFile() {
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(new FileReader("src/main/resources/com/example/project/cherry.txt"));//decorator design pattern in bufferd reader and scanner
            int currentValue = Integer.parseInt(in.nextLine());
           in.close();
            int newValue = currentValue + 1;
            out = new PrintWriter(new FileWriter("src/main/resources/com/example/project/cherry.txt"));
            out.write(Integer.toString(newValue));
            out.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }
//    public void deserialize() throws IOException,ClassNotFoundException{
//        ObjectInputStream in = null;
//        try{
//            in = new ObjectInputStream(new FileInputStream("out.txt"));
//            Hero h2  = (Hero) in.readObject();
//        }
//        finally {
//            in.close();
//        }
//    }
    public void decrementToFile() {
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(new FileReader("src/main/resources/com/example/project/cherry.txt"));//decorator design pattern in bufferd reader and scanner
            int currentValue = Integer.parseInt(in.nextLine());
            in.close();
            int newValue = currentValue -10;
            // Write the updated value back to the file
            out = new PrintWriter(new FileWriter("src/main/resources/com/example/project/cherry.txt"));
            out.write(Integer.toString(newValue));
            out.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }
    public int readFromFile() {
        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(new FileReader("src/main/resources/com/example/project/cherry.txt"));//decorator design pattern in bufferd reader and scanner
            int currentValue = Integer.parseInt(in.nextLine());
            in.close();
            return currentValue;
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
        return 0;
    }

    public void runhero(){
        i1.setVisible(false);
        double time=50;
        double move=10;
        i2.setTranslateX(pillar1.getWidth()-i2.getFitWidth()-10);
        i3.setTranslateX(pillar1.getWidth()-i3.getFitWidth()-10);
        desired =(pillar2.getTranslateX()+pillar2.getWidth())-i2.getFitWidth()-20;
        i2.setVisible(true);
        lak=true;
        if(!check1){
            Pillar p1= new Pillar();
            p1.createPillar();
        }
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(time),i2);
        translateTransition1.setByX(move);
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(time),i3);
        translateTransition2.setByX(move);
        fall=((pillar2.getTranslateX()-pillar1.getTranslateX()-pillar1.getWidth())>stickLength() || (pillar2.getTranslateX()+ pillar2.getWidth()-pillar1.getTranslateX()-pillar1.getWidth())<stickLength());
        translateTransition1.play();
        translateTransition1.setOnFinished(event -> {
            fall1=(flip &&(pillar2.getTranslateX()<=i2.getTranslateX()+i2.getFitWidth()+10));
            if(flip && !revive1 && !cherrybool && (i2.getTranslateX()+i2.getFitWidth()>=cherry.getTranslateX() && i2.getTranslateX()< cherry.getTranslateX()+cherry.getFitWidth())){
                cherrybool=true;
                cherry.setVisible(false);
            }
            i3.setTranslateX(i2.getTranslateX());
            i2.setVisible(false);
            i3.setVisible(true);
            translateTransition2.play();

        });
        translateTransition2.setOnFinished(event -> {
            if(flip && !revive1 && cherrybool && (i3.getTranslateX()+ i3.getFitWidth()>=cherry.getTranslateX() && i3.getTranslateX()< cherry.getTranslateX()+cherry.getFitWidth())){
                cherrybool=true;
                cherry.setVisible(false);
            }
            fall1=(flip &&(pillar2.getTranslateX()<=i3.getTranslateX()+i3.getFitWidth()+10));
            i2.setTranslateX(i3.getTranslateX());
            i3.setVisible(false);
            if(fall1){
                fallinganimation();
            }
            else if (i3.getTranslateX()<desired&& !fall) {
                i2.setVisible(true);
                translateTransition1.play();
            }
            else if(fall&& i3.getTranslateX() <stickLength()+pillar1.getWidth()-30){
                i2.setVisible(true);
                translateTransition1.play();

            }
            else{
                if(fall){
                    fallinganimation();
                }
                else{
                    i1.setTranslateX(pillar2.getTranslateX()+pillar2.getWidth()-i1.getFitWidth()-20);
                    i1.setVisible(true);
                    lak=false;
                    score.setText(String.valueOf(Integer.parseInt(score.getText())+1));
                    generatescene();
                }
            }
        });
    }
    public void fallinganimation(){
        if(fall1) {
            i1.setScaleY(-1);
            i1.setTranslateX(pillar2.getTranslateX()-i1.getFitWidth());
        }
        else if(flip && fall){
            i1.setTranslateY(265);
            i1.setScaleY(-1);
            i1.setTranslateX(stickLength()+pillar1.getWidth()-10);
        }
        else{
            i1.setTranslateX(stickLength()+pillar1.getWidth()-10);
        }
        if(!check){
            Stick stick = new Stick();
            stick.setStickLength(stickLength());
            stick.StickEnough(i1,flip,fall,fall1);
        }
        i1.setVisible(true);
        TranslateTransition t3= new TranslateTransition(Duration.millis(600),i1);
        t3.setByY(350);
        t3.play();
        t3.setOnFinished(Event -> {
            i1.setVisible(false);
        });
        Rotate r1 = new Rotate();
        r1.setPivotX(mystick.getStartX());
        r1.setPivotY(mystick.getStartY());
        mystick.getTransforms().add(r1);
        Timeline timeline1 = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(r1.angleProperty(), 0)),
                new KeyFrame(Duration.millis(600), new KeyValue(r1.angleProperty(), 90)));
        timeline1.play();
        timeline1.setOnFinished(Event->{
            cherry.setVisible(false);
            mystick.setVisible(false);
            checkHighLow();
            high.setText(String.valueOf(readFromFile1()));
            low.setText(score.getText());
            anchorpane1.setVisible(false);
            anchorpane.setVisible(true);
            if(!check2){
                h1 = Hero.getInstance();
                h1.getScore();
            }
            cherrybool=false;
//            try {
//                throw new HerofallException("hero fell down click on play to play more");
//            } catch (HerofallException e) {
//                throw new RuntimeException(e);
//            }
        });
    }
    @FXML
    private void setflip(){
        if(!flip &&(i2.getTranslateX()+10>pillar1.getWidth() || i3.getTranslateX()+10>pillar1.getWidth()) && i2.getTranslateX()<pillar2.getTranslateX()-10 && i3.getTranslateX()<pillar2.getTranslateX()-10 && lak){
            if(!check2){
                h1 = Hero.getInstance();
                h1.flipOnStick();
            }
            i2.setScaleY(-1);
            i3.setScaleY(-1);
            i2.setTranslateY(265);
            i3.setTranslateY(265);
            flip=true;
        }
        else{
            if(!check2){
                h1 = Hero.getInstance();
                h1.flipOnStick();
            }
            i2.setScaleY(1);
            i3.setScaleY(1);
            i2.setTranslateY(220);
            i3.setTranslateY(220);
            flip=false;
        }
    }
    private double stickLength() {
        double x1 = mystick.getStartX();
        double y1 = mystick.getStartY();
        double x2 = mystick.getEndX();
        double y2 = mystick.getEndY();
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    public void generatescene(){
        cherry.setVisible(false);
        if(!check1){
            Cherry c1= new Cherry();
            c1.generateCherry();
        }
        if (cherrybool ){
            incrementToFile();
            cherryscore.setText(String.valueOf(readFromFile()));
            cherrybool=false;
        }
        if(!check1){
            Pillar p1= new Pillar();
            p1.createPillar();
        }
        pillar3.setTranslateX(pillar2.getTranslateX());
        pillar3.setWidth(pillar2.getWidth());
        mystick.setVisible(false);
        pillar1.setVisible(false);
        pillar2.setVisible(false);
        pillar3.setVisible(true);
        center2.setVisible(false);
        TranslateTransition pt1 = new TranslateTransition(Duration.seconds(0.5),pillar3);
        pt1.setByX(-pillar3.getTranslateX());
        TranslateTransition it1= new TranslateTransition(Duration.seconds(0.5),i1);
        it1.setByX(-i1.getTranslateX()+pillar2.getWidth()-i1.getFitWidth()-10);
        pt1.play();
        it1.play();
        thread();
        pt1.setOnFinished(event -> {
            pillar3.setVisible(false);
            pillar1.setWidth(pillar2.getWidth());
            pillar1.setVisible(true);
            Rotate rotate = new Rotate(-90);
            rotate.setPivotX(mystick.getStartX());
            rotate.setPivotY(mystick.getStartY());
            mystick.getTransforms().add(rotate);
            mystick.setStartX(pillar1.getTranslateX()+pillar1.getWidth()+28);
            mystick.setEndX(pillar1.getTranslateX()+pillar1.getWidth()+28);
            mystick.setStartY(10);
            mystick.setEndY(10);
            mystick.setVisible(true);
            if(!check2){
                h1 = Hero.getInstance();
                h1.getScore();
            }
            if(checkdiff()==1){
                Random random = new Random();
                Random random1 = new Random();
                pillar2.setTranslateX(130 + random.nextDouble()* 150);
                pillar2.setWidth(50 + random1.nextDouble()*80);
                center2.setTranslateX(pillar2.getTranslateX() + pillar2.getWidth() / 2 - center2.getWidth() / 2);
            }
            else if(checkdiff()==3){
                Random random = new Random();
                Random random1 = new Random();
                pillar2.setTranslateX(250 + random.nextDouble()*220 );
                pillar2.setWidth(30 +  random1.nextDouble()*40);
                center2.setTranslateX(pillar2.getTranslateX() + pillar2.getWidth() / 2 - center2.getWidth() / 2);
            }
            else{
                Random random = new Random();
                Random random1 = new Random();
                pillar2.setTranslateX(180 + random.nextDouble()* 225);
                pillar2.setWidth(30 + random1.nextDouble()* 100);
                center2.setTranslateX(pillar2.getTranslateX() + pillar2.getWidth() / 2 - center2.getWidth() / 2);
            }
            pillar2.setVisible(true);
            center2.setVisible(true);
            cherrygenerator();
            revive1=false;
            lak=false;
            touch=false;
            release=false;
        });
    }
    @FXML
    private void HomeSwitch(ActionEvent e) throws IOException {
        Scene1Controller s= new Scene1Controller();
        Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        release=false;
        touch=false;
        lak=false;
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void playagain(ActionEvent e) throws IOException{
        anchorpane.setVisible(false);
        release=false;
        touch=false;
        lak=false;
        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void revive(){
        if(Integer.parseInt(cherryscore.getText())>=10){
            decrementToFile();
            cherryscore.setText(String.valueOf(readFromFile()));
            anchorpane.setVisible(false);
            Rotate r1 = new Rotate();
            r1.setPivotX(mystick.getStartX());
            r1.setPivotY(mystick.getStartY());
            mystick.getTransforms().add(r1);
            Timeline timeline1 = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(r1.angleProperty(), 0)),
                    new KeyFrame(Duration.millis(1), new KeyValue(r1.angleProperty(), -180)));
            timeline1.play();
            if(!check1){
                Cherry c1= new Cherry();
                c1.checkCherryCollection();
            }
            timeline1.setOnFinished(Event->{
                mystick.setStartX(pillar1.getTranslateX()+pillar1.getWidth()+28);
                mystick.setEndX(pillar1.getTranslateX()+pillar1.getWidth()+28);
                mystick.setStartY(10);
                mystick.setEndY(10);
                mystick.setVisible(true);
                if(flip){
                    flip=false;
                    lak=false;
                }
                i1.setScaleY(1);
                i2.setScaleY(1);
                i3.setScaleY(1);
                i1.setTranslateY(220);
                i2.setTranslateY(220);
                i3.setTranslateY(220);
                i1.setTranslateX(pillar1.getWidth()-i1.getFitWidth()-10);
                i2.setTranslateX(pillar1.getWidth()-i2.getFitWidth()-10);
                i3.setTranslateX(pillar1.getWidth()-i3.getFitWidth()-10);
                anchorpane1.setVisible(true);
                i1.setVisible(true);
                revive1 =true;
                lak=false;
                release=false;
                touch=false;
            });
        }
    }
    private void cherrygenerator(){
        if(!check1){
            Cherry c1= new Cherry();
            c1.checkCherryCollection();
        }
        Random random3= new Random();
        cherry.setTranslateX(pillar1.getWidth()+10 + random3.nextDouble() * (pillar2.getTranslateX()-pillar1.getWidth()-cherry.getFitWidth()-5));
        cherry.setVisible(true);
    }
    @FXML
    public void extendStick() {
        stickLength=0;
        if(!touch){
            if(!check){
                Stick stick = new Stick();
                stick.setStickLength(stickLength());

                stick.extendStick();
            }
            timeline = new Timeline(new KeyFrame(Duration.millis(0.5), event -> {
                stickLength += STICK_SPEED;
                mystick.setEndY(mystick.getEndY()-stickLength);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            mystick.setStartY(mystick.getEndY()-1.1);
            touch=true;
        }
    }
}