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
import Business.*;
import Persistence.Highscore;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class MainMenuController implements Initializable {
    private Game game;
    private Highscore highscore;
    private Stage currentStage;
    @FXML
    private Button ButtonChoseScenario;
    @FXML
    private Label ChosenScenarioLabel;
    @FXML
    private Label HighscoreLabel;
    @FXML
    private TextArea HighscoreView;
    @FXML
    private Button ButtonPlay;
    

    public void setCurrentStage(Stage stage){
        this.currentStage = stage;
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
    @FXML
    private void handleScenarioPicker() throws FileNotFoundException{
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
        System.out.println("Please choose one of the following scenarios to play.");
        System.out.println("You choose by typing the ID of the scenario you wish to play.");
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

        if(!chosen.get().getText().equals("Cancel")){
            String scenarioPath = "scenarios/" + chosen.get().getText();

            Riddle.setPath(scenarioPath); //Sets the path for the riddles.

            LogBook log = new LogBook();
            game = new Game(log, scenarioPath);

            this.highscore = game.getHighscoreRef();
            this.ChosenScenarioLabel.setText(chosen.get().getText());
            this.HighscoreLabel.setOpacity(1);
            this.ButtonPlay.setDisable(false);
            this.HighscoreView.setOpacity(1);
            this.HighscoreView.clear();
            this.handleHighscoreView();
        }
    }
    
    private void handleHighscoreView() throws FileNotFoundException{
        this.highscore.readHighscoreTable();
        int i = 0;
        for(String workString : this.highscore.getStringArray()){
            if(workString != null){
                this.HighscoreView.appendText("" + workString + " : " + this.highscore.getIntArray()[i] + "\n");
                i++;
            }
        }
    }

    @FXML
    private void handlePlayButton() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("GameFXML.fxml").openStream());
        GameController controller = (GameController) loader.getController();
        controller.setGame(game);
        Scene scene = new Scene(root);
        currentStage.getIcons().add(new Image("Sherlock-Holmes.jpg"));
        currentStage.setResizable(false);
        currentStage.setScene(scene);
        currentStage.show();
    }
}
