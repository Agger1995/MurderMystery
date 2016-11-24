/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class GameController implements Initializable {
    private Game game;
    @FXML
    private ListView<?> ActionListView;
    @FXML
    private TextField TimeLeft;
    @FXML
    private Label TimeLabel;
    @FXML
    private ScrollPane GameText;
    @FXML
    private Pane MiniMap;
    @FXML
    private ListView<?> InRoomListView;
    @FXML
    private Label ActionLabel;
    @FXML
    private Label InRoomLabel;
    @FXML
    private Button PreformActionButtom;
    @FXML
    private Button GoWest;
    @FXML
    private Button GoEast;
    @FXML
    private Button GoSouth;
    @FXML
    private Button GoNorth;
    @FXML
    private Button LogBookButton;
    @FXML
    private Button HelpButton;
    @FXML
    private ListView<?> InventoryListView;
    @FXML
    private Label InRoomLabel1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    void setGame(Game game) {
        this.game = game;
        this.updateTime();
    }

    @FXML
    private void OnPreformAction(ActionEvent event) {
    }

    @FXML
    private void onWest(ActionEvent event) {
    }

    @FXML
    private void onEast(ActionEvent event) {
    }

    @FXML
    private void onSouth(ActionEvent event) {
    }

    @FXML
    private void onNorth(ActionEvent event) {
    }

    @FXML
    private void onLogbook(ActionEvent event) {
    }

    @FXML
    private void OnHelp(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help screen");
        alert.setContentText("There is no info available atm");
        alert.setHeaderText("");
        alert.showAndWait();
    }
    
    private void updateTime(){
        this.TimeLeft.setText(game.getTime());
    }
}
