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
    private ArrayList<Person> suspects;
    private ArrayList<Items> inventory;
    private final int invMaxItems;
    
    public LogBook(){
        invMaxItems = 10;
        responses = new HashMap<>();
        murderWeapons = new ArrayList<>();
        suspects = new ArrayList<>();
        inventory = new ArrayList<>();
        itemsInspected = new HashMap<>();
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
    
    public void addPerson(Person toAdd){
        suspects.add(toAdd);
    }
    
    public void removePersonFromSuspectsList(Person toRemove){
        suspects.remove(toRemove);
    }
    
    public void addItemDescription(Items item){
        if(!itemsInspected.containsKey(item)){
            itemsInspected.put(item,item.getKeyWords());
        }
    }
    
    public boolean containsItem(Items toCheck){
        return inventory.contains(toCheck);
    }
    
    public void removeItem(Items toDrop){
        inventory.remove(toDrop);
    }

    public void printAll() {
        if(responses.isEmpty() && itemsInspected.isEmpty()){
            System.out.println("You haven't inspected nor interrogated anything yet.");
            return;
        }
        
        System.out.println("############################################################");
        System.out.println("########################## LOGBOOK #########################");
        System.out.println("############################################################");
        
        String format = "%15s%45s%15s\n";
        
        if(!responses.isEmpty()){
            System.out.println("########################## PERSONS #########################");
        
            Set<Person> personsAsked = responses.keySet();
            for(Person person : personsAsked){
                System.out.format(format,person.getName(), responses.get(person),person.getName());
            }
        }
        
        System.out.println("############################################################");
        
        if(!itemsInspected.isEmpty()){
            System.out.println("##########################  ITEMS  #########################");
        
            Set<Items> inspectedItems = itemsInspected.keySet();
            for(Items item : inspectedItems){
                System.out.format(format,item.getName(),itemsInspected.get(item));
            }
        }
        
        System.out.println("############################################################");
    }
}
