/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author chris
 */
public class LogBook {
    private HashMap<Person, String> responses;
    private HashMap<Items,String> itemsInspected;
    private ArrayList<Items> murderWeapons;
    private ArrayList<Items> inventory;
    private final int invMaxItems;
    private int currentInv;
    
    public LogBook(){
        this.invMaxItems = 10;
        this.currentInv = 0;
        this.responses = new HashMap<>();
        this.murderWeapons = new ArrayList<>();
        this.inventory = new ArrayList<>();
        this.itemsInspected = new HashMap<>();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Items> getInventory(){
        return this.inventory;
    }
    
    public int getInventorySize(){
        return this.currentInv;
    }
    
    public boolean isInventoryFull(Items toCheck){
        if((this.currentInv + toCheck.getWeight()) <= this.invMaxItems){
            return true;
        } else {
            return false;
        }
    }
    
    public int getMaxInventorySize(){
        return this.invMaxItems;
    }
    
    public ArrayList<Items> getMurderWeapons(){
        return this.murderWeapons;
    }
    
    public void addInventory(Items toAdd){
        if(toAdd.isMurderweapon()){
            this.murderWeapons.add(toAdd);
            return;
        }
        if(this.isInventoryFull(toAdd)){
            this.currentInv += toAdd.getWeight();
            this.inventory.add(toAdd);
        }
    }
    
    public void addPersonResponse(Person person, String responseKeyWords){
        this.responses.put(person, responseKeyWords);
    }
    
    public boolean askedAllPersons(){
        return this.responses.size() == 5;
    }
    
    public boolean gatheredAllWeapons(){
        return this.murderWeapons.size() == 5;
    }
    
    public void addItemDescription(Items item){
        if(!this.itemsInspected.containsKey(item)){
            this.itemsInspected.put(item,item.getKeyWords());
        }
    }
    
    public boolean containsItem(Items toCheck){
        return this.inventory.contains(toCheck);
    }
    
    public void removeItem(Items toDrop){
        this.inventory.remove(toDrop);
        this.currentInv -= toDrop.getWeight();
    }

    public void printAll() {
        if(this.responses.isEmpty() && this.itemsInspected.isEmpty()){
            System.out.println("You haven't inspected nor interrogated anything yet.");
            return;
        }
        
        System.out.println("############################################################");
        System.out.println("########################## LOGBOOK #########################");
        System.out.println("############################################################");
        
        String format = "%15s%45s\n";
        
        if(!this.responses.isEmpty()){
            System.out.println("########################## PERSONS #########################");
        
            Set<Person> personsAsked = this.responses.keySet();
            for(Person person : personsAsked){
                System.out.format(format,person.getName() + " - ", this.responses.get(person));
            }
            System.out.println("############################################################");
        }
        
        if(!this.itemsInspected.isEmpty()){
            System.out.println("##########################  ITEMS  #########################");
        
            Set<Items> inspectedItems = this.itemsInspected.keySet();
            for(Items item : inspectedItems){
                System.out.format(format,item.getName() + " - ", this.itemsInspected.get(item));
            }
            System.out.println("############################################################");
        }
        System.out.println("\n");
    }
}