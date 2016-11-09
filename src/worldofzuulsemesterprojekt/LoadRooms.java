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
public final class LoadRooms {

    private String path;
    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private final int LOAD_CONNECTIONS = 1;
    private ArrayList<Room> rooms_list;

    public LoadRooms(String path, ArrayList<Room> rooms_list) {
        this.path = path;
        this.rooms_list = rooms_list;
        state = LOAD_ATTRIBUTES;
        load();
    }

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
        while (scanner.hasNextLine()) {
            switch (scanner.nextLine()) {
                case "[Room]:":
                    state = LOAD_ATTRIBUTES;
                    break;
                case "[Connections]:":
                    state = LOAD_CONNECTIONS;
                    break;

                default:
                    break;
            }
            switch (state) {
                case LOAD_ATTRIBUTES:
                    String name = scanner.nextLine();
                    int timeToTravel = Integer.parseInt(scanner.nextLine());
                    boolean isLocked = Boolean.parseBoolean(scanner.nextLine());
                    boolean isTransportRoom = Boolean.parseBoolean(scanner.nextLine());
                    Room newRoom = new Room(name, timeToTravel, isLocked, isTransportRoom);
                    rooms_list.add(newRoom);
                    break;
                case LOAD_CONNECTIONS:
                    while (scanner.hasNextLine()) {
                        String[] string = scanner.nextLine().split(",");
                        this.getRoomByName(string[0]).setExit(string[1], this.getRoomByName(string[1]));
                    }
                    break;
                default:
                    break;
            }
        }
    }
    
    public Room getRoomByName(String name) {
        for(Room room : rooms_list) {
            if (room.getShortDescription().equals(name)) {
                return room;
            }
        }
        return null;
    }
}
