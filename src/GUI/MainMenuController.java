/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Business.Game;
import Business.LogBook;
import Business.Riddle;
import Persistence.Highscore;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Main Menu Controller. In this controller, you can choose between the different scenarios. You can also see the highscores for each.
 *
 * @author chris
 */
public class MainMenuController implements Initializable {

    private Game game;
    private Riddle riddle = new Riddle();
    private LogBook logbook;
    private Highscore highscore;
    private Stage currentStage;
    @FXML
    private Button buttonChoseScenario, buttonPlay;
    @FXML
    private Label chosenScenarioLabel, highscoreLabel;
    @FXML
    private TextArea highscoreView;

    /**
     * Sets the current stage.
     *
     * @param stage
     */
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Creates a window, that allows you to choose between the scenarios.
     */
    @FXML
    private void handleScenarioPicker() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Scenario Picker");
        alert.setHeaderText("You have following scenarios available to play.");

        File file = new File("scenarios/");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        int i = 0;

        String appendTo = "";
        ArrayList<ButtonType> buttons = new ArrayList<>();
        for (String workingString : directories) {
            appendTo += (i + 1) + " : " + workingString + "\n";

            buttons.add(new ButtonType(workingString));

            i++;
        }
        buttons.add(new ButtonType("Cancel"));

        alert.getButtonTypes().setAll(buttons);
        alert.setContentText(appendTo);

        Optional<ButtonType> chosen = alert.showAndWait();

        if (!chosen.get().getText().equals("Cancel")) {
            String scenarioPath = "scenarios/" + chosen.get().getText();

            this.riddle.setPath(scenarioPath); //Sets the path for the riddles.

            this.logbook = new LogBook();
            this.game = new Game(this.logbook, scenarioPath, this.riddle);

            this.highscore = game.getHighscoreRef();
            this.chosenScenarioLabel.setText(chosen.get().getText());
            this.highscoreLabel.setOpacity(1);
            this.buttonPlay.setDisable(false);
            this.highscoreView.setOpacity(1);
            this.highscoreView.clear();
            this.handleHighscoreView();
        }
    }

    /**
     * Gets the highscore data for the chosen scenario.
     */
    private void handleHighscoreView() {
        this.highscoreView.appendText(this.game.getHighscoreData());
    }

    /**
     * Handles when the play button has been pressed. Sets the scene to the Game scene - You are then able to play.
     */
    @FXML
    private void handlePlayButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("GameFXML.fxml").openStream());
            GameController gameController = (GameController) loader.getController();
            gameController.setRefAndInitialData(this.game, this.logbook);
            Scene scene = new Scene(root);
            scene.setOnKeyReleased(new ShortcutEventHandler(gameController));
            currentStage.getIcons().add(new Image("gameIcon.jpg"));
            currentStage.setTitle("Murder Mystery");
            currentStage.setResizable(false);
            currentStage.setScene(scene);
            currentStage.show();

        } catch (IOException IOErr) {
            System.out.println(IOErr + "Fatal error loading game window!");
        }
    }
}
