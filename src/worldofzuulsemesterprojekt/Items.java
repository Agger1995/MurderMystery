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
public class Items {
    private boolean isActive = false; 
    private boolean isMurderweapon = false;
    private String type = null;
    private String msgOnInspect = null;
    private String name;
    private String msgOnPickup;
    private String keyWords;
    
    public Items(boolean isActive, String msgOnPickup, String msgOnInspect, boolean isMurderWeapon, String name, String keyWords){
        this.isActive = isActive;
        this.name = name;
        this.msgOnPickup = msgOnPickup;
        this.msgOnInspect = msgOnInspect;
        this.isMurderweapon = isMurderWeapon; 
        this.keyWords = keyWords;
    }
    
    public boolean isActive(){
        return isActive;
    }
    
    public boolean isMurderweapon(){
        return isMurderweapon;
    }
    
    public String getName(){
        return name;
    }
    
    public String getMsgOnInspect(){
        return msgOnInspect;
    }
    
    public String getMsgOnPickup(){
        return msgOnPickup;
    }
    
    public String getKeyWords(){
        return keyWords;
    }
}
