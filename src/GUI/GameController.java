/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Command;
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
import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Business.Interactable;
import Business.Inventory;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class GameController implements Initializable {

    private Game game;
    private LogBook logbook;
    private Stage logbookStage;
    private ListView chosenList;
    ObservableList<CommandWord> actionListData = FXCollections.observableArrayList();
    ArrayList<String> welcomeMsg;
    int welcomeMsgCounter = 0;

    @FXML
    private ListView<CommandWord> actionListView;
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
    private ListView<Interactable> objectsInRoomList;
    @FXML
    private Button PreformActionButtom, GoWest, GoEast, GoSouth, GoNorth, LogBookButton, HelpButton;
    @FXML
    private ListView<Interactable> inventoryListView;
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
        this.updateObjects();
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
        if (logbookStage == null || logbookStage.getScene() == null) {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("LogbookFXML.fxml").openStream());
            LogbookController controller = (LogbookController) loader.getController();
            Scene scene = new Scene(root);
            logbookStage = new Stage();
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
    private void continueWelcomeMsg() {
        if (this.welcomeMsgCounter < this.welcomeMsg.size()) {
            this.GameText.appendText(this.welcomeMsg.get(this.welcomeMsgCounter) + "\n");
            this.welcomeMsgCounter++;
        } else {
            this.continueWelcomeMsgBtn.setDisable(true);
        }
    }

    private void getWelcomeText() {
        this.welcomeMsg = this.game.printWelcome();
        this.GameText.appendText(this.welcomeMsg.get(this.welcomeMsgCounter));
        this.welcomeMsgCounter++;
    }

    private void updateTime() {
        this.TimeLeft.setText(game.getTime());
    }

    private void addActions() {
        for (CommandWord CW : CommandWord.values()) {
            this.actionListData.add(CW);
        }
        this.actionListView.setItems(actionListData);
    }
    
    private void handleEndCycleUpdates(){
        this.updateTime();
    }

    //method that add all objects in the current room @Laura
    private void updateObjects() {
        objectsInRoomList.getItems().removeAll();
        ArrayList<Interactable> objects = game.getObjectsInCurrentRoom();
        /*for(Interactable tmp: objects)
        {
            this.InRoomListView.getItems().add(tmp.getName());
        }*/
        this.objectsInRoomList.getItems().addAll(objects);
        Inventory inventory = game.getInventory();
        inventoryListView.getItems().clear();
        inventoryListView.getItems().addAll(inventory.getInventory());
    }

    @FXML
    private void objectSelectionListener(Event e) {
        ListView toDeactivate = (e.getSource().equals(inventoryListView)) ? objectsInRoomList : inventoryListView;
        this.chosenList = (e.getSource().equals(inventoryListView)) ? inventoryListView : objectsInRoomList;
        toDeactivate.getSelectionModel().select(null); // Deactivates the list that is clicked on last

        if (this.chosenList.getSelectionModel().isEmpty()) {
            return;
        }
        Object item1 = this.chosenList.getSelectionModel().getSelectedItem();
        String item_type = item1.getClass().getName().split("Business.")[1]; //Gets the class name and later uses it
        this.actionListView.getItems().clear();
        if (item_type.compareTo("Item") == 0) {
            if (e.getSource().equals(objectsInRoomList)) {
                this.actionListData.add(CommandWord.INSPECT);
                this.actionListData.add(CommandWord.TAKE);
            } else {
                this.actionListData.add(CommandWord.INSPECT);
                this.actionListData.add(CommandWord.DROP);
            }
            this.actionListView.setItems(actionListData);
        } else if (item_type.compareTo("Person") == 0) {
            this.actionListData.add(CommandWord.ASK);
            this.actionListData.add(CommandWord.ACCUSE);
            this.actionListView.setItems(actionListData);
        } else {
            this.actionListView.getItems().clear();
        }
    }

    @FXML
    private void onActionClicked() {
        if (this.actionListView.getSelectionModel().isEmpty() || this.chosenList.getItems().isEmpty()) {
            return;
        }
        int index = this.chosenList.getSelectionModel().getSelectedIndex();
        String cmdString = actionListView.getSelectionModel().getSelectedItem().toString();
        CommandWord cmdWord = CommandWord.get(cmdString);
        String secondWord = this.chosenList.getSelectionModel().getSelectedItem().toString();
        Command cmd = new Command(cmdWord, secondWord);
        game.processCommand(cmd);
        this.objectsInRoomList.getItems().clear();
        this.chosenList.getItems().clear();
        updateObjects();
        this.chosenList.getSelectionModel().select(index);
        this.handleEndCycleUpdates();
    }
}
