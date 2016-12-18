/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;

/**
 * Inventory class is used to hold all items the player has picked up throughout the game. There is only created one Inventory object throughout the game, it holds objects of type Item.
 *
 * @author chris
 */
public class Inventory {

    /**
     * ArrayList containing Item objects. These Item objects are the ones the player has picked up throughout the game.
     */
    private ArrayList<Item> inventoryItems;
    /**
     * Sets the max amount of Item objects the inventoryItems ArrayList can hold.
     */
    private int invMaxItems;
    /**
     * Holds an int refering to how much Item weight a player is carrying in combined Item objects.
     */
    private int currentInv;

    /**
     * The Inventory class' constructor. Is called in Game class to instantiate an Inventory object. Is used throughout the game to put Item objects into the Inventory
     *
     * @param maxInventorySlots the maximum amount of Item objects weight a player can have in his inventory
     */
    public Inventory(int maxInventorySlots) {
        this.inventoryItems = new ArrayList<>();
        this.invMaxItems = maxInventorySlots;
        this.currentInv = 0;
    }

    /**
     * Checks if the item is placed in the inventory.
     *
     * @param itemToCheck Item object to check if is in this.inventoryItems
     * @return true if itemToCheck is in this.inventoryItems, false otherwise
     */
    boolean containsItem(Item itemToCheck) {
        return this.inventoryItems.contains(itemToCheck);
    }

    /**
     * Drops item to room
     *
     * @param itemToDrop Item object to remove from this.inventoryItems.
     */
    void removeItem(Item itemToDrop) {
        this.inventoryItems.remove(itemToDrop);
        this.currentInv -= itemToDrop.getWeight();
    }

    /**
     * Method which gets the entire Inventory for a player.
     *
     * @return this.inventoryItems as ArrayList
     */
    ArrayList<Item> getInventory() {
        return this.inventoryItems;
    }

    /**
     * Adds item to inventory, if the inventory isn't full.
     *
     * @param itemToAdd Item object to add to inventory.
     */
    void addInventory(Item itemToAdd) {
        if (this.isInventoryFull(itemToAdd.getWeight())) {
            this.currentInv += itemToAdd.getWeight();
            this.inventoryItems.add(itemToAdd);
        }
    }

    /**
     * Gets the size of the inventory.
     *
     * @return int refering to the combined weight of the players inventory
     */
    int getInventorySize() {
        return this.currentInv;
    }

    /**
     * Method which checks if a given Item weight exceeds the maximum allowed weight for the inventory
     *
     * @param weightTocheck int refering to an Item objects weight
     * @return true if the weightToCheck added to the currentInv weight doesn't exceed the maximum allowed weight.
     */
    boolean isInventoryFull(int weightTocheck) {
        return (this.currentInv + weightTocheck) <= this.invMaxItems;
    }

    /**
     * Returns the size of the inventory
     *
     * @return int, size.
     */
    int getMaxInventorySize() {
        return this.invMaxItems;
    }
}
