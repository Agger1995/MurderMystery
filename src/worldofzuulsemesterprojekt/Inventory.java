/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Inventory {
    private final ArrayList<Item> inventoryItems;
    private final int invMaxItems;
    private int currentInv;
    
    public Inventory(int maxInventorySlots){
        this.inventoryItems = new ArrayList<>();
        this.invMaxItems = maxInventorySlots;
        this.currentInv = 0;
    }
    
    public boolean containsItem(Item itemToCheck){
        return this.inventoryItems.contains(itemToCheck);
    }
    
    public void removeItem(Item itemToDrop){
        this.inventoryItems.remove(itemToDrop);
        this.currentInv -= itemToDrop.getWeight();
    }
    
    public ArrayList<Item> getInventory(){
        return this.inventoryItems;
    }
    
    public void addInventory(Item itemToAdd){
        if(this.isInventoryFull(itemToAdd.getWeight())){
            this.currentInv += itemToAdd.getWeight();
            this.inventoryItems.add(itemToAdd);
        }
    }
    
    public int getInventorySize(){
        return this.currentInv;
    }
    
    public boolean isInventoryFull(int weightTocheck){
        return (this.currentInv + weightTocheck) <= this.invMaxItems;
    }
    
    public int getMaxInventorySize(){
        return this.invMaxItems;
    }
}
