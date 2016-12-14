/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * Point class.
 * 
 * @author chris
 */
public class Point {
    /**
     * The points which the player has acquired throughout the game.
     */
    private int points;
    
    /**
     * The Point class' constructor.
     * Is used when creating new instances of Point.
     * Only 1 instance should be created of Point throughout a game.
     */
    public Point(){
        this.points = 0;
    }
    
    /**
     * Method which adds pointsToAdd to this.points
     * Also prints a message to the player how many points has been added to the player.
     * @param pointsToAdd int, of the points to add to this.points
     * @return 
     */
    String addPoints(int pointsToAdd){
        this.points += pointsToAdd;
        return "You receive " + pointsToAdd + " points.\n";
    }
    
    String removePoints(int pointsToRemove){
        this.points -= pointsToRemove;
        return "You lost " + pointsToRemove + " points.\n";
    }
    
    /**
     * Method which gets this.points
     * @return int, of the points the player has acquired throughout the game
     */
    int getPoints(){
        return this.points;
    }
}
