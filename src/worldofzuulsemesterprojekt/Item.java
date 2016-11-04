/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

/**
 *
 * @author chris
 */
public class Item {
    private final boolean isActive;
    private final int ID;
    private final boolean isMurderweapon;
    private final String msgOnInspect;
    private final String name;
    private final String msgOnPickup;
    private boolean hasBeenInspected;
    private final String keyWords;
    private final int weight;
    private final boolean isDrinkable;
    private LogBook LogConnection;
    private final int timeToTake;
    private final int timeToInspect;
    private final int timeToDrink;
    
    public Item(int ID, String name, boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, String keyWords, int weight, boolean isDrinkable, int timeToTake, int timeToInspect, int timeToDrink, LogBook Log){
        this.ID = ID;
        this.isActive = isActive;
        this.name = name;
        this.msgOnPickup = msgOnPickup;
        this.hasBeenInspected = false;
        this.msgOnInspect = msgOnInspect;
        this.isMurderweapon = isMurderWeapon; 
        this.keyWords = keyWords;
        this.weight = weight;
        this.isDrinkable = isDrinkable;
        this.timeToTake = timeToTake;
        this.timeToInspect = timeToInspect;
        this.timeToDrink = timeToDrink;
        this.LogConnection = Log;
    }
    
    public int getWeight(){
        return this.weight;
    }
    
    public int getID(){
        return this.ID;
    }
    
    public int getTimeToTake(){
        return timeToTake;//returnes the time it takes to take the item
    }
    
    public int getTimeToInspect(){
        return timeToInspect;//returnes the time it takes to inspect the item
    }
    
    public int getTimeToDrink(){
        return timeToDrink;//returnes the time it takes to drink the item
    }
    
    public boolean isActive(){
        return this.isActive;
    }
    
    public void setHasBeenInspected(boolean condition){
        this.hasBeenInspected = condition;
    }
    
    public boolean isInspected(){
        return this.hasBeenInspected;
    }
    
    public boolean isMurderweapon(){
        return this.isMurderweapon;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getMsgOnInspect(){
        this.LogConnection.addItemDescription(this, this.getKeyWords());
        return this.msgOnInspect;
    }
    
    public String getMsgOnPickup(){
        return this.msgOnPickup;
    }
    
    public String getKeyWords(){
        return this.keyWords;
    }
    
    public boolean isDrinkable(){
        return this.isDrinkable;
    }
}