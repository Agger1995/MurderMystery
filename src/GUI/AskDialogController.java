/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Game;
import Business.Person;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Agger
 */
public class AskDialogController implements Initializable {
    private Person personInDialog;
    private LogbookController logController;
    private Game gameRef;
    private String stringToAddLogbook = "";
    @FXML
    private Label personName;
    @FXML
    private TextArea conversationArea;
    @FXML
    private Button questionOne, questionTwo, questionThree, closeBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    void setGameRef(Game game, LogbookController logController){
        this.gameRef = game;
        this.logController = logController;
    }
    
    @FXML
    private void handleCloseButtonAction(ActionEvent event) {
        this.personInDialog.addToLogBook(this.stringToAddLogbook);
        this.logController.updateListViews();
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    void setPersonInDialog(Person personInDialog) {
        this.personInDialog = personInDialog;
        this.personName.setText(this.personInDialog.getName());
        this.conversationArea.appendText(this.personInDialog.returnQuestions());
    }
    
    @FXML
    private void handleQuestionAction(ActionEvent e){
        String toPrint = "";
        if(e.getSource() == questionOne){
            toPrint = this.gameRef.handleInterrogation(personInDialog, 1);
            this.questionOne.setDisable(true);
        } else if(e.getSource() == questionTwo){
            toPrint = this.gameRef.handleInterrogation(personInDialog, 2);
            this.questionTwo.setDisable(true);
        } else if(e.getSource() == questionThree){
            toPrint = this.gameRef.handleInterrogation(personInDialog, 3);
            this.questionThree.setDisable(true);
        }
        this.stringToAddLogbook += toPrint + "\n";
        this.conversationArea.appendText("\n" + toPrint + "\n");
    }
}
