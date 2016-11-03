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
    private HashMap<Item, String> itemsInspected;
    private ArrayList<Item> murderWeapons;
    private int currentDrink;
    private final int maxDrink;
    
    public LogBook(){
        this.maxDrink = 3;
        this.currentDrink = 0;
        this.responses = new HashMap<>();
        this.murderWeapons = new ArrayList<>();
        this.itemsInspected = new HashMap<>();
    }
    
    /**
     *
     * @return
     */
    public ArrayList<Item> getMurderWeapons(){
        return this.murderWeapons;
    }
    
    public void addPersonResponse(Person personToAdd){
        this.responses.put(personToAdd, personToAdd.getKeyWords());
    }
    
    public boolean askedAllPersons(){
        return this.responses.size() == 5;
    }
    
    public boolean gatheredAllWeapons(){
        return this.murderWeapons.size() == 5;
    }
    
    public void addItemDescription(Item itemToAdd, String keyWords){
        if(!this.itemsInspected.containsKey(itemToAdd)){
            this.itemsInspected.put(itemToAdd,keyWords);
        }
    }
    
    public void addMurderWeapons(Item itemToAdd){
        this.murderWeapons.add(itemToAdd);
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
            for(Person personToPrint : personsAsked){
                System.out.format(format,personToPrint.getName() + " - ", this.responses.get(personToPrint));
            }
            System.out.println("############################################################");
        }
        
        if(!this.itemsInspected.isEmpty()){
            System.out.println("##########################  ITEMS  #########################");
        
            Set<Item> inspectedItems = this.itemsInspected.keySet();
            for(Item itemToPrint : inspectedItems){
                System.out.format(format,itemToPrint.getName() + " - ", this.itemsInspected.get(itemToPrint));
            }
            System.out.println("############################################################");
        }
        System.out.println("\n");
    }

    public void addDrink() {
        this.currentDrink++;
    }

    public boolean isDrinkMax() {
        return this.currentDrink < this.maxDrink;
    }
}