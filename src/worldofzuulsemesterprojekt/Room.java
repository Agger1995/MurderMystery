package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

/**
 * Room class is used to create new a new instance object of type room
 * Room objects holds all informations about that room.
 * Holds which Item, SpecialItem, Person, PersonWithRiddle objects that is in this room
 * @author Christian
 */

public class Room {
    /**
     * a String representation of this room. A small description.
     */
    private String description;
    /**
     * HashMap containing all exits for this Room
     */
    private HashMap<String, Room> exits;
    /**
     * ArrayList containing all Item in this Room
     */
    private ArrayList<Item> itemsInRoom;
    /**
     * ArrayList containing all SpecialItem in this Room
     */
    private ArrayList<SpecialItem> specialItemsInRoom;
    /**
     * ArrayList containing all Person in this Room
     */
    private ArrayList<Person> personsInRoom;
    /**
     * ArrayList containing all PersonWithRiddle in this Room
     */
    private ArrayList<PersonWithRiddle> personRiddleInRoom;
    /**
     * true if this is locked, false otherwise
     */
    private boolean isLocked;
    /**
     * Room object from which this is locked
     */
    private Room lockedFrom;
    /**
     * Item object required to unlock this Room
     */
    private Item itemRequiredToUnlock;
    /**
     * 0 .. maxInt - time it takes to move from any Room to this
     */
    private int timeToMove;
    /**
     * true if this is a transport room, false otherwise
     */
    private boolean isTransportRoom;

    /**
     * Constructor for Room class.
     * Sets instance variables and generates a new hashmap per room created
     * The hashmap contains directions in form of "String" and the Room the direction
     * leads to as an object of type "Room".
     * @param description the 'name' of the room
     * @param timeToMove Time it takes to go to the room
     * @param isLocked If the room is locked
     * @param isTransportRoom If the room is a transport room
     */
    public Room(String description, int timeToMove, boolean isLocked, boolean isTransportRoom){
        this.description = description;
        this.exits = new HashMap<>();
        this.specialItemsInRoom = new ArrayList<>();
        this.itemsInRoom = new ArrayList<>();
        this.personsInRoom = new ArrayList<>();
        this.timeToMove = timeToMove;
        this.personRiddleInRoom = new ArrayList<>();
        this.isLocked = isLocked;
        this.isTransportRoom = isTransportRoom;
    }
    
    /**
     * method which gets all exits for an object of type room
     * @return HashMap<String, Room> of all exits for this.
     */
    public HashMap<String, Room> getAllExits(){
        return this.exits;
    }
    
    /**
     * method that moves a Person from one Room to another Room in this.exits
     * called from game class when a Person movement is needed
     * @param toMove Person object to move from this to another Room
     */
    public void movePerson(Person toMove){
        int randomRoomChoose = (int) (Math.random() * this.exits.size());
        Set<String> nearbyExits = this.exits.keySet();
        String chosenRoomExit = "";
        int i = 0;
        for(String roomString : nearbyExits){
            if(i == randomRoomChoose){
                chosenRoomExit = roomString;
            }
            i++;
        }
        this.getExit(chosenRoomExit).addPerson(toMove);
        this.personsInRoom.remove(toMove);
    }
    
    /**
     * gets the isLocked state of this
     * @return true if the room is locked. false otherwise
     */
    public boolean isLocked(){
        return this.isLocked;
    }
    
    /**
     * gets the room from which this is locked from
     * @return Room object from which this is locked
     */
    public Room getlockedFrom(){
        return this.lockedFrom;
    }
    
    /**
     * gets the item which is required to unlock this from lockedFrom
     * @return Item object required to unlock this
     */
    public Item getItemToUnlock(){
        return this.itemRequiredToUnlock;
    }
    
    /**
     * sets the state of isLocked for this
     * @param newState false if the room should be unlocked, true otherwise
     */
    public void setIsLocked(boolean newState){
        this.isLocked = newState;
    }
    
    /**
     * sets where this is locked from
     * @param toLockFrom Room object from which this is locked
     */
    public void setLockedFrom(Room toLockFrom){
        this.lockedFrom = toLockFrom;
    }
    
    /**
     * sets the item required to unlock this from lockedFrom
     * @param itemToUnlock Item object required to unlock
     */
    public void setItemRequiredToUnlock(Item itemToUnlock){
        this.itemRequiredToUnlock = itemToUnlock;
    }
    
    /**
     * sets the state of isTransportRoom for this
     * @param state true if this should be a transport room (teleports the player to random room), false otherwise.
     */
    public void setIsTransportRoom(boolean state){
        this.isTransportRoom = state;
    }
    
    /**
     * gets the state of isTransportRoom for this
     * @return true if this is transport room, false otherwise.
     */
    public boolean isTransportRoom(){
        return this.isTransportRoom;
    }
    
    /**
     * adds a PersonWithRiddle to this.personRiddleInRoom 
     * @param toAdd PersonWithRiddle object which should be placed in this
     */
    public void addPersonWithRiddle(PersonWithRiddle toAdd){
        this.personRiddleInRoom.add(toAdd);
    }
    
    /**
     * get the list of PersonWithRiddle for this.personRiddleInRoom
     * @return ArrayList<PersonWithRiddle> list of PersonWithRiddle in this
     */
    public ArrayList<PersonWithRiddle> getRiddlersInRoom(){
        return this.personRiddleInRoom;
    }

    /**
     * setExit sets an exit (direction) leading to a Room (neighbor)
     * adds a new key value set to this.exits HashMap<String, Room>
     * @param direction the direction to go to get to Room
     * @param neighbor the Room object
     */
    public void setExit(String direction, Room neighbor){
        this.exits.put(direction, neighbor);
    }

    /**
     * gets a String with this description
     * @return String object containing this description
     */
    public String getShortDescription(){
        return this.description;
    }
    
    /**
     * gets the timeToMove it costs to move from any Room to this
     * @return int time it takes
     */
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
     * gets a complete return string for this
     * @return a complete String representation of the contents of this
     */
    private String getReturnString(){
        String toReturn = "";
        toReturn += this.getExitString() + ".\n";
        
        if(!this.itemsInRoom.isEmpty()|| !this.specialItemsInRoom.isEmpty()){
            toReturn += this.getItemsString() + ".\n";
        }
        if(!this.personsInRoom.isEmpty() || !this.personRiddleInRoom.isEmpty()){
            toReturn += this.getPersonsString() + ".\n";
        }
        if(!this.personRiddleInRoom.isEmpty()) {
            toReturn += this.getRiddlersInRoom().get(0).tellIntroMessage() + "\n";
        }
        
        return toReturn;
    }
    
    /**
     * getExitString returns a String version of all the exits to the current room
     * @return String object for this.exits
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
    
    /**
     * gets a string representation of Item in this
     * @return String object for this.itemsInRoom
     */
    private String getItemsString(){
        String toReturn = "Items: ";
        for(Item item : this.itemsInRoom){
            toReturn += item.getName() + ", ";
        }
        for(SpecialItem sitem : this.specialItemsInRoom){
            toReturn += sitem.getName() + ", ";
        }
        toReturn = toReturn.substring(0, toReturn.length() - 2);
        
        return toReturn;
    }
    
    /**
     * gets a string representation of Person in this
     * @return String object for this.personInRoom
     */
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
    
    /**
     * adds an Item object to this.itemsInRoom
     * @param toAdd Item object to add to this.itemsInRoom
     */
    public void addItem(Item toAdd){
        this.itemsInRoom.add(toAdd);
    }
    
    /**
     * adds a SpecialItem to this.specialItemsInRoom
     * @param toAdd SpecialItem object to add to this.specialItemsInRoom
     */
    public void addSpecialItem(SpecialItem toAdd){
        this.specialItemsInRoom.add(toAdd);
    }
    
    /**
     * gets a list for this.itemsInRoom
     * @return ArrayList<Item> for this
     */
    public ArrayList<Item> getItems(){
        return this.itemsInRoom;
    }
    
    /**
     * gets a list for this.specialItemsInRoom
     * @return ArrayList<SpecialItem> for this
     */
    public ArrayList<SpecialItem> getSpecialItems(){
        return this.specialItemsInRoom;
    }
    
    /**
     * adds a Person object to this.personsInRoom
     * @param toAdd Person object to add to this.personsInRoom
     */
    public void addPerson(Person toAdd){
        this.personsInRoom.add(toAdd);
    }
    
    /**
     * gets a list for this.personsInRoom
     * @return ArrayList<Person> for this.personsInRoom
     */
    public ArrayList<Person> getPersonsInRoom(){
        return this.personsInRoom;
    }
}