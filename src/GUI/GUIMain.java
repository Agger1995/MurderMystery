/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * GUIMain - Initializes the MainMenu Controller,
 * found in the MainMenuFXML.fxml
 * @author chris
 */
public class GUIMain extends Application {
    
    /**
     * Loads the layout, which initializes a controller.
     * 
     * @param primaryStage
     * @throws IOException 
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("MainMenuFXML.fxml").openStream());
        MainMenuController controller = (MainMenuController) loader.getController();
        controller.setCurrentStage(primaryStage);
        Scene scene = new Scene(root);
        
        primaryStage.getIcons().add(new Image("gameIcon.jpg"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        });
    }

    /**
     * The main method.
     * Calls 'launch(args)', which later calls the start method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
