/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Highscore window handling.
 * This controller handles all the events regarding the highscore window.
 *
 * @author chris
 */
public class EndHighscoreViewController implements Initializable {
    @FXML
    private TextArea highscoreView;
    @FXML
    private Label scenarioNameLabel;
    @FXML
    private Button closeGameBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    /**
     * This method writes the highscore data to the TextArea.
     * @param data String of the highscore
     * @param scenarioName Name of the Scenario
     */
    void setHighscoreData(String data, String scenarioName){
        this.highscoreView.appendText(data);
        this.scenarioNameLabel.setText("Highscore view for: " + scenarioName);
    }
    
    /**
     * Makes sure the game is closed, when the 'x' button is pressed.
     */
    @FXML
    private void handleCloseGameBtn(){
        Platform.exit();
    }
    
}
