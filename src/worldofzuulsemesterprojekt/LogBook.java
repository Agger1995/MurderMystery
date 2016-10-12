/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author chris
 */
public class LogBook {
    private HashMap<Person, String> responses;
    private ArrayList<Items> murderWeapons;
    private ArrayList<Person> suspects;
    private ArrayList<Items> inventory;
    private final int invMaxItems;
    
    public LogBook(){
        invMaxItems = 10;
        responses = new HashMap<>();
        murderWeapons = new ArrayList<>();
        suspects = new ArrayList<>();
        inventory = new ArrayList<>();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Items> getInventory(){
        return inventory;
    }
    
    public ArrayList<Person> getSuspects(){
        return suspects;
    }
    
    public ArrayList<Items> getMurderWeapons(){
        return murderWeapons;
    }
    
    public void addInventory(Items toAdd){
        if(toAdd.isMurderweapon()){
            murderWeapons.add(toAdd);
            return;
        }
        if(inventory.size() < invMaxItems){
            inventory.add(toAdd);
        } else {
            System.out.println("Inventory is full!");
        }
    }
    
    public boolean containsItem(Items toCheck){
        return inventory.contains(toCheck);
    }
    
    public void removeItem(Items toDrop){
        inventory.remove(toDrop);
    }

    public void printAll() {
        
    }
}
