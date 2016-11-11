/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;

/**
 *
 * @author kristian
 */
public class ScenarioLoader {

    protected String path;
    protected ArrayList<Room> rooms_list;
    protected LogBook log;
    protected ArrayList<Person> persons_list;
    protected PrintUtility printer;
    protected Time time;

    public ScenarioLoader(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, PrintUtility printer, Time time) {
        this.time = time;
        this.path = path;
        this.log = log;
        this.persons_list = persons_list;
        this.printer = printer;
        this.rooms_list = rooms_list;
    }
    
    public void load() {
        new LoadRooms(this.path, log, rooms_list, persons_list, printer, time);
        new LoadPersons(this.path, log, rooms_list, persons_list, printer, time);
        new LoadPersonsWithRiddle(this.path, log, rooms_list, persons_list, printer, time);
        new LoadItems(this.path, log, rooms_list, persons_list, printer, time);
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
