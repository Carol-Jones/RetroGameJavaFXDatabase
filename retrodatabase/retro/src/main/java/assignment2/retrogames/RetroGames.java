package assignment2.retrogames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class RetroGames extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("retrogames-view.fxml"));

        Scene scene = new Scene(root);

        stage.getIcons().add(new Image("file:src/main/resources/assignment2/retrogames/images/DragonQuestSlimeIcon.png"));
        stage.setTitle("狩勝山's Retro Game Library");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}