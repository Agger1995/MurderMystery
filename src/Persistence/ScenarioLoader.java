/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Business.LogBook;
import Business.Person;
import Business.Riddle;
import Business.TextHandler;
import Business.Room;
import Business.Time;
import java.util.ArrayList;

/**
 *
 * @author kristian
 */
public class ScenarioLoader {

    String path;
    ArrayList<Room> rooms_list;
    LogBook log;
    ArrayList<Person> persons_list;
    TextHandler printer;
    Time time;
    private Riddle riddleRef;
    /**
     * This is the first constructor of the class ScenarioLoader.
     * It sets all the reference, that is used in the load() method.
     * @param path to load
     * @param log to write log to
     * @param rooms_list the list to store the rooms in.
     * @param persons_list the list to store all of the persons in.
     * @param printer a text handler, that handles the print.
     * @param time a time reference.
     */

    public ScenarioLoader(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time){
        this.time = time;
        this.path = path;
        this.log = log;
        this.persons_list = persons_list;
        this.printer = printer;
        this.rooms_list = rooms_list;
    }
    
    /**
     * This is the second constructor of the class ScenarioLoader.
     * It sets all the reference, that is used in the load() method.
     * The only difference between this constructor and the first, is the
     * reference to the riddle object.
     * @param path to load
     * @param log to write log to
     * @param rooms_list the list to store the rooms in.
     * @param persons_list the list to store all of the persons in.
     * @param printer a text handler, that handles the print.
     * @param time a time reference.
     * @param riddleRef riddle reference
     */
    
    public ScenarioLoader(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time,Riddle riddleRef){
        this.time = time;
        this.path = path;
        this.log = log;
        this.persons_list = persons_list;
        this.printer = printer;
        this.rooms_list = rooms_list;
        this.riddleRef = riddleRef;
    }
    
    /**
    * load() method is used to call all Load-classes, and load
    * for a given scenario.
    */
    
    public void load() {
        String[] parts = this.path.split("/");
        new LoadRooms(this.path, log, rooms_list, persons_list, printer, time);
        new LoadPersons(this.path, log, rooms_list, persons_list, printer, time);
        new LoadPersonsWithRiddle(this.path, log, rooms_list, persons_list, printer, time, riddleRef);
        new LoadItems(this.path, log, rooms_list, persons_list, printer, time);
        new LoadMiniMap(this.path, log, rooms_list, persons_list, printer, time);
        new LoadSpecialItems(this.path, log, rooms_list, persons_list, printer, time);
        new LoadWelcomeDescription(this.path, printer); //Loads and shows the description of the game.
    }

    /**
     * returns a room by its name.
     * @param name
     * @return Room object
     */
    protected Room getRoomByName(String name) {
        for (Room room : rooms_list) {
            if (room.getShortDescription().equals(name)) {
                return room;
            }
        }
        return null;
    }
}
