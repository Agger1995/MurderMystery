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
    private String description = null;
    private String name;
    private String use;
    private String keyWords;
    
    public Items(boolean isActive, String use, String description, boolean isMurderWeapon, String name, String keyWords){
        this.isActive = isActive;
        this.name = name;
        this.use = use;
        this.description = description;
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
    
    public String getDescription(){
        return description;
    }
    
    public String getUse(){
        return use;
    }
    
    public String getKeyWords(){
        return keyWords;
    }
}
