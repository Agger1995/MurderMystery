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
public final class LoadRooms extends ScenarioLoader {

    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private final int LOAD_CONNECTIONS = 1;

    public LoadRooms(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        load();
    }


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
                    if (newRoom.isTransportRoom()) {
                        newRoom.setExit("exit", newRoom);
                    }
                    rooms_list.add(newRoom);
                    break;
                case LOAD_CONNECTIONS:
                    while (scanner.hasNextLine()) {
                        String[] string = scanner.nextLine().split(",");
                        Room room = this.getRoomByName(string[0]);
                        room.setExit(string[1], this.getRoomByName(string[1]));
                        room.setExitDir(string[2], getRoomByName(string[1]));
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
