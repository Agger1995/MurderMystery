/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.io.FileNotFoundException;

/**
 * Begin the game class
 * @author Agger
 */
public class WorldOfZuulSemesterProjekt {

    /**
     * Main method, the game is initialized from here, nothing more.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Game game;
        game = new Game();
        
        try{
            game.play();
        } catch (FileNotFoundException fnferr){
            System.out.println("Error in highscore system.");
        }
    }
}
