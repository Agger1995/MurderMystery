/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * Holds all the coordinates for a given Room-object.
 * @author Laura
 */
public class Coordinates {

    private int playerX;
    private int playerY;
    private int person0X;
    private int person0Y;
    private int person1X;
    private int person1Y;
    private int person2X;
    private int person2Y;
    private int person3X;
    private int person3Y;
    private int person4X;
    private int person4Y;

    /**
     * All the coordinates for a given Room object is set in this class.
     * The first 2 coordinates are reserved for the player
     * The last 4 coordinates are reserved for 4 persons(NPCs)
     * All coordinates are int values.
     * <p>
     * All the getter methods are the same - returns either a x- or y-coordinate based on the name of the method.
     * @param playerX
     * @param playerY
     * @param person0X
     * @param person0Y
     * @param person1X
     * @param person1Y
     * @param person2X
     * @param person2Y
     * @param person3X
     * @param person3Y
     * @param person4X
     * @param person4Y 
     */
    public Coordinates(int playerX, int playerY, int person0X, int person0Y, int person1X, int person1Y, int person2X, int person2Y, int person3X, int person3Y, int person4X, int person4Y) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.person0X = person0X;
        this.person0Y = person0Y;
        this.person1X = person1X;
        this.person1Y = person1Y;
        this.person2X = person2X;
        this.person2Y = person2Y;
        this.person3X = person3X;
        this.person3Y = person3Y;
        this.person4X = person4X;
        this.person4Y = person4Y;
    }

    public int getPlayerCoordinateX() {
        return this.playerX;
    }
    public int getPlayerCoordinateY(){
        return this.playerY;
    }
    public int getPerson0CoordinateX(){
       return this.person0X; 
    }
    public int getPerson0CoordinateY(){
       return this.person0Y; 
    }
    public int getPerson1CoordinateX(){
       return this.person1X; 
    }
    public int getPerson1CoordinateY(){
       return this.person1Y; 
    }
    public int getPerson2CoordinateX(){
       return this.person2X; 
    }
    public int getPerson2CoordinateY(){
       return this.person2Y; 
    }
    public int getPerson3CoordinateX(){
       return this.person3X; 
    }
    public int getPerson3CoordinateY(){
       return this.person3Y; 
    }
    public int getPerson4CoordinateX(){
       return this.person4X; 
    }
    public int getPerson4CoordinateY(){
       return this.person4Y; 
    }
}
