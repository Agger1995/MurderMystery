/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Business.LogBook;
import Business.Person;
import Business.PrintUtility;
import Business.Room;
import Business.SpecialItem;
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
 * @author Laura
 */
public class LoadSpecialItems extends ScenarioLoader {
    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private HashMap<String, SpecialItem> specialitems_list;

    public LoadSpecialItems(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, PrintUtility printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        specialitems_list = new HashMap();
        load();
    }
      @Override
    public void load() {
        File file = new File(path + "/" + "specialItems.txt"); //Hold file of the riddles. riddles.txt should be placed in the root folder.
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
                case "[SpecialItem]:":
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
                    boolean isSecretEntrance = Boolean.parseBoolean(scanner.nextLine());
                    String secretExitName = scanner.nextLine();
                    String secretExitsRoomsName = scanner.nextLine();
                    
                    SpecialItem sitem = new SpecialItem(id, name, isActive, messageOnPickup, messageOnInspect, isMurderWeapon, keyWords, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, log, isSecretEntrance);
                    sitem.setSecretExit(secretExitName, getRoomByName(secretExitsRoomsName));
                    room.addSpecialItem(sitem);
                    specialitems_list.put(name, sitem);
                    break;

                case "[Secret Room]:":
                    String[] word = scanner.nextLine().split(",");
                    SpecialItem temp_specialitem = specialitems_list.get(word[0]);
                    temp_specialitem.setIsSecretEntrance(true);
                    temp_specialitem.setSecretExit(word[1], getRoomByName(word[2]));
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

    


