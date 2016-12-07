/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * The Item class is used to create new Objects of type Item.
 * Item objects are used throughout the game, where they are placed in Room objects.
 * The Item objects can be picked up by the player and added to their Inventory.
 * Item objects are containing information which will be added to the LogBook once inspected.
 * @author chris
 */
public class Item implements Interactable{
    /**
     * True if an item should be active, meaning it can be picked up. False otherwise.
     */
    private boolean isActive;
    /**
     * The ID for an item. This should be unique for an Item object.
     */
    private int ID;
    /**
     * True if this Item is a murder weapon, meaning it will be treated differently when inspected. False otherwise.
     */
    private boolean isMurderweapon;
    /**
     * The message to be displayed to the player when Inspecting an Item in game.
     */
    private String msgOnInspect;
    /**
     * The Item objects name. Is used to refer to this Item in game.
     */
    private String name;
    /**
     * The message to be displayed to the player when picking up the item in game.
     */
    private String msgOnPickup;
    /**
     * True if this Item has been inspected before. False otherwise
     */
    private boolean hasBeenInspected;
    /**
     * An Items weight. This defines how much this Item weighs and how much space it takes in the Inventory.
     */
    private int weight;
    /**
     * True if this Item can be drunk from. False otherwise.
     */
    private boolean isDrinkable;
    /**
     * The Item object's own reference to the LogBook. This is here so that every Item object themselves will add their own descriptions to the LogBook upon inspectiong etc.
     */
    private LogBook LogConnection;
    /**
     * An Items time parameter it takes to pick up the item.
     */
    private int timeToTake;
    /**
     * An Items time parameter it takes to inspect the Item.
     */
    private int timeToInspect;
    /**
     * An items time parameter it takes to Drink the Item.
     */
    private int timeToDrink;
    
    /**
     * The Item class' constructor.
     * Is used when creating new instances of Item.
     * Sets all the instance attributes from the parameter list.
     * @param ID int, is this Items unique ID
     * @param name String, is this Items 'name'
     * @param isActive boolean, if this Item can be picked up
     * @param msgOnPickup String, the message to be displayed on pickup
     * @param msgOnInspect String, the message to be displayed on inspect
     * @param isMurderWeapon boolean, if this Item is a murder weapon
     * @param weight int, is this Item's weight. How much space it takes in Inventory
     * @param isDrinkable boolean, if this Item is drinkable
     * @param timeToTake int, time it takes to pickup this Item
     * @param timeToInspect int, time it takes to inspect this Item
     * @param timeToDrink int, time it takes to drink this Item
     * @param Log LogBook, reference to the LogBook object created in Game class.
     */
    public Item(int ID, String name, boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, int weight, boolean isDrinkable, int timeToTake, int timeToInspect, int timeToDrink, LogBook Log){
        this.ID = ID;
        this.isActive = isActive;
        this.name = name;
        this.msgOnPickup = msgOnPickup;
        this.hasBeenInspected = false;
        this.msgOnInspect = msgOnInspect;
        this.isMurderweapon = isMurderWeapon;
        this.weight = weight;
        this.isDrinkable = isDrinkable;
        this.timeToTake = timeToTake;
        this.timeToInspect = timeToInspect;
        this.timeToDrink = timeToDrink;
        this.LogConnection = Log;
    }
    
    /**
     * Method which gets this Item's weight
     * @return int of this Items weight
     */
    public int getWeight(){
        return this.weight;
    }
    
    /**
     * Method which gets this Item's ID
     * @return int of this Items ID
     */
    public int getID(){
        return this.ID;
    }
    
    /**
     * Method which gets this Items time it takes to pickup
     * @return int of this Items timeToTake
     */
    public int getTimeToTake(){
        return timeToTake;
    }
    
    /**
     * Method which gets this Items time it takes to inspect
     * @return int of this items timeToInspect
     */
    public int getTimeToInspect(){
        return timeToInspect;
    }
    
    /**
     * Method which gets this Items time it takes to drink
     * @return int of this items timeToDrink
     */
    public int getTimeToDrink(){
        return timeToDrink;
    }
    
    /**
     * Method which gets this Items active state
     * @return true if this Item is active, false otherwise
     */
    public boolean isActive(){
        return this.isActive;
    }
    
    /**
     * Method which sets this Items hasBeenInspected attributes
     * @param condition is the new state of this.hasBeenInspected. True if this Item has been Inspected, false otherwise.
     */
    public void setHasBeenInspected(boolean condition){
        this.hasBeenInspected = condition;
    }
    
    /**
     * Method which gets this Items hasBeenInspected state
     * @return true if this Item has been inspected, false otherwise.
     */
    public boolean isInspected(){
        return this.hasBeenInspected;
    }
    
    /**
     * Method which gets this Items isMuderweapon state
     * @return true if this Item is a murder weapon, false otherwise.
     */
    public boolean isMurderweapon(){
        return this.isMurderweapon;
    }
    
    /**
     * Method which gets this Items name attribute
     * @return String of this items name
     */
    
    @Override
    public String getName(){
        return this.name;
    }
    
    /**
     * Method which gets this Items msgOnInspect
     * Also adds this Items keywords to the LogBook through this.LogConnection.
     * @return String of this items msgOnInspect
     */
    public String getMsgOnInspect(){
        this.LogConnection.addItemDescription(this);
        return this.msgOnInspect;
    }
    
    /**
     * Method which gets this Items msgOnPickup
     * @return String of this items msgOnPickup
     */
    public String getMsgOnPickup(){
        return this.msgOnPickup;
    }
    
    /**
     * Method which gets this Items isDrinkable state
     * @return true if this Item is drinkable, false otherwise.
     */
    public boolean isDrinkable(){
        return this.isDrinkable;
    }
    @Override
    public String toString()
    {
        return this.name;
    }
   
    @Override
    public String getType()
    {
        return this.getClass().getSimpleName();
    }
}