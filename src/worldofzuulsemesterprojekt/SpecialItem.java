/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import javafx.util.Pair;

/**
 *
 * @author Laura
 */
public class SpecialItem extends Item {
    private boolean isSecretEntrance;
    private Pair<String, Room> secretExit;
    
    public SpecialItem(int ID, String name, boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, String keyWords, int weight, boolean isDrinkable, int timeToTake, int timeToInspect, int timeToDrink, LogBook Log, boolean isSecretEntrance) {
        super(ID, name, isActive, msgOnPickup, msgOnInspect, isMurderWeapon, keyWords, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, Log);
        this.isSecretEntrance = isSecretEntrance;
    }
    
    public void setSecretExit(String exitString, Room exitRoom){
        this.secretExit = new Pair<>(exitString, exitRoom);
    }
    
    public String getSecretExitFirst(){
        return this.secretExit.getKey();
    }
    
    public Room getSecretExitSecond(){
        return this.secretExit.getValue();
    }
    
    public void setIsSecretEntrance(boolean state){
        this.isSecretEntrance = state;
    }
    
    public boolean isSecretEntrance(){
        return this.isSecretEntrance;
    }
}
