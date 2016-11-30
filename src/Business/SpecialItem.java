/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import javafx.util.Pair;

/**
 * SpecialItem class.
 * Is used to create special Item objects.
 * These objects are primarily used to describe if an Item leads to a secret entrance.
 * This class extends from Item, because we wish to treat these SpecialItems alike normal Items, only with a few more attributes connected.
 * @author Laura
 */
public class SpecialItem extends Item implements Interactable {
    /**
     * True if this Item is a secret entrace, meaning that upon inspection a secret entrance will appear in the Room where this SpecialItem is placed.
     */
    private boolean isSecretEntrance;
    /**
     * Is used to hold the exit key value pair which this SpecialItem adds to the Room where this SpecialItem is placed. The key value pair is added to the Room's available exits upon inspection.
     */
    private Pair<String, Room> secretExit;
    
    /**
     * SpecialItem's constructor.
     * Is used when creating a new instance of SpecialItem.
     * When calling this constructor, a call to the super constructor (Item) is also made.
     * Resulting in the Item class attributes is also being set. 
     * @param ID int, is this Items unique ID
     * @param name String, is this Items 'name'
     * @param isActive boolean, if this Item can be picked up
     * @param msgOnPickup String, the message to be displayed on pickup
     * @param msgOnInspect String, the message to be displayed on inspect
     * @param isMurderWeapon boolean, if this Item is a murder weapon
     * @param keyWords String, the keywords describing this Item to be added to LogBook
     * @param weight int, is this Item's weight. How much space it takes in Inventory
     * @param isDrinkable boolean, if this Item is drinkable
     * @param timeToTake int, time it takes to pickup this Item
     * @param timeToInspect int, time it takes to inspect this Item
     * @param timeToDrink int, time it takes to drink this Item
     * @param Log LogBook, reference to the LogBook object created in Game class.
     * @param isSecretEntrance boolean, true if this SpecialItem is a secret entrance
     */
    public SpecialItem(int ID, String name, boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, String keyWords, int weight, boolean isDrinkable, int timeToTake, int timeToInspect, int timeToDrink, LogBook Log, boolean isSecretEntrance) {
        super(ID, name, isActive, msgOnPickup, msgOnInspect, isMurderWeapon, keyWords, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, Log);
        this.isSecretEntrance = isSecretEntrance;
    }
    
    /**
     * Method which sets this.secretExit key value pair.
     * @param exitString String, is the direction the player must choose to get to the secret room
     * @param exitRoom Room, is the Room object which the exitString leads to.
     */
    public void setSecretExit(String exitString, Room exitRoom){
        this.secretExit = new Pair<>(exitString, exitRoom);
    }
    
    /**
     * Method which gets the key for the pair
     * @return String, key of this.secretExit pair
     */
    public String getSecretExitFirst(){
        return this.secretExit.getKey();
    }
    
    /**
     * Method which gets the value for the pair
     * @return Room, value of this.secretExit pair
     */
    public Room getSecretExitSecond(){
        return this.secretExit.getValue();
    }
    
    /**
     * Method which sets if this SpecialItem is a secret entrance
     * @param state True if this SpecialItem is a secret entrance, false otherwise
     */
    public void setIsSecretEntrance(boolean state){
        this.isSecretEntrance = state;
    }
    
    /**
     * Method which gets this.isSecretEntrance state
     * @return true if this SpecialItem is a secret entrance, false otherwise.
     */
    public boolean isSecretEntrance(){
        return this.isSecretEntrance;
    }
}
