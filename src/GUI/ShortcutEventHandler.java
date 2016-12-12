/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author kristian
 */
public class ShortcutEventHandler implements EventHandler<KeyEvent> {
    private GameController controller;

    public ShortcutEventHandler(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(KeyEvent event) {
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
