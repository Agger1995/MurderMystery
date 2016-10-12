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

public class Room 
{
    private String description;
    private String contents;
    //private ArrayList<Item> items;
    //private ArrayList<Person> persons;
    private HashMap<String, Room> exits;
    private ArrayList<Items> itemsInRoom;

    /**
     * Constructor for Room class.
     * Sets instance variables and generates a new hashmap per room created
     * The hashmap contains directions in form of "String" and the Room the direction
     * leads to as an object of type "Room".
     * @param description
     * @param contents 
     */
    public Room(String description, String contents){
        this.description = description;
        this.contents = contents;
        exits = new HashMap<String, Room>();
        itemsInRoom = new ArrayList<>();
    }

    /**
     * setExit sets an exit (direction) leading to a Room (neighbor)
     * @param direction the direction to go to get to Room
     * @param neighbor the Room object
     */
    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    /**
     * getShortDescription returns a String with the rooms description
     * @return a String with the rooms description
     */
    public String getShortDescription(){
        return description;
    }

    /**
     * getLongDescription returns a customized message including the description of the room
     * @return a String of a customized description message, and the current rooms exits
     */
    public String getLongDescription(){
        return "You are in " + description + ".\n" + getItemsString() + ".\n" + getExitString();
    }
    
    /**
     * getContents returns a String with the rooms contents
     * @return a String with the rooms contents
     */
    public String getContents(){
        return contents;
    }

    /**
     * getExitString returns a String version of all the exits to the current room
     * @return a String with available exits to current room
     */
    private String getExitString(){
        String returnString = "Exits: ";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += exit + ", ";
        }
        returnString = returnString.substring(0, returnString.length() - 2);
        
        return returnString;
    }
    
    private String getItemsString(){
        String toReturn = "Items: ";
        for(Items item : itemsInRoom){
            toReturn += item.getName() + ", ";
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
        return exits.get(direction);
    }
    
    public void addItem(boolean isActive, String use, String description, boolean isMurderWeapon, String name){
        itemsInRoom.add(new Items(isActive, use, description, isMurderWeapon, name));
    }
    
    public ArrayList<Items> getItems(){
        return itemsInRoom;
    }
}
