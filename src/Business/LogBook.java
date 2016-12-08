/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author chris
 */
public class LogBook {
    private HashMap<Person, String> responses;
    private ArrayList<Item> itemsInspected;
    private ArrayList<Item> murderWeapons;
    private int currentDrink;
    private final int maxDrink;
    
    public LogBook(){
        this.maxDrink = 2;
        this.currentDrink = 0;
        this.responses = new HashMap<>();
        this.murderWeapons = new ArrayList<>();
        this.itemsInspected = new ArrayList<>();
    }
    
    public String getResponse(Person person){
        return this.responses.get(person);
    }
    
    public ArrayList<Item> getMurderWeapons(){
        return this.murderWeapons;
    }
    
    public Set<Person> getPersons(){
        return this.responses.keySet();
    }
    
    public ArrayList<Item> getItems(){
        return this.itemsInspected;
    }
    
    void addPersonResponse(Person personToAdd, String toAdd){
        this.responses.put(personToAdd, toAdd);
    }
    
    boolean askedAllPersons(){
        return this.responses.size() == 5;
    }
    
    boolean gatheredAllWeapons(){
        return this.murderWeapons.size() == 5;
    }
    
    void addItemDescription(Item itemToAdd){
        if(!this.itemsInspected.contains(itemToAdd)){
            this.itemsInspected.add(itemToAdd);
        }
    }
    
    void addMurderWeapons(Item itemToAdd){
        if(!this.murderWeapons.contains(itemToAdd)){
            this.murderWeapons.add(itemToAdd);
        }
    }

    void addDrink() {
        this.currentDrink++;
    }

    boolean isDrinkMax() {
        return this.currentDrink < this.maxDrink;
    }
}