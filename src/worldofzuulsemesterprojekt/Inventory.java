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
    private final ArrayList<Integer> inventoryItems;
    private final int invMaxItems;
    private int currentInv;
    
    public Inventory(int maxInventorySlots){
        this.inventoryItems = new ArrayList<>();
        this.invMaxItems = maxInventorySlots;
        this.currentInv = 0;
    }
    
    public boolean containsItem(Integer idToCheck){
        return this.inventoryItems.contains(idToCheck);
    }
    
    public void removeItem(Integer idToDrop, int itemWeight){
        this.inventoryItems.remove(idToDrop);
        this.currentInv -= itemWeight;
    }
    
    public ArrayList<Integer> getInventory(){
        return this.inventoryItems;
    }
    
    public void addInventory(Integer idToAdd, Integer itemWeight){
        if(this.isInventoryFull(itemWeight)){
            this.currentInv += itemWeight;
            this.inventoryItems.add(idToAdd);
        }
    }
    
    public int getInventorySize(){
        return this.currentInv;
    }
    
    public boolean isInventoryFull(Integer itemWeight){
        return (this.currentInv + itemWeight) <= this.invMaxItems;
    }
    
    public int getMaxInventorySize(){
        return this.invMaxItems;
    }
}
