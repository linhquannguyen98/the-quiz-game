package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField textField;

    String level;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    void loadSecond(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("f2.fxml"));
        rootPane.getChildren().setAll(pane);
    }

    String getLevel(){
        return level = textField.getText();
    }
    @FXML
    void sendText(ActionEvent event) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("f2.fxml"));
            Parent game = loader1.load();
            game.getStylesheets().add("sample/lightTheme.css");
            F2 out = loader1.getController();

            out.showPlayer(getLevel());
            Stage stage = new Stage();
            stage.setScene(new Scene(game));
            stage.show();
        }catch (IOException e){
           e.printStackTrace();
        }
    }


}
