/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Game;
import Business.Item;
import Business.LogBook;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import Business.ObjectsInRoom;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class GameController implements Initializable {
    private Game game;
    private LogBook logbook;
    private Stage logbookStage;
    ObservableList<CommandWord> actionListData = FXCollections.observableArrayList();
    ArrayList<String> welcomeMsg;
    int welcomeMsgCounter = 0;
    
    @FXML
    private ListView<CommandWord> ActionListView;
    @FXML
    private TextField TimeLeft;
    @FXML
    private Label TimeLabel, ActionLabel, InRoomLabel, InRoomLabel1;
    @FXML
    private TextArea GameText;
    @FXML
    private Pane MiniMap;
    @FXML
    //Muligvis tilføj dette som et interface der implementeres af både Item og Person. På denne måde kan et listview indeholde begge datatyper.
    //Definer derefter listview til at indeholde det pågældende interface som datatype.
    private ListView<ObjectsInRoom> InRoomListView;
    @FXML
    private Button PreformActionButtom, GoWest, GoEast, GoSouth, GoNorth, LogBookButton, HelpButton;
    @FXML
    private ListView<Item> InventoryListView;
    @FXML
    private Button continueWelcomeMsgBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    void setGame(Game game) {
        this.game = game;
        this.game.setTextAreaRef(GameText);
        this.updateTime();
        this.addActions();
        this.getWelcomeText();
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
    private void onLogbook(ActionEvent event) throws IOException {
        if (logbookStage == null || logbookStage.getScene() == null){
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("LogbookFXML.fxml").openStream());
        LogbookController controller = (LogbookController) loader.getController();
        Scene scene = new Scene(root);
        logbookStage = new Stage();
        logbookStage.getIcons().add(new Image("logbook.png"));
        logbookStage.setResizable(false);
        logbookStage.setScene(scene);
        logbookStage.show();
        logbookStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent we) {
              logbookStage.setScene(null);
          }
      }); 
        } else {
            logbookStage.close();
            logbookStage = null;
        }
    }

    @FXML
    private void OnHelp(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Help screen");
        alert.setContentText("There is no info available atm");
        alert.setHeaderText("");
        alert.showAndWait();
        this.game.printHelp();
    }
    
    @FXML
    private void continueWelcomeMsg(){
        if(this.welcomeMsgCounter < this.welcomeMsg.size()){
            this.GameText.appendText(this.welcomeMsg.get(this.welcomeMsgCounter) + "\n");
            this.welcomeMsgCounter++;
        } else {
            this.continueWelcomeMsgBtn.setOpacity(0);
        }
    }
    
    private void getWelcomeText(){
        this.welcomeMsg = this.game.printWelcome();
        this.GameText.appendText(this.welcomeMsg.get(this.welcomeMsgCounter));
        this.welcomeMsgCounter++;
    }
    
    private void updateTime(){
        this.TimeLeft.setText(game.getTime());
    }

    private void addActions() {
        for(CommandWord CW : CommandWord.values()){
            this.actionListData.add(CW);
        }
        this.ActionListView.setItems(actionListData);
    }

    @FXML
    private void onGoRoom(KeyEvent event) {
    }
}
