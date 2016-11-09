/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kristian
 */
public final class LoadItems {

    private String path;
    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private ArrayList<Room> rooms_list;
    private HashMap<String, Item> items_list;
    private LogBook log;

    public LoadItems(String path, LogBook log, ArrayList<Room> rooms_list) {
        items_list = new HashMap();
        this.log = log;
        this.path = path;
        this.rooms_list = rooms_list;
        state = LOAD_ATTRIBUTES;
        load();
    }

    public void load() {
        File file = new File(path + "/" + "items.txt"); //Hold file of the riddles. riddles.txt should be placed in the root folder.
        Scanner scanner = null; //if the scanner can't load the file.
//        if (!CheckFile.rightFormat(file, 16)) {
//            throw new IllegalArgumentException("File is probably corrupt, check if the lines count is correct.");
//        }

        try {
            scanner = new Scanner(file); // scanner for the file
        } catch (FileNotFoundException ex) {
            try {
                //if not such file exists create it.
                file.createNewFile();
            } catch (IOException ex1) {
                Logger.getLogger(LoadItems.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        while (scanner.hasNextLine()) {
            switch (scanner.nextLine()) {
                case "[Item]:":
                    Room room = getRoomByName(scanner.nextLine());
                    int id = Integer.parseInt(scanner.nextLine());
                    String name = scanner.nextLine();
                    boolean isActive = Boolean.parseBoolean(scanner.nextLine());
                    String messageOnPickup = scanner.nextLine();
                    String messageOnInspect = Util.stringConvertSmaller(scanner.nextLine());
                    boolean isMurderWeapon = Boolean.parseBoolean(scanner.nextLine());
                    String keyWords = scanner.nextLine();
                    int weight = Integer.parseInt(scanner.nextLine());
                    boolean isDrinkable = Boolean.parseBoolean(scanner.nextLine());
                    int timeToTake = Integer.parseInt(scanner.nextLine());
                    int timeToInspect = Integer.parseInt(scanner.nextLine());
                    int timeToDrink = Integer.parseInt(scanner.nextLine());

                    Item item = new Item(id, name, isActive, messageOnPickup, messageOnInspect, isMurderWeapon, keyWords, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, log);

                    room.addItem(item);
                    items_list.put(name, item);
                    break;

                case "[Secret Room]:":
                    String[] word = scanner.nextLine().split(",");
                    Item temp_item = items_list.get(word[0]);
                    temp_item.setIsSecretEntrance(true);
                    temp_item.setSecretExit(word[1], getRoomByName(word[2]));
                    break;
                    
                case "[Locked Door]:":
                    String[] key = scanner.nextLine().split(",");
                    Item itemToUnlock = items_list.get(key[0]);
                    Room LockedFrom = getRoomByName(key[1]);
                    Room LockedRoom = getRoomByName(key[2]);
                    LockedRoom.setIsLocked(true);
                    LockedRoom.setItemRequiredToUnlock(itemToUnlock);
                    LockedRoom.setLockedFrom(LockedFrom);
                    break;
                    
                default:
                    break;
            }
        }
    }

    public Room getRoomByName(String name) {
        for (Room room : rooms_list) {
            if (room.getShortDescription().equals(name)) {
                return room;
            }
        }
        return null;
    }
}
