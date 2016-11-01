/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

/**
 *
 * @author chris
 */
public class Point {
    private int points;
    
    public Point(){
        this.points = 0;
    }
    
    public void addPoints(int pointsToAdd){
        this.points += pointsToAdd;
        System.out.println("You receive " + pointsToAdd + " points.");
    }
    
    public int getPoints(){
        return points;
    }
}
