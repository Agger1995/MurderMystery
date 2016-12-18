/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * The Item class is used to create new Objects of type Item. Item objects are used throughout the game, where they are placed in Room objects. The Item objects can be picked up by the player and
 * added to their Inventory. Item objects are containing information which will be added to the LogBook once inspected.
 *
 * @author chris
 */
public class Item implements Interactable {

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
     * The Item class' constructor. Is used when creating new instances of Item. Sets all the instance attributes from the parameter list.
     *
     * @param ID int, is this item's unique ID
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
     * @param Log LogBook, reference to the LogBook object created in Game object.
     */
    public Item(int ID, String name, boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, int weight, boolean isDrinkable, int timeToTake, int timeToInspect, int timeToDrink, LogBook Log) {
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
     *
     * @return int of this item's weight
     */
    int getWeight() {
        return this.weight;
    }

    /**
     * Method which gets this Item's ID
     *
     * @return int of this Items ID
     */
    int getID() {
        return this.ID;
    }

    /**
     * Method which gets this Items time it takes to pickup
     *
     * @return int of this Items timeToTake
     */
    int getTimeToTake() {
        return timeToTake;
    }

    /**
     * Method which gets this Items time it takes to inspect
     *
     * @return int of this items timeToInspect
     */
    int getTimeToInspect() {
        return timeToInspect;
    }

    /**
     * Method which gets this Items time it takes to drink
     *
     * @return int of this items timeToDrink
     */
    int getTimeToDrink() {
        return timeToDrink;
    }

    /**
     * Returns whether or not the item is pickable.
     *
     * @return true if this Item is active, false otherwise
     */
    public boolean isActive() {
        return this.isActive;
    }

    /**
     * Sets if the item has been inspected before.
     *
     * @param condition true = inspected before.
     */
    void setHasBeenInspected(boolean condition) {
        this.hasBeenInspected = condition;
    }

    /**
     * Method which gets this Items hasBeenInspected state
     *
     * @return true if this Item has been inspected, false otherwise.
     */
    boolean isInspected() {
        return this.hasBeenInspected;
    }

    /**
     * Returns whether of not the item is a murder weapon.
     *
     * @return true if this Item is a murder weapon, false otherwise.
     */
    boolean isMurderweapon() {
        return this.isMurderweapon;
    }

    /**
     * Returns the name of the item.
     *
     * @return String, name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the msg on inspect. I.e "you inspected a pickle".
     *
     * @return String, msg
     */
    public String getMsgOnInspect() {
        this.LogConnection.addItemDescription(this);
        return this.msgOnInspect;
    }

    /**
     * Message on pickup: i.e. "you picked up a pickle".
     *
     * @return String, msg.
     */
    public String getMsgOnPickup() {
        return this.msgOnPickup;
    }

    /**
     * Returns whether or not the item is drinkable.
     *
     * @return boolean, isDrinkable.
     */
    public boolean isDrinkable() {
        return this.isDrinkable;
    }

    /**
     * Returns the name of the item.
     *
     * @return string, name of item.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns the type of the item.
     *
     * @return String, type.
     */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
