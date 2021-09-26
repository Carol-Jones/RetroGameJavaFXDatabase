package assignment2.retrogames;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AlertController {
    @FXML
    public Label error;

    public void setAlertText(String text) {
        // set text from another class
        error.setText(text);
    }

}
