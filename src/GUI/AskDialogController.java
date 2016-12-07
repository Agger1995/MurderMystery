/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Business.Game;
import Business.Person;
import Business.PersonWithRiddle;
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
    private PersonWithRiddle personWithRiddleInDialog;
    private boolean riddleDialog;
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
        if(!this.riddleDialog){
            this.personInDialog.addToLogBook(this.stringToAddLogbook);
            this.logController.updateListViews();
        }
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    void setPersonInDialog(Object personInDialog) {
        this.questionOne.setDisable(false);
        this.questionTwo.setDisable(false);
        this.questionThree.setDisable(false);
        if( personInDialog instanceof Person){
            this.questionOne.setText("Question 1");
            this.questionTwo.setText("Question 2");
            this.questionThree.setText("Question 3");
            this.personInDialog = (Person)personInDialog;
            this.riddleDialog = false;
            this.personName.setText(this.personInDialog.getName());
            this.conversationArea.appendText(this.personInDialog.returnQuestions());
        } else if(personInDialog instanceof PersonWithRiddle){
            this.personWithRiddleInDialog = (PersonWithRiddle) personInDialog;
            this.riddleDialog = true;
            this.personName.setText(this.personWithRiddleInDialog.getName());
            if(!this.personWithRiddleInDialog.hasRiddle()){
                this.riddleHasBeenAsked(this.personWithRiddleInDialog);
                return;
            }
            this.questionOne.setText("Answer 1");
            this.questionTwo.setText("Answer 2");
            this.questionThree.setText("Answer 3");
            this.conversationArea.appendText(this.personWithRiddleInDialog.tellIntroMessage() + "\n");
            this.conversationArea.appendText(this.personWithRiddleInDialog.getRiddle() + "\n");
            this.gameRef.createRiddle(this.personWithRiddleInDialog);
            this.conversationArea.appendText(this.personWithRiddleInDialog.printAnswers() + "\n");
        }
        
    }
    
    @FXML
    private void handleQuestionAction(ActionEvent e){
        String toPrint = "";
        if(this.riddleDialog){
            if(e.getSource() == questionOne){
                toPrint = this.gameRef.handleRiddle(personWithRiddleInDialog, 1);
            } else if(e.getSource() == questionTwo){
                toPrint = this.gameRef.handleRiddle(personWithRiddleInDialog, 2);
            } else if(e.getSource() == questionThree){
                toPrint = this.gameRef.handleRiddle(personWithRiddleInDialog, 3);
            }
            this.questionOne.setDisable(true);
            this.questionThree.setDisable(true);
            this.questionTwo.setDisable(true);
        } else {
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
        }
        this.conversationArea.appendText("\n" + toPrint + "\n");
    }

    private void riddleHasBeenAsked(PersonWithRiddle personInDialog) {
        this.conversationArea.appendText(personInDialog.getName() + ": I have already given you a riddle earlier human!");
        this.questionOne.setDisable(true);
        this.questionTwo.setDisable(true);
        this.questionThree.setDisable(true);
    }
}
