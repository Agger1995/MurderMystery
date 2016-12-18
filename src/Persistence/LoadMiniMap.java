/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Business.Coordinates;
import Business.LogBook;
import Business.Person;
import Business.Room;
import Business.TextHandler;
import Business.Time;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laura
 */
class LoadMiniMap extends ScenarioLoader {

    protected ArrayList<Room> rooms_list;

    private int state;
    private final int LOAD_ATTRIBUTES = 0;

    /**
     * Called in the ScenarioLoader.
     *
     * @param path to load
     * @param log to write log to
     * @param rooms_list the list to store the rooms in.
     * @param persons_list the list to store all of the persons in.
     * @param printer a text handler, that handles the print.
     * @param time a time reference.
     */
    public LoadMiniMap(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        load();
    }

    /**
     * load is a method, that is overriden from the ScenarioLoader. It loads the data, from the minimap.txt file, which is found at the path given in the constructor.
     */

    @Override
    public void load() {
        File file = new File(this.path + "/" + "minimap.txt");
        Scanner scanner = null;

        try {
            scanner = new Scanner(file); // scanner for the file
        } catch (FileNotFoundException ex) {
            try {
                //if not such file exists create it.
                file.createNewFile();
            } catch (IOException ex1) {
                Logger.getLogger(LoadMiniMap.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        Room temp_room = null;
        while (scanner.hasNextLine()) {
            switch (scanner.nextLine()) {
                case "[Room]:":
                    state = LOAD_ATTRIBUTES;//if scanner fundt "[Room]:" case, get minimap attributes for that room
                    break;
                default:
                    break;
            }
            switch (state) {
                case LOAD_ATTRIBUTES: //case that get coordinates on the map for each person, if if it is located in this room.
                    Room room = getRoomByName(scanner.nextLine());
                    int playerX1 = Integer.parseInt(scanner.nextLine());
                    int playerY1 = Integer.parseInt(scanner.nextLine());
                    int person0X1 = Integer.parseInt(scanner.nextLine());
                    int person0Y1 = Integer.parseInt(scanner.nextLine());
                    int person1X1 = Integer.parseInt(scanner.nextLine());
                    int person1Y1 = Integer.parseInt(scanner.nextLine());
                    int person2X1 = Integer.parseInt(scanner.nextLine());
                    int person2Y1 = Integer.parseInt(scanner.nextLine());
                    int person3X1 = Integer.parseInt(scanner.nextLine());
                    int person3Y1 = Integer.parseInt(scanner.nextLine());
                    int person4X1 = Integer.parseInt(scanner.nextLine());
                    int person4Y1 = Integer.parseInt(scanner.nextLine());
                    Coordinates coo = new Coordinates(playerX1, playerY1, person0X1, person0Y1, person1X1,
                            person1Y1, person2X1, person2Y1, person3X1, person3Y1, person4X1, person4Y1);
                    room.setCoordinates(coo);
                default:
                    break;
            }
        }
    }

}
