package worldofzuulsemesterprojekt;

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
    private HashMap<String, Room> exits;

    /**
     * Constructor for Room class, 
     * @param description
     * @param contents 
     */
    public Room(String description, String contents){
        this.description = description;
        this.contents = contents;
        exits = new HashMap<String, Room>();
    }

    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    public String getShortDescription(){
        return description;
    }

    public String getLongDescription(){
        return "You are in " + description + ".\n" + getExitString();
    }
    
    public String getContents(){
        return contents;
    }

    private String getExitString(){
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }
}

