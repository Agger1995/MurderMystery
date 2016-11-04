package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Room class is used to create new a new instance object of type room
 * Uses 2 parameters, description consisting of a brief description of the room
 * Contents consisting of a thorough description of the rooms contents
 * @author Christian
 */

public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> itemsInRoom;
    private ArrayList<Person> personsInRoom;
    private ArrayList<PersonWithRiddle> personRiddleInRoom;
    private int timeToMove;

    /**
     * Constructor for Room class.
     * Sets instance variables and generates a new hashmap per room created
     * The hashmap contains directions in form of "String" and the Room the direction
     * leads to as an object of type "Room".
     * @param description
     * @param timeToMove
     */
    public Room(String description, int timeToMove){
        this.description = description;
        this.exits = new HashMap<>();
        this.itemsInRoom = new ArrayList<>();
        this.personsInRoom = new ArrayList<>();
        this.timeToMove = timeToMove;
        this.personRiddleInRoom = new ArrayList<>();
    }
    
    public void addPersonWithRiddle(PersonWithRiddle toAdd){
        this.personRiddleInRoom.add(toAdd);
    }
    
    public ArrayList<PersonWithRiddle> getRiddlersInRoom(){
        return this.personRiddleInRoom;
    }

    /**
     * setExit sets an exit (direction) leading to a Room (neighbor)
     * @param direction the direction to go to get to Room
     * @param neighbor the Room object
     */
    public void setExit(String direction, Room neighbor){
        this.exits.put(direction, neighbor);
    }

    /**
     * getShortDescription returns a String with the rooms description
     * @return a String with the rooms description
     */
    public String getShortDescription(){
        return this.description;
    }
    
    public int getTimeToMove(){
        return this.timeToMove;
    }

    /**
     * getLongDescription returns a customized message including the description of the room
     * @return a String of a customized description message, and the current rooms exits
     */
    public String getLongDescription(){
        return "You are in " + this.description + ".\n" + this.getReturnString();
    }
    
    /**
     * getContents returns a String with the rooms contents
     * @return a String with the rooms contents
     */

    private String getReturnString(){
        String toReturn = "";
        toReturn += this.getExitString() + ".\n";
        
        if(!this.itemsInRoom.isEmpty()){
            toReturn += this.getItemsString() + ".\n";
        }
        if(!this.personsInRoom.isEmpty() && !this.personRiddleInRoom.isEmpty()){
            toReturn += this.getPersonsString() + ".\n";
        }
        
        return toReturn;
    }
    
    /**
     * getExitString returns a String version of all the exits to the current room
     * @return a String with available exits to current room
     */
    private String getExitString(){
        String returnString = "Exits: ";
        Set<String> keys = this.exits.keySet();
        for(String exit : keys) {
            returnString += exit + ", ";
        }
        returnString = returnString.substring(0, returnString.length() - 2);
        
        return returnString;
    }
    
    private String getItemsString(){
        String toReturn = "Items: ";
        for(Item item : this.itemsInRoom){
            toReturn += item.getName() + ", ";
        }
        toReturn = toReturn.substring(0, toReturn.length() - 2);
        
        return toReturn;
    }
    
    private String getPersonsString(){
        String toReturn = "Persons: ";
        for(Person person : this.personsInRoom){
            toReturn += person.getName() + ", ";
        }
        for(PersonWithRiddle specialPerson : this.personRiddleInRoom){
            toReturn += specialPerson.getName() + ", ";
        }
        toReturn = toReturn.substring(0, toReturn.length() - 2);
        
        return toReturn;
    }

    /**
     * getExit returns an object of type Room 
     * @param direction Is a room name
     * @return An object in the exits hashmap that is reffered to by the direction
     */
    public Room getExit(String direction){
        return this.exits.get(direction);
    }
    
    public void addItem(Item toAdd){
        this.itemsInRoom.add(toAdd);
    }
    
    public ArrayList<Item> getItems(){
        return this.itemsInRoom;
    }
    
    public void addPerson(Person toAdd){
        this.personsInRoom.add(toAdd);
    }
    
    public ArrayList<Person> getPersonsInRoom(){
        return this.personsInRoom;
    }
}