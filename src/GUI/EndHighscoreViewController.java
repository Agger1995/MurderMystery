/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Persistence.Highscore;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
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
    
    void setHighscoreData(String data, String scenarioName){
        this.highscoreView.appendText(data);
        this.scenarioNameLabel.setText("Highscore view for: " + scenarioName);
    }
    
    @FXML
    private void handleCloseGameBtn(){
        Platform.exit();
    }
    
}
