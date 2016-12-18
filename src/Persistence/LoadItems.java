/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Business.Item;
import Business.LogBook;
import Business.Person;
import Business.TextHandler;
import Business.Room;
import Business.Time;
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
final class LoadItems extends ScenarioLoader {

    private HashMap<String, Item> items_list;

    /**
     * Loads the items found at the path by the name of items.txt.
     *
     * @param path
     * @param log
     * @param rooms_list
     * @param persons_list
     * @param printer
     * @param time
     */
    public LoadItems(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        items_list = new HashMap();
        this.load();
    }

    /**
     * Loads the items found at the path defined in the constructor by name of items.txt.
     */
    @Override
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
                case "[Item]:": //if scanner fundt "[Item]:" case, get items attributes and put this in the items_list
                    Room room = getRoomByName(scanner.nextLine());
                    int id = Integer.parseInt(scanner.nextLine());
                    String name = scanner.nextLine();
                    boolean isActive = Boolean.parseBoolean(scanner.nextLine());
                    String messageOnPickup = scanner.nextLine();
                    String messageOnInspect = scanner.nextLine();
                    boolean isMurderWeapon = Boolean.parseBoolean(scanner.nextLine());
                    int weight = Integer.parseInt(scanner.nextLine());
                    boolean isDrinkable = Boolean.parseBoolean(scanner.nextLine());
                    int timeToTake = Integer.parseInt(scanner.nextLine());
                    int timeToInspect = Integer.parseInt(scanner.nextLine());
                    int timeToDrink = Integer.parseInt(scanner.nextLine());

                    Item item = new Item(id, name, isActive, messageOnPickup, messageOnInspect, isMurderWeapon, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, log);

                    room.addItem(item);
                    items_list.put(name, item);
                    break;

                case "[Locked Door]:": //if scanner fundt "[Locked Door]:" case, show this some "locked door" until player do not provide key.
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
}
