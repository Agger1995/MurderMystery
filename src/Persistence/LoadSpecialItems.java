/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Business.LogBook;
import Business.Person;
import Business.TextHandler;
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
 * @author Laura LoadSpecialItems class inherit the ScenarioLoader class and therefore class used that same atributes and methods
 */
final class LoadSpecialItems extends ScenarioLoader {

    private HashMap<String, SpecialItem> special_items_list;

    /**
     * Does almost have the same functionality as the LoadItems class, but it has some extra funtionality, because the items are special.
     *
     * @param path to load
     * @param log to write log to
     * @param rooms_list the list to store the rooms in.
     * @param persons_list the list to store all of the persons in.
     * @param printer a text handler, that handles the print.
     * @param time a time reference.
     */
    public LoadSpecialItems(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        special_items_list = new HashMap();
        this.load();
    }

    /**
     * Loads the data from the file named specialItems.txt, found in the path given in the constructor.
     */
    @Override
    public void load() {
        File file = new File(path + "/" + "specialItems.txt"); //Hold file of the riddles. riddles.txt should be placed in the root folder.
        Scanner scanner = null; //if the scanner can't load the file.

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
                    String messageOnInspect = scanner.nextLine();
                    boolean isMurderWeapon = Boolean.parseBoolean(scanner.nextLine());
                    int weight = Integer.parseInt(scanner.nextLine());
                    boolean isDrinkable = Boolean.parseBoolean(scanner.nextLine());
                    int timeToTake = Integer.parseInt(scanner.nextLine());
                    int timeToInspect = Integer.parseInt(scanner.nextLine());
                    int timeToDrink = Integer.parseInt(scanner.nextLine());
                    boolean isSecretEntrance = Boolean.parseBoolean(scanner.nextLine());
                    String[] exits = scanner.nextLine().split(",");
                    String secretExitName = exits[0];
                    String secretExitDir = exits[1];
                    String secretExitsRoomsName = scanner.nextLine();

                    SpecialItem sItem = new SpecialItem(id, name, isActive, messageOnPickup, messageOnInspect, isMurderWeapon, weight, isDrinkable, timeToTake, timeToInspect, timeToDrink, log, isSecretEntrance);
                    sItem.setSecretExit(secretExitName, getRoomByName(secretExitsRoomsName));
                    sItem.setDirectionalExit(secretExitDir, getRoomByName(secretExitsRoomsName));
                    room.addSpecialItem(sItem);
                    special_items_list.put(name, sItem);
                    break;

                default:
                    break;
            }
        }
    }
}
