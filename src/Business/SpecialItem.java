/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import javafx.util.Pair;

/**
 * SpecialItem class. Is used to create special Item objects. These objects are primarily used to describe if an Item leads to a secret entrance. This class extends from Item, because we wish to treat
 * these SpecialItems alike normal Items, only with a few more attributes connected.
 *
 * @author Laura
 */
public class SpecialItem extends Item implements Interactable {

    private boolean isSecretEntrance;

    private Pair<String, Room> secretExit;
    private Pair<String, Room> directionalExit;

    /**
     * SpecialItem's constructor. Is used when creating a new instance of SpecialItem. When calling this constructor, a call to the super constructor (Item) is also made. Resulting in the Item class
     * attributes is also being set.
     *
     * @param ID int, is this Items unique ID
     * @param name String, name of the item
     * @param isActive boolean, can this Item can be picked up?
     * @param msgOnPickup String, the message to be displayed on pickup
     * @param msgOnInspect String, the message to be displayed on inspect
     * @param isMurderWeapon boolean, if this item is a murder weapon
     * @param weight int, weight of the item. How much does it take
     * @param isDrinkable boolean, is this item drinkable?
     * @param timeToTake int, time it takes to pickup this Item
     * @param timeToInspect int, time it takes to inspect this Item
     * @param timeToDrink int, time it takes to drink this Item, if it's drinkable.
     * @param Log LogBook, reference to the LogBook object created in Game class.
     * @param isSecretEntrance boolean, true if this SpecialItem is a secret entrance
     */
    public SpecialItem(int ID, String name, boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, int weight, boolean isDrinkable, int timeToTake, int timeToInspect, int timeToDrink, LogBook Log, boolean isSecretEntrance) {
        super(ID, name, isActive, msgOnPickup, msgOnInspect, isMurderWeapon, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, Log);
        this.isSecretEntrance = isSecretEntrance;
    }

    /**
     * If the item is a secret exit, it creates a link between two rooms.
     *
     * @param exitString String, is the direction the player must choose to get to the secret room
     * @param exitRoom Room, is the Room object which the exitString leads to.
     */
    public void setSecretExit(String exitString, Room exitRoom) {
        this.secretExit = new Pair<>(exitString, exitRoom);
    }

    /**
     * @return String, key of this.secretExit pair
     */
    String getSecretExitFirst() {
        return this.secretExit.getKey();
    }

    /**
     * Which direction to press, to travel to the secret room.
     *
     * @param direction north, west, south, east.
     * @param room
     */
    public void setDirectionalExit(String direction, Room room) {
        this.directionalExit = new Pair<>(direction, room);
    }

    /**
     * Method which gets the value for the pair
     *
     * @return Room, value of this.secretExit pair
     */
    Room getSecretExitSecond() {
        return this.secretExit.getValue();
    }

    /**
     * Returns the directional exit generated by the special item.
     *
     * @return room, ie. kitchen, ballroom..
     */
    Room getDirectionalExit() {
        return this.directionalExit.getValue();
    }

    /**
     * This returns the direction to the room.
     *
     * @return either north, west, south, east
     */
    String getDirectionalKey() {
        return this.directionalExit.getKey();
    }

    /**
     * Sets whether or not the special item is a secret entrance to a room.
     *
     * @param state true = secret entrance, false = not a secret entrance.
     */
    void setSecretEntrance(boolean state) {
        this.isSecretEntrance = state;
    }

    /**
     * Method that returns true, if the item is a secret entrance.
     *
     * @return true if it's a secret entrance.
     */
    boolean isSecretEntrance() {
        return this.isSecretEntrance;
    }
}
