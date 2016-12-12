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
    
    /**
     * Method that returns the value of the hashmap responses, corresponsing to the specified person parameter
     * @param person Person object for which we want to get the value of the hashmap responses 
     * @return String
     */
    public String getResponse(Person person){
        return this.responses.get(person);
    }
    
    /**
     * Method that returns the arraylist murderWeapons
     * @return Arraylist holding Item objects
     */
    public ArrayList<Item> getMurderWeapons(){
        return this.murderWeapons;
    }
    
    /**
     * Method that returns a set of responses keys and their corresponding values within the set.
     * @return Set holding Person objects
     */
    public Set<Person> getPersons(){
        return this.responses.keySet();
    }
    
    /**
     * Method that returns the arraylist itemsInspected
     * @return Arraylist holding Item objects
     */
    public ArrayList<Item> getItems(){
        return this.itemsInspected;
    }
    
    /**
     * Method that adds a Person objects response to the responses hashmap.
     * @param personToAdd Person object to add as a key to the hashmap
     * @param toAdd String object to add as a value with personToAdd as key to the hashmap
     */
    void addPersonResponse(Person personToAdd, String toAdd){
        this.responses.put(personToAdd, toAdd);
    }
    
    /**
     * Method that adds an Item object to the arraylist itemsInspected
     * The method checks whether the Item object already exists in the list prior to adding it
     * @param itemToAdd Item object to add
     */
    void addItemDescription(Item itemToAdd){
        if(!this.itemsInspected.contains(itemToAdd)){
            this.itemsInspected.add(itemToAdd);
        }
    }
    
    /**
     * Method that adds an Item object to the arraylist murderWeapons
     * The method checks whether the Item object already exists in the list prior to adding it
     * @param itemToAdd Item object to add
     */
    void addMurderWeapons(Item itemToAdd){
        if(!this.murderWeapons.contains(itemToAdd)){
            this.murderWeapons.add(itemToAdd);
        }
    }

    /**
     * Method that counts currentDrink int attribute up by 1
     */
    void addDrink() {
        this.currentDrink++;
    }

    /**
     * Method that checks if currentDrink attribute exceeds maxDrink attribute
     * @return true if currentDrink > maxDrink, false otherwise
     */
    boolean isDrinkMax() {
        return this.currentDrink < this.maxDrink;
    }
}