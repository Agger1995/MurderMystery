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
 * @author kristian
 */
final class LoadRooms extends ScenarioLoader {

    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private final int LOAD_CONNECTIONS = 1;

    /**
     * Loads the rooms, from the rooms.txt file found at path.
     * It is essential, that this is the first class, that is run first, because
     * the fact that the other objects are saved in the Room-objects.
     * @param path to load
     * @param log to write log to
     * @param rooms_list the list to store the rooms in.
     * @param persons_list the list to store all of the persons in.
     * @param printer a text handler, that handles the print.
     * @param time a time reference.
     */
    public LoadRooms(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        load();
    }

    /**
     * Loads the rooms found at the path by the name rooms.txt.
     * This method creates the rooms, and links them together.
     */
    @Override
    public void load() {
        File file = new File(path + "/" + "rooms.txt"); //Hold file of the riddles. riddles.txt should be placed in the root folder.
        Scanner scanner = null; //if the scanner can't load the file.

        try {
            scanner = new Scanner(file); // scanner for the file
        } catch (FileNotFoundException ex) {
            try {
                //if not such file exists create it.
                file.createNewFile();
            } catch (IOException ex1) {
                Logger.getLogger(LoadRooms.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        while (scanner.hasNextLine()) { //if scanner har fundt next line of text in the file
            switch (scanner.nextLine()) {
                case "[Room]:":         //if scanner fundt "[Room]:" case, get rooms attributes
                    state = LOAD_ATTRIBUTES;
                    break;
                case "[Connections]:"://if scanner fundt "[Connections]:" case, get connections from file
                    state = LOAD_CONNECTIONS;
                    break;

                default:
                    break;
            }
            switch (state) {
                case LOAD_ATTRIBUTES: //case, that get rooms attributes and add them to room_list
                    String name = scanner.nextLine();
                    int timeToTravel = Integer.parseInt(scanner.nextLine());
                    boolean isLocked = Boolean.parseBoolean(scanner.nextLine());
                    boolean isTransportRoom = Boolean.parseBoolean(scanner.nextLine());
                    Room newRoom = new Room(name, timeToTravel, isLocked, isTransportRoom);
                    if (newRoom.isTransportRoom()) {
                        newRoom.setExit("exit", newRoom);
                        newRoom.setExitDir("west", newRoom);
                    }
                    rooms_list.add(newRoom);
                    break;
                case LOAD_CONNECTIONS: //case that get connections betweem rooms in game
                    while (scanner.hasNextLine()) {
                        String[] string = scanner.nextLine().split(",");
                        Room room = this.getRoomByName(string[0]);
                        room.setExit(string[1], this.getRoomByName(string[1]));
                        if (!this.getRoomByName(string[1]).isTransportRoom() && !room.isTransportRoom()) {
                            room.setExitDir(string[2], getRoomByName(string[1]));
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
