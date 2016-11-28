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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author amaliehoff
 */
public class LogbookController implements Initializable {

    @FXML
    private Label WeaponLabel;
    @FXML
    private TextArea LogbookTextArea;
    @FXML
    private Button logbookHelpBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void onHelpBtn(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help window");
        alert.setHeaderText("Help window for logbook screen.");
        alert.setContentText("Here is explanation for the logbook screen.");
        alert.showAndWait();
    }
    
}
