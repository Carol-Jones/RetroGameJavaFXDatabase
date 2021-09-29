package assignment2.retrogames;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RetroGamesController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    @FXML
    private ImageView image;
    @FXML
    private BorderPane GamePortal;
    @FXML
    private Label title;
    @FXML
    private Label about;
    @FXML
    private Button play;
    @FXML
    private Button pause;
    @FXML
    private ComboBox type;
    @FXML
    private TextField name;
    Media media;
    MediaPlayer player;
    OrderedDictionary database = null;
    RetroGameRecord retroGame = null;
    int gameType = 1;

    @FXML
    public void exit() {
        Stage stage = (Stage) mainMenu.getScene().getWindow();
        stage.close();
    }

    public void find() {
        DataKey key = new DataKey(this.name.getText(), gameType);
        try {
            retroGame = database.find(key);
            showGame();
        } catch (DictionaryException ex) {
            displayAlert(ex.getMessage());
        }
    }

    public void delete() {
        RetroGameRecord previousGame = null;
        try {
            previousGame = database.predecessor(retroGame.getDataKey());
        } catch (DictionaryException ex) {

        }
        RetroGameRecord nextGame = null;
        try {
            nextGame = database.successor(retroGame.getDataKey());
        } catch (DictionaryException ex) {

        }
        DataKey key = retroGame.getDataKey();
        try {
            database.remove(key);
        } catch (DictionaryException ex) {
            System.out.println("Error in delete "+ ex);
        }
        if (database.isEmpty()) {
            this.GamePortal.setVisible(false);
            displayAlert("No more retro games in the database to show");
        } else {
            if (previousGame != null) {
                retroGame = previousGame;
                showGame();
            } else if (nextGame != null) {
                retroGame = nextGame;
                showGame();
            }
        }
    }

    private void showGame() {
        play.setDisable(false);
        pause.setDisable(true);
        if (player != null) {
            player.stop();
        }
        String img = retroGame.getImage();
        Image gameImage = new Image("file:src/main/resources/assignment2/retrogames/images/" + img);
        image.setImage(gameImage);
        title.setText(retroGame.getDataKey().getGameName());
        about.setText(retroGame.getAbout());
    }

    private void displayAlert(String msg) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/main/resources/assignment/retrogames/images/UNTIcon.png"));
            stage.setTitle("Dictionary Exception");
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex1) {

        }
    }

    public void getType() {
        switch (this.type.getValue().toString()) {
            case "Horror":
                this.gameType = 1;
                break;
            case "RPG":
                this.gameType = 2;
                break;
            case "Platformer":
                this.gameType = 3;
                break;
            default:
                break;
        }
    }

    public void first() throws DictionaryException {
        // Write this method
        retroGame = database.largest();
        showGame();
    }

    public void last() throws DictionaryException {
        // Write this method
        retroGame = database.smallest();
        showGame();
    }

    public void next() throws DictionaryException {
        // Write this method;
        if(retroGame == null) {
            retroGame = database.largest();
            showGame();
            return;
        }
        retroGame = database.successor(retroGame.getDataKey());
        showGame();
    }

    public void previous() throws DictionaryException {
        if(retroGame == null)
            retroGame = database.smallest();
        retroGame = database.predecessor(retroGame.getDataKey());
        showGame();
    }

    public void play() {
        String filename = "src/main/resources/assignment2/retrogames/sounds/" + retroGame.getSound();
        media = new Media(new File(filename).toURI().toString());
        player = new MediaPlayer(media);
        play.setDisable(true);
        pause.setDisable(false);
        player.play();
    }

    public void pause() {
        play.setDisable(false);
        pause.setDisable(true);
        if (player != null) {
            player.stop();
        }
    }

    public void loadDictionary() throws DictionaryException {
        Scanner input;
        int line = 0;
        try {
            String gameName = "";
            String description;
            int type = 0;
            input = new Scanner(new File("RetroGamesDatabase.txt"));
            while (input.hasNext()) // read until  end of file
            {
                String data = input.nextLine();
                switch (line % 3) {
                    case 0:
                        type = Integer.parseInt(data);
                        break;
                    case 1:
                        gameName = data;
                        break;
                    default:
                        description = data;
                        database.insert(new RetroGameRecord(new DataKey(gameName, type), description, gameName + ".mp3", gameName + ".jpg"));
                        break;
                }
                line++;
            }
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: RetroGamesDatabase.txt");
            System.out.println(e.getMessage());
        } catch (DictionaryException ex) {
            Logger.getLogger(RetroGamesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.GamePortal.setVisible(true);
        this.first();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database = new OrderedDictionary();
        type.setItems(FXCollections.observableArrayList(
                "Horror", "RPG", "Platformer"
        ));
        type.setValue("Horror");
    }
}