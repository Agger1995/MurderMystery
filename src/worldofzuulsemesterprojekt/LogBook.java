/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import static worldofzuulsemesterprojekt.Game.itemDatabase;
import static worldofzuulsemesterprojekt.Game.personDatabase;

/**
 *
 * @author chris
 */
public class LogBook {
    private HashMap<Integer, String> responses;
    private HashMap<Integer, String> itemsInspected;
    private ArrayList<Integer> murderWeapons;
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
    public ArrayList<Integer> getMurderWeapons(){
        return this.murderWeapons;
    }
    
    public void addPersonResponse(Integer personID, String responseKeyWords){
        this.responses.put(personID, responseKeyWords);
    }
    
    public boolean askedAllPersons(){
        return this.responses.size() == 5;
    }
    
    public boolean gatheredAllWeapons(){
        return this.murderWeapons.size() == 5;
    }
    
    public void addItemDescription(Integer itemID, String keyWords){
        if(!this.itemsInspected.containsKey(itemID)){
            this.itemsInspected.put(itemID,keyWords);
        }
    }
    
    public void addMurderWeapons(Integer itemID){
        this.murderWeapons.add(itemID);
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
        
            Set<Integer> personsAsked = this.responses.keySet();
            for(Integer personID : personsAsked){
                System.out.format(format,personDatabase.get(personID).getName() + " - ", this.responses.get(personID));
            }
            System.out.println("############################################################");
        }
        
        if(!this.itemsInspected.isEmpty()){
            System.out.println("##########################  ITEMS  #########################");
        
            Set<Integer> inspectedItems = this.itemsInspected.keySet();
            for(Integer itemID : inspectedItems){
                System.out.format(format,itemDatabase.get(itemID).getName() + " - ", this.itemsInspected.get(itemID));
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