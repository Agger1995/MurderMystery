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

    public ScenarioLoader(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time){
        this.time = time;
        this.path = path;
        this.log = log;
        this.persons_list = persons_list;
        this.printer = printer;
        this.rooms_list = rooms_list;
    }
    
    public ScenarioLoader(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time, Riddle riddleRef) {
        this(path, log, rooms_list, persons_list, printer, time);
        this.riddleRef = riddleRef;
    }
    
    public void load() {
        new LoadRooms(this.path, log, rooms_list, persons_list, printer, time);
        new LoadPersons(this.path, log, rooms_list, persons_list, printer, time);
        new LoadPersonsWithRiddle(this.path, log, rooms_list, persons_list, printer, time, riddleRef);
        new LoadItems(this.path, log, rooms_list, persons_list, printer, time);
        new LoadSpecialItems(this.path, log, rooms_list, persons_list, printer, time);
        new LoadWelcomeDescription(this.path, printer); //Loads and shows the description of the game.
    }

    protected Room getRoomByName(String name) {
        for (Room room : rooms_list) {
            if (room.getShortDescription().equals(name)) {
                return room;
            }
        }
        return null;
    }
}
