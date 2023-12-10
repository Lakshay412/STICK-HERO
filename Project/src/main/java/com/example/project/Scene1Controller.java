package com.example.project;
import java.io.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene1Controller implements Initializable{
    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private MediaView mediaView;
    @FXML
    private Parent root;
    @FXML
    private Text cherry;
    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private Button easy;
    @FXML
    private Button medium;
    @FXML
    private Button hard;
    private static boolean song=true;
    private boolean difficultySet=false;
    @FXML
    private AnchorPane difficulty;
    public boolean getSong() {
        return song;
    }
    public void setSong(boolean song) {
        this.song = song;
    }
    @FXML
    public void setDifficulty(ActionEvent e) throws IOException {
        if(!difficultySet){
            difficultySet=true;
            if(checkdiff()==1){
                easy.setStyle("-fx-background-color: #00ff00");
                medium.setStyle("-fx-background-color: transparent");
                hard.setStyle("-fx-background-color: transparent");
            }
            else if(checkdiff()==3){
                hard.setStyle("-fx-background-color: #00ff00");
                easy.setStyle("-fx-background-color: transparent");
                medium.setStyle("-fx-background-color: transparent");

            }
            else{
                medium.setStyle("-fx-background-color: #00ff00");
                easy.setStyle("-fx-background-color: transparent");
                hard.setStyle("-fx-background-color: transparent");
            }
            difficulty.setVisible(true);
        }
        else{
            difficultySet=false;
            difficulty.setVisible(false);
        }
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
    public void incrementToFile(int diff) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/project/diff.txt"));
            writer.write(Integer.toString(diff));
            writer.close();
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }
    @FXML
    public void setEasy(ActionEvent e) throws IOException {
        incrementToFile(1);
        difficulty.setVisible(false);
        difficultySet=false;
    }
    @FXML
    public void setMedium(ActionEvent e) throws IOException{
        incrementToFile(2);
        difficulty.setVisible(false);
        difficultySet=false;

    }
    @FXML
    public void setHard(ActionEvent e) throws IOException {
        incrementToFile(3);
        difficulty.setVisible(false);
        difficultySet=false;

    }
    public void readFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/project/cherry.txt"));
            int currentValue = Integer.parseInt(reader.readLine());
            reader.close();
            cherry.setText(String.valueOf(currentValue));
        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
    }
    public void initialize(URL url, ResourceBundle rb) {
        Game scene2Controller = new Game();
        if(song && !Game.song1){
            file = new File("src/main/resources/com/example/project/music.mp3");
            media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.getMediaPlayer().play();
            song=false;
        }
        readFromFile();
    }
    public void switchtoScene2(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("scene2.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}