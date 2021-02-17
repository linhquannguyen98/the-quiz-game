package sample;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.*;

public class F2 {

    @FXML
    private Label questionLabel;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Label questionNum;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label textF2;

    static ArrayList<Question> questions;
    static int percent;
    static int pauseValue = 1500;

    @FXML
    public void initialize() {

        List<String> questionList = Arrays.asList( "set1.txt","set2.txt");
        ArrayList<String> level = new ArrayList<>();
        level.addAll(questionList);
        int questionSetRand = new java.util.Random().nextInt(level.size());
        String value = level.get(questionSetRand);

        System.out.println("Question set: "+ value);
        questions = Question.load(value);

        Question.setButtons(button1, button2, button3, button4);
        questions.get(Question.getIndex()).display(questionLabel, questionNum);
        button1.setOnAction(this::handleButtonAction);
        button2.setOnAction(this::handleButtonAction);
        button3.setOnAction(this::handleButtonAction);
        button4.setOnAction(this::handleButtonAction);
    }

    public void showPlayer(String text){
        textF2.setText(text);
    }

    private void handleButtonAction(ActionEvent event) {

        button1.setDisable(true);
        button2.setDisable(true);
        button3.setDisable(true);
        button4.setDisable(true);
        questions.get(Question.getIndex()).check((Button) event.getTarget(), questions, scoreLabel);

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater( () -> {
                    questions.get(Question.getIndex()).display(questionLabel, questionNum);
                    button1.setDisable(false);
                    button2.setDisable(false);
                    button3.setDisable(false);
                    button4.setDisable(false);
                });
            }}, pauseValue);
    }

    public static void gameEnded(int score, int questionsCorrect) {
        if (questionsCorrect == 0) {
            percent = 0;
        }else {
            percent = (int) ((double)questionsCorrect/(double)questions.size() * 100);
            Alert finish = new Alert(Alert.AlertType.INFORMATION);
            finish.setTitle("You Win!");
            finish.setHeaderText("Score: " + score);
            finish.setContentText("Questions Correct: " + questionsCorrect + " out of " + questions.size() + " (" + percent + "%)");
            finish.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }
}