/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * An event handler, that makes you able to control the movement by the Arrow
 * Keys.
 *
 * @author kristian
 */
public class ShortcutEventHandler implements EventHandler<KeyEvent> {

    private GameController controller;

    /**
     * Constructor of the event handler.
     * @param controller
     */
    public ShortcutEventHandler(GameController controller) {
        /*
        Sets the Controller reference to the GameController.
         */
        this.controller = controller;
    }

    /**
     * Handles the event thrown at it.
     *
     * @param event
     */
    @Override
    public void handle(KeyEvent event) {
        /*
        Switches between the different directions based on the source
         */
        switch (event.getCode()) {
            case UP:
                controller.goDirection("north");
                break;

            case DOWN:
                controller.goDirection("south");
                break;

            case LEFT:
                controller.goDirection("west");
                break;

            case RIGHT:
                controller.goDirection("east");
                break;

            default:
                break;
        }
    }
}
