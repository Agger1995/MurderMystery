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
import Business.Item;
import Business.Person;
import Business.Room;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

/**
 * FXML Controller class This controller controls all the game events.
 *
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
    private ListView<Interactable> objectsInRoomList;
    @FXML
    private ListView<Interactable> inventoryListView;
    @FXML
    private Label minimapplayer, person0label, person1label, person2label, person3label, person4label, inventoryCurrent;
    @FXML
    private Pane MiniMap1, MiniMap2, MiniMap3;
    @FXML
    private Button goWest, goEast, goSouth, goNorth, helpButton, continueWelcomeMsgBtn;
    @FXML
    private TextArea gameText;
    @FXML
    private TextField points, currentRoom, timeLeft;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * This method sets up all of the references to the Game object. The game reference is passed from the GUIMain.java.
     *
     * @param game Game reference from GUIMain
     * @param logbook Logbook reference, also passed from GUIMain.
     */
    void setRefAndInitialData(Game game, LogBook logbook) {
        this.game = game;
        this.logbook = logbook;
        this.game.setTextAreaRef(gameText);
        this.updateTime();
        this.addActions();
        this.getWelcomeText();
        this.updateObjects();
        this.openLogbook();
        this.handleEndCycleUpdates();
        this.placePlayerAndPersonsOnMap();
        this.placePersonsOnMiniMap();
    }

    /**
     * This method places labels on the minimap. It also checks which scenario is chosen.
     */
    private void placePlayerAndPersonsOnMap() {
        /*
        If-else statement, that switches between the different scenario names.
        Makes sure the correct minimap is chosen, while the others are disabled.
         */
        if (game.getScenarioName().toLowerCase().compareTo("a very bloody x-mas") == 0) {
            MiniMap1.setVisible(true);
            MiniMap2.setVisible(false);
            MiniMap3.setVisible(false);
            MiniMap1.getChildren().add(minimapplayer);
            MiniMap1.getChildren().add(person0label);
            MiniMap1.getChildren().add(person1label);
            MiniMap1.getChildren().add(person2label);
            MiniMap1.getChildren().add(person3label);
            MiniMap1.getChildren().add(person4label);
        } else if (game.getScenarioName().toLowerCase().compareTo("not a phine day") == 0) {
            MiniMap1.setVisible(false);
            MiniMap2.setVisible(true);
            MiniMap3.setVisible(false);
            MiniMap2.getChildren().add(minimapplayer);
            MiniMap2.getChildren().add(person0label);
            MiniMap2.getChildren().add(person1label);
            MiniMap2.getChildren().add(person2label);
            MiniMap2.getChildren().add(person3label);
            MiniMap2.getChildren().add(person4label);
        } else if (game.getScenarioName().toLowerCase().compareTo("the mariano bar shooting") == 0) {
            MiniMap1.setVisible(false);
            MiniMap2.setVisible(false);
            MiniMap3.setVisible(true);
            MiniMap3.getChildren().add(minimapplayer);
            MiniMap3.getChildren().add(person0label);
            MiniMap3.getChildren().add(person1label);
            MiniMap3.getChildren().add(person2label);
            MiniMap3.getChildren().add(person3label);
            MiniMap3.getChildren().add(person4label);
        } else {
            MiniMap1.setVisible(false);
            MiniMap2.setVisible(false);
            MiniMap3.setVisible(false);
        }
    }

    /**
     * Sets the label coordinates. Each label has a person or player represented.
     */
    private void placePersonsOnMiniMap() {
        /*
        Each label represents a person.
         */
        minimapplayer.setLayoutX(game.getCurrentRoom().getCoordinates().getPlayerCoordinateX());
        minimapplayer.setLayoutY(game.getCurrentRoom().getCoordinates().getPlayerCoordinateY());
        for (Room r : game.getRooms()) {
            for (Person p : r.getPersonsInRoom()) {
                switch (p.getID()) {
                    case 0:
                        person0label.setLayoutX(r.getCoordinates().getPerson0CoordinateX());
                        person0label.setLayoutY(r.getCoordinates().getPerson0CoordinateY());
                        break;
                    case 1:
                        person1label.setLayoutX(r.getCoordinates().getPerson1CoordinateX());
                        person1label.setLayoutY(r.getCoordinates().getPerson1CoordinateY());
                        break;
                    case 2:
                        person2label.setLayoutX(r.getCoordinates().getPerson2CoordinateX());
                        person2label.setLayoutY(r.getCoordinates().getPerson2CoordinateY());
                        break;
                    case 3:
                        person3label.setLayoutX(r.getCoordinates().getPerson3CoordinateX());
                        person3label.setLayoutY(r.getCoordinates().getPerson3CoordinateY());
                        break;
                    case 4:
                        person4label.setLayoutX(r.getCoordinates().getPerson4CoordinateX());
                        person4label.setLayoutY(r.getCoordinates().getPerson4CoordinateY());
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /**
     * This opens the Logbook gui in another window. It also makes it uncloseable.
     */
    private void openLogbook() {
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("LogbookFXML.fxml").openStream()); //get layout
            LogbookController logController = (LogbookController) loader.getController(); //get controller
            logController.setRefAndInitialData(this.logbook); //sets reference to logbook
            this.logbookController = logController; //
            Scene scene = new Scene(root); //creates a new scene for the controller.
            logbookStage = new Stage(); //creates a new stage for the scene
            logbookStage.getIcons().add(new Image("logbook.png")); //sets icon
            logbookStage.setResizable(false); //makes sure you can't resize this.
            logbookStage.setScene(scene); //sets scene
            logbookStage.setAlwaysOnTop(true); //makes sure, it's always visible.
            logbookStage.setTitle("Logbook");
            logbookStage.setX(30);
            logbookStage.setY(200);
            logbookStage.show();
            /*
            Makes sure you can't close the logbook while playing the game.
            Comes with a warning if you try.
             */
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

    /**
     * Triggers a command in the direction specified in the method.
     *
     * @param event
     */
    @FXML
    private void onWest(ActionEvent event) {
        this.goDirection("west");
    }

    /**
     * Triggers a command in the direction specified in the method.
     *
     * @param event
     */
    @FXML
    private void onEast(ActionEvent event) {
        this.goDirection("east");
    }

    /**
     * Triggers a command in the direction specified in the method.
     *
     * @param event
     */
    @FXML
    private void onSouth(ActionEvent event) {
        this.goDirection("south");
    }

    /**
     * Triggers a command in the direction specified in the method.
     *
     * @param event
     */
    @FXML
    private void onNorth(ActionEvent event) {
        this.goDirection("north");
    }

    /**
     * Makes the different buttons available and unavailable. The directions where there is a connection, it enables the button of the given direction.
     */
    private void refreshDirectionalButtons() {
        /*
        Disables the buttons first, when enables each, if a connection is in the existence.
         */
        goWest.setDisable(true);
        goEast.setDisable(true);
        goNorth.setDisable(true);
        goSouth.setDisable(true);
        /*
        Reenables if present.
         */
        if (this.game.getCurrentRoom().getExitDir("east") != null) {
            goEast.setDisable(false);
        }
        if (this.game.getCurrentRoom().getExitDir("west") != null) {
            goWest.setDisable(false);
        }
        if (this.game.getCurrentRoom().getExitDir("south") != null) {
            goSouth.setDisable(false);
        }
        if (this.game.getCurrentRoom().getExitDir("north") != null) {
            goNorth.setDisable(false);
        }
    }

    /**
     * Handles the travel to the specified direction.
     *
     * @param dir
     */
    void goDirection(String dir) {
        /*
        Checks if you can go that way. Returns if you can't.
         */
        if (this.game.getCurrentRoom().getExitDir(dir) == null) {
            return;
        }
        /*
        Handles the travelling.
         */
        String roomName = this.game.getCurrentRoom().getExitDir(dir).getShortDescription();
        CommandWord cmdWord = CommandWord.get("Go");
        Command cmd = new Command(cmdWord, roomName);
        this.sendCommand(cmd);
        this.handleEndCycleUpdates();
        this.placePersonsOnMiniMap(); //add persons on the map, every time player go destination
    }

    /**
     * This is triggered, when the help button is pressed. It comes fourth as an alert box of the type: INFORMATION.
     *
     * @param event
     */
    @FXML
    private void onHelp(ActionEvent event) {
        /*
        Createas a new Alert window, to display the information.
         */
        Alert alert = new Alert(AlertType.INFORMATION); //sets type.
        alert.setTitle("Help screen"); //sets title.
        alert.setHeaderText("Here you can read about the game window components");
        alert.setContentText("This is the main game window\n"
                + "You can move around the different rooms using the buttons in the middle of the screen\n"
                + "To the right you can see 3 lists:\n"
                + " - Inventory: Shows the items you have on you\n"
                + " - In room: Shows the items and persons in the room you are in\n"
                + " - Actions: Shows the commands you can perform depending on what you selected in Inventory/In room list\n"
                + "\n"
                + "To the left you see a minimap showing your location (Red dot)\n"
                + "and other persons location (Blue dots)\n"
                + "The text area will show you relevant information throughout the game\n"
                + "There is a time parameter in the top right corner of the screen\n"
                + "Remember to keep an eye on this as you will have limited time to solve the murder\n"
                + "At 8:00 AM the police will arrive and you if you haven't solved the murder by then you will lose.");
        alert.showAndWait();
    }

    /**
     * Appends the message box.
     */
    @FXML
    private void continueWelcomeMsg() {
        /*
        Appends the TextArea of the missing text.
         */
        if (this.welcomeMsgCounter < this.welcomeMsg.size()) {
            this.gameText.appendText(this.welcomeMsg.get(this.welcomeMsgCounter) + "\n");
            this.welcomeMsgCounter++;
        } else {
            this.continueWelcomeMsgBtn.setOpacity(0);
            this.gameText.setPrefHeight(320);
        }
    }

    /**
     * Appends the message box.
     */
    private void getWelcomeText() {
        this.welcomeMsg = this.game.printWelcome();
        this.gameText.appendText(this.welcomeMsg.get(this.welcomeMsgCounter) + "\n");
        this.welcomeMsgCounter++;
    }

    /**
     * Updates the label showing the time.
     */
    private void updateTime() {
        this.timeLeft.setText(game.getTime());
    }

    /**
     * Adds all the actions.
     */
    private void addActions() {
        for (CommandWord CW : CommandWord.values()) {
            this.actionListData.add(CW);
        }
        this.actionListView.setItems(actionListData);
    }

    /**
     * After a command-button has been pressed, this will be called to handle all the updates.
     */
    private void handleEndCycleUpdates() {
        /*
        Calls all the end cycle methods.
         */
        this.updateTime();
        this.logbookController.updateListViews();
        this.objectsInRoomList.getItems().clear();
        this.updateObjects();
        this.refreshDirectionalButtons();
        this.updatePoints();
        this.updateInventorySize();
        this.currentRoom.setText(this.game.getCurrentRoom().getShortDescription());
    }

    /**
     * Updates the lists containing the objects in room and inventory.
     */
    private void updateObjects() {
        /*
        refreshes the lists.
        Starts by emptying the lists.
        End by filling them up.
         */
        objectsInRoomList.getItems().removeAll();
        ArrayList<Interactable> objects = game.getObjectsInCurrentRoom();
        this.objectsInRoomList.getItems().addAll(objects);
        inventoryListView.getItems().clear();
        inventoryListView.getItems().addAll(this.game.getInventory());
    }

    /**
     * This handles which item is selected through the different lists.
     *
     * @param e ListView that is chosen.
     */
    @FXML
    private void objectSelectionListener(Event e) {
        //Makes sure only one list is chosen at a time.
        ListView toDeactivate = (e.getSource().equals(inventoryListView)) ? objectsInRoomList : inventoryListView;
        this.chosenList = (e.getSource().equals(inventoryListView)) ? inventoryListView : objectsInRoomList;
        toDeactivate.getSelectionModel().select(null); // Deactivates the list that is clicked on last

        //Returns if the list is empty, or no item has been pressed.
        if (this.chosenList.getSelectionModel().isEmpty()) {
            return;
        }
        //Handles the actions available.
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
        } else if (item_type.compareTo("Item") == 0) {
            if (e.getSource().equals(objectsInRoomList)) {
                this.actionListData.add(CommandWord.INSPECT);
            } else {
                this.actionListData.add(CommandWord.INSPECT);
                this.actionListData.add(CommandWord.DROP);
            }
            Item temp = (Item) this.chosenList.getSelectionModel().getSelectedItem();
            if (temp.isDrinkable()) {
                if (this.objectsInRoomList.getSelectionModel().getSelectedItem() == null) {
                    this.actionListData.add(CommandWord.DRINK);
                }
            }
            if (temp.isActive()) {
                if (this.objectsInRoomList.getSelectionModel().getSelectedItem() != null) {
                    this.actionListData.add(CommandWord.TAKE);
                }
            }
        } else if (item_type.compareTo("Person") == 0) {
            this.actionListData.add(CommandWord.ASK);
            this.actionListData.add(CommandWord.ACCUSE);
        } else {
            this.actionListView.getItems().clear();
        }
    }

    /**
     * Handles the action pressed in the list view menu.
     */
    @FXML
    private void onActionClicked() {
        //Makes sure an items has been chosen.
        if (this.chosenList == null || this.chosenList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        if (this.actionListView.getSelectionModel().isEmpty() || this.chosenList.getItems().isEmpty()) {
            return;
        }
        //Gets the index, so the chosen one remains after the lists are refreshed.
        int index = this.chosenList.getSelectionModel().getSelectedIndex();
        String cmdString = actionListView.getSelectionModel().getSelectedItem().toString();
        CommandWord cmdWord = CommandWord.get(cmdString);
        String secondWord = this.chosenList.getSelectionModel().getSelectedItem().toString();
        if (cmdWord == CommandWord.ASK) {
            this.handleAskCmd();
        }
        Command cmd = new Command(cmdWord, secondWord);
        this.sendCommand(cmd);
        this.objectsInRoomList.getItems().clear();
        this.chosenList.getItems().clear();
        updateObjects();
        this.chosenList.getSelectionModel().select(index);
        this.handleEndCycleUpdates();
    }

    /**
     * This creates a window that handles the dialogue between you and the npc.
     */
    private void handleAskCmd() {
        FXMLLoader loader = new FXMLLoader();
        Parent root;
        try {
            root = loader.load(getClass().getResource("AskDialogFXML.fxml").openStream()); // Throws I/O Exception
            AskDialogController askDialogController = (AskDialogController) loader.getController();
            askDialogController.setGameRef(this.game, this.logbookController);
            askDialogController.setPersonInDialog(this.chosenList.getSelectionModel().getSelectedItem());
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

    /**
     * Sends the chosen command to the business layer.
     *
     * @param cmd Command that is chosen.
     */
    private void sendCommand(Command cmd) {
        if (this.game.processCommand(cmd)) {
            this.handleGameOver();
        }
    }

    /**
     * This handles the Game Over routine.
     */
    private void handleGameOver() {
        try {
            this.game.addPoints();
            Alert preAlert = new Alert(AlertType.INFORMATION);
            preAlert.setTitle("Game Over");
            switch (this.game.getGameOverCause()) {
                case "drink":
                    preAlert.setHeaderText("You died because you drank too much!");
                    preAlert.setContentText("As a detective you cannot drink too much!\nYou need to stay focused and sober if you\nare to solve the murder!");
                    break;
                case "time":
                    preAlert.setHeaderText("You ran out of time! The police has arrived!");
                    preAlert.setContentText(this.game.getEndGameMsg("time"));
                    break;
                case "correctAccuse":
                    preAlert.setHeaderText("You solved the case! Well done!");
                    preAlert.setContentText(this.game.getEndGameMsg("correctAccuse"));
                    break;
                case "wrongAccuse":
                    preAlert.setHeaderText("You accused the wrong person!");
                    preAlert.setContentText(this.game.getEndGameMsg("wrongAccuse"));
                    break;

                default:
                    break;
            }
            preAlert.showAndWait();
            if (this.game.canGetOnHighscore()) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Game Over");
                dialog.setHeaderText("You have earned enough points to get on the highscore!\nYou earned: " + this.game.getPoints() + " points!");
                dialog.setContentText("Please enter your name: ");
                dialog.showAndWait();
                String playerName;
                if (dialog.getResult() == null) {
                    playerName = "Player";
                } else {
                    playerName = dialog.getResult();
                }

                if (!this.game.addToHighscore(playerName)) {
                    Alert error = new Alert(AlertType.ERROR);
                    error.setTitle("Highscore error!");
                    error.setHeaderText("An error occured when trying to write to the highscore file!");
                    error.setContentText("");
                    error.showAndWait();
                }
            }

            FXMLLoader loader = new FXMLLoader();
            Parent root;
            root = loader.load(getClass().getResource("EndHighscoreViewFXML.fxml").openStream()); // Throws I/O Exception
            EndHighscoreViewController controller = (EndHighscoreViewController) loader.getController();
            controller.setHighscoreData(this.game.getHighscoreData(), this.game.getScenarioName());
            Scene scene = new Scene(root);
            Stage highscoreView;
            highscoreView = new Stage();
            highscoreView.setTitle("Highscore view");
            highscoreView.setResizable(false);
            highscoreView.getIcons().add(new Image("logbook.png"));
            highscoreView.setScene(scene);
            highscoreView.show();

            Stage gameStage = (Stage) this.gameText.getScene().getWindow();
            gameStage.close();
            this.logbookStage.close();

        } catch (FileNotFoundException err) {
            System.out.println("Error in highscore");
        } catch (IOException e) {
            System.out.println("Error opening highscore view!");
        }
    }

    /**
     * This updates the label showing the current points gotten.
     */
    private void updatePoints() {
        this.points.setText("" + this.game.getPoints());
    }

    /**
     * This updates the label showing the current inventory capacity.
     */
    private void updateInventorySize() {
        this.inventoryCurrent.setText("" + this.game.getInventorySize() + "/" + this.game.getInventoryMaxSize());
    }
}
