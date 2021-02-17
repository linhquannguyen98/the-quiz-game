package sample;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Question {

    static Random rand = new Random();
    static ArrayList<Button> button;
    static int score = 0;
    static int questionAmount = 1;
    static int Index = 0;
    static int correctAnswer = 0;


    String question;
    String correct;
    ArrayList<String> wrongs;
    Button buttonRandom;

    public Question(String question, String correct, String wrong1, String wrong2, String wrong3) {
        this.question = question;
        this.correct = correct;
        this.wrongs = new ArrayList<>();
        this.wrongs.add(wrong1);
        this.wrongs.add(wrong2);
        this.wrongs.add(wrong3);
    }

    public static ArrayList<Question> load(String filename) {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            Path filePath = Paths.get("./" + filename);
            Files.lines(filePath).forEach(line -> {
                if (line.startsWith("//") || line.isEmpty()) {
                    return;
                }
                String[] tokens = line.split(";");
                questions.add(new Question(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(), tokens[3].trim(), tokens[4].trim()));
                Collections.shuffle(questions);
            });
        } catch (IOException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error!!!");
            error.setHeaderText("Questions file not found!");
            error.showAndWait();
            Platform.exit();
            System.exit(0);
        }

        if (questions.isEmpty()) {
            Alert empty = new Alert(Alert.AlertType.WARNING);
            empty.setTitle("File is empty!!!");
            empty.setHeaderText("No Questions were found in " + filename);
            empty.showAndWait();
            Platform.exit();
            System.exit(0);
        }
        return questions;
    }
    public void display(Label labell, Label correctLabel) {
        ArrayList<Button> buttonSettings = new ArrayList<>(button);
        for (Button but: buttonSettings) {
            but.getStyleClass().remove("correct");
            but.getStyleClass().remove("wrong");
        }
        labell.setText(this.question);
        correctLabel.setText("Question " + questionAmount);
        int randInt = rand.nextInt(4);
        buttonRandom = buttonSettings.get(randInt);
        buttonSettings.get(randInt).setText(this.correct);
        buttonSettings.remove(randInt);
        Collections.shuffle(this.wrongs);
        for (Button but : buttonSettings) {
            but.setText(this.wrongs.get(buttonSettings.indexOf(but)));
        }
    }

    public void check(Button but, ArrayList<Question> questions, Label scoreLabel) {
        if (this.buttonRandom == but) {
            but.getStyleClass().add("correct");
            score += 10;
            scoreLabel.setText("Score: " + score);
            correctAnswer += 1;
        } else {
            but.getStyleClass().add("wrong");
            this.buttonRandom.getStyleClass().add("correct");
        }

        if (questions.size() == questionAmount) {
            F2.gameEnded(score, correctAnswer);
        }
        questionAmount += 1;
        Index += 1;
    }
    public static void setButtons(Button...buttonsArray) {
        button = new ArrayList<>(Arrays.asList(buttonsArray));
    }

    public static int getIndex() {
        return Index;
    }
}
