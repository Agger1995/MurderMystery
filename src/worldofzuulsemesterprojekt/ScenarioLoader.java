/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kristian
 */
public final class ScenarioLoader {

    private String path;

    public ScenarioLoader(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list) {
        this.path = path;
        load(path, log, rooms_list, persons_list);
    }
    
    public void load(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list) {
        new LoadRooms(this.path, rooms_list);
        new LoadPersons(this.path, log, rooms_list, persons_list);
        new LoadItems(this.path, log, rooms_list);
    }
}
