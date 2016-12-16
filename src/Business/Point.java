/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * Point class. Holds the total amount of points.
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
     * Only 1 instance should be created of Point throughout the game.
     */
    public Point(){
        this.points = 0;
    }
    
    /**
     * Adds points to the total points.
     * @param pointsToAdd int, amount of points to add.
     */
    String addPoints(int pointsToAdd){
        this.points += pointsToAdd;
        return "You receive " + pointsToAdd + " points.\n";
    }
    
    /**
     * Removes a points from the total.
     * @param pointsToRemove int, total to remove.
     * @return String, the amount you lose in human readable level.
     */
    String removePoints(int pointsToRemove){
        this.points -= pointsToRemove;
        return "You lost " + pointsToRemove + " points.\n";
    }
    
    /**
     * Get the total amount of points scored.
     * @return int, the total amount of points.
     */
    int getPoints(){
        return this.points;
    }
}
