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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLController implements Initializable {
    private Game game;
    @FXML
    private Button ButtonStart;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent event) throws FileNotFoundException {
        if(event.getSource() == ButtonStart){
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
                try {
                    game.play();
                } catch (FileNotFoundException fnferr) {
                    System.out.println("Error in highscore system.");
                }
            }
        }
    }
    
}
