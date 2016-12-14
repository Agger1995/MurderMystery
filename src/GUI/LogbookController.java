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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import Business.Item;
import Business.Person;
import Business.LogBook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

/**
 * FXML Controller class
 *
 * @author amaliehoff
 */
public class LogbookController implements Initializable {
    private LogBook logbook;
    ObservableList<Item> weaponListViewData = FXCollections.observableArrayList();
    ObservableList<Item> itemListViewData = FXCollections.observableArrayList();
    ObservableList<Person> personListViewData = FXCollections.observableArrayList();
    @FXML
    private Button logbookHelpBtn;
    @FXML
    private ListView<Item> weaponListView;
    @FXML
    private ListView<Item> itemListView;
    @FXML
    private ListView<Person> personListView;
    @FXML
    private TextArea logbookTextArea;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setRefAndInitialData(LogBook logbook){
        this.logbook = logbook;
        this.initialViewOfLists();
    }
    
    private void initialViewOfLists(){
        this.itemListView.getItems().clear();
        this.personListView.getItems().clear();
        this.weaponListView.getItems().clear();
        this.itemListViewData.clear();
        this.personListViewData.clear();
        this.weaponListViewData.clear();
        for(Item tempItem : this.logbook.getMurderWeapons()){
            if(tempItem != null){
                this.weaponListViewData.add(tempItem);
            }
        }
        for(Person tempPerson : this.logbook.getPersons()){
            if(tempPerson != null){
                this.personListViewData.add(tempPerson);
            }
        }
        
        for(Item tempItem : this.logbook.getItems()){
            if(tempItem != null){
                this.itemListViewData.add(tempItem);
            }
        }
        this.itemListView.getItems().addAll(itemListViewData);
        this.personListView.getItems().addAll(personListViewData);
        this.weaponListView.getItems().addAll(weaponListViewData);
    }
    
    public void updateListViews(){
        this.initialViewOfLists();
    }
    
    @FXML
    private void listViewListener(Event e){
        this.logbookTextArea.clear();
        ListView source = (ListView)e.getSource();
        if(source.getSelectionModel().getSelectedItem() == null){
            return;
        }
        if(e.getSource() == itemListView){
            this.logbookTextArea.appendText(this.itemListView.getSelectionModel().getSelectedItem().getMsgOnInspect());
        } else if(e.getSource() == personListView){
            this.logbookTextArea.appendText(logbook.getResponse(this.personListView.getSelectionModel().getSelectedItem()));
        } else if(e.getSource() == weaponListView){
            this.logbookTextArea.appendText(this.weaponListView.getSelectionModel().getSelectedItem().getMsgOnInspect());
        }
    }
    
    @FXML
    private void onHelpBtn(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Logbook Help Screen");
        alert.setHeaderText("Here you can read about the logbook screen \nand how you can benefit from it.");
        alert.setContentText("This window contains 3 lists.\n"
                + " - Weapons found: This list holds the murder weapons you have found throughout the game.\n"
                + " - Persons talked to: This list holds the information given to you from Persons, when interacting with them throughout the game.\n"
                + " - Items Inspected: This list holds the information given to you from inspecting items throughout the game.\n"
                + "\n"
                + "You can select one of the items from the lists and it will\nshow the information you previously learned about the particular item or person.");
        alert.showAndWait();
    }
}
