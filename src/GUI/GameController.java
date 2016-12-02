/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Command;
import Business.Game;
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
import Business.Person;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class GameController implements Initializable {

    private Game game;
    private LogBook logbook;
    private Stage logbookStage;
    private Stage askDialogStage;
    private LogbookController logbookController;
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

    void setRefs(Game game, LogBook logbook) {
        this.game = game;
        this.logbook = logbook;
        this.game.setTextAreaRef(GameText);
        this.updateTime();
        this.addActions();
        this.getWelcomeText();
        this.updateObjects();
        this.openLogbook();
    }
    
    private void openLogbook(){
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("LogbookFXML.fxml").openStream());
            LogbookController logController = (LogbookController) loader.getController();
            logController.setRef(this.logbook);
            this.logbookController = logController;
            Scene scene = new Scene(root);
            logbookStage = new Stage();
            logbookStage.getIcons().add(new Image("logbook.png"));
            logbookStage.setResizable(false);
            logbookStage.setScene(scene);
            logbookStage.setAlwaysOnTop(true);
            logbookStage.setTitle("Logbook");
            logbookStage.setX(30);
            logbookStage.setY(200);
            logbookStage.show();
            logbookStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    
                    Alert alert = new Alert(AlertType.INFORMATION);
                    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                    alertStage.getIcons().add(new Image("logbook.png"));
                    alert.setTitle("");
                    alert.setHeaderText("");
                    alert.setContentText("You cannot close the logbook window.\nYou need it throughout the game.");
                    alert.show();
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void OnPreformAction(ActionEvent event) {
    }

    @FXML
    private void onWest(ActionEvent event) {
        this.goDirection("west");
    }

    @FXML
    private void onEast(ActionEvent event) {
        this.goDirection("east");
    }

    @FXML
    private void onSouth(ActionEvent event) {
        this.goDirection("south");
    }

    @FXML
    private void onNorth(ActionEvent event) {
        this.goDirection("north");
    }
    
    private void goDirection(String dir) {
        if(this.game.getCurrentRoom().getExitDir(dir) == null) {
            return;
        }
        String roomName = this.game.getCurrentRoom().getExitDir(dir).getShortDescription();
        CommandWord cmdWord = CommandWord.get("Go");
        Command cmd = new Command(cmdWord, roomName);
        game.processCommand(cmd);
        this.handleEndCycleUpdates();
    }

//    @FXML
//    private void onLogbook(ActionEvent event) throws IOException {
//        if (logbookStage == null || logbookStage.getScene() == null) {
//            FXMLLoader loader = new FXMLLoader();
//            Parent root = loader.load(getClass().getResource("LogbookFXML.fxml").openStream());
//            LogbookController logController = (LogbookController) loader.getController();
//            logController.setRef(this.logbook);
//            this.logbookController = logController;
//            Scene scene = new Scene(root);
//            logbookStage = new Stage();
//            logbookStage.getIcons().add(new Image("logbook.png"));
//            logbookStage.setResizable(false);
//            logbookStage.setScene(scene);
//            logbookStage.show();
//            logbookStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent we) {
//                    logbookStage.setScene(null);
//                }
//            });
//        } else {
//            logbookStage.close();
//            logbookStage = null;
//        }
//    }

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
            this.continueWelcomeMsgBtn.setOpacity(0);
            this.GameText.setPrefHeight(320);
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
        this.logbookController.updateListViews();
        this.objectsInRoomList.getItems().clear();
        this.updateObjects();
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
        
        if (item_type.compareTo("SpecialItem") == 0) {
            if (e.getSource().equals(objectsInRoomList)) {
                this.actionListData.add(CommandWord.INSPECT);
            } else {
                this.actionListData.add(CommandWord.INSPECT);
                this.actionListData.add(CommandWord.DROP);
            }
            this.actionListView.setItems(actionListData);
        } else if (item_type.compareTo("PersonWithRiddle") == 0) {
            this.actionListData.add(CommandWord.ASK);
            this.actionListView.setItems(actionListData);
        } else if(item_type.compareTo("Item") == 0){
            this.actionListData.add(CommandWord.TAKE);
            this.actionListData.add(CommandWord.INSPECT);
        } else if(item_type.compareTo("Person") == 0){
            this.actionListData.add(CommandWord.ASK);
            this.actionListData.add(CommandWord.ACCUSE);
        } else {
            this.actionListView.getItems().clear();
        }
    }

    @FXML
    private void onActionClicked() {
        if(this.objectsInRoomList.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if (this.actionListView.getSelectionModel().isEmpty() || this.chosenList.getItems().isEmpty()) {
            return;
        }
        int index = this.chosenList.getSelectionModel().getSelectedIndex();
        String cmdString = actionListView.getSelectionModel().getSelectedItem().toString();
        CommandWord cmdWord = CommandWord.get(cmdString);
        String secondWord = this.chosenList.getSelectionModel().getSelectedItem().toString();
        if(cmdWord == CommandWord.ASK){
            this.handleAskCmd();
        }
        Command cmd = new Command(cmdWord, secondWord);
        game.processCommand(cmd);
        this.objectsInRoomList.getItems().clear();
        this.chosenList.getItems().clear();
        updateObjects();
        this.chosenList.getSelectionModel().select(index);
        this.handleEndCycleUpdates();
    }

    private void handleAskCmd() {
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("AskDialogFXML.fxml").openStream());
            AskDialogController askDialogController = (AskDialogController) loader.getController();
            askDialogController.setGameRef(this.game, this.logbookController);
            askDialogController.setPersonInDialog((Person) this.chosenList.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(root);
            askDialogStage = new Stage();
            askDialogStage.setResizable(false);
            askDialogStage.setScene(scene);
            askDialogStage.setAlwaysOnTop(true);
            askDialogStage.initStyle(StageStyle.UNDECORATED);
            askDialogStage.show();
        } catch (IOException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
