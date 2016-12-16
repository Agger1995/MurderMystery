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
final class LoadPersons extends ScenarioLoader {
    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private final int LOAD_QUESTIONS = 1;
    private final int LOAD_ANSWERS = 2;

    /**
     * Loads the persons found at the path, by the name of persons.txt.
     * @param path to load
     * @param log to write log to
     * @param rooms_list the list to store the rooms in.
     * @param persons_list the list to store all of the persons in.
     * @param printer a text handler, that handles the print.
     * @param time a time reference.
     */
    public LoadPersons(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, TextHandler printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        this.load();
    }


    /**
     * Loads the persons found the persons.txt file at the path defined in the constructor.
     */
    public void load() {
        File file = new File(path + "/" + "persons.txt");
        Scanner scanner = null;
        
        try {
            scanner = new Scanner(file); // scanner for the file
        } catch (FileNotFoundException ex) {
            try {
                //if not such file exists create it.
                file.createNewFile();
            } catch (IOException ex1) {
                Logger.getLogger(LoadPersons.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        Person temp_person = null;
        while (scanner.hasNextLine()) {
            switch (scanner.nextLine()) {
                case "[Person]:": //if scanner fundt "[Person]:" case, get persons attributes
                    state = LOAD_ATTRIBUTES;
                    break;
                case "[Questions]:"://if scanner fundt "[Questions]:" case, get questions, that player can ask person about
                    state = LOAD_QUESTIONS;
                    break;
                case "[Answers]:":
                    state = LOAD_ANSWERS;//if scanner fundt "[Answers]:" case, get answers, that player can ger from person to his questions
                    break;

                default:
                    break;
            }
            switch (state) {
                case LOAD_ATTRIBUTES: //case, that get persons attributes
                    int id = Integer.parseInt(scanner.nextLine());
                    String name = scanner.nextLine();
                    boolean isMurder = Boolean.parseBoolean(scanner.nextLine());
                    String accusationResponse = scanner.nextLine();
                    String askName = scanner.nextLine();
                    int time = Integer.parseInt(scanner.nextLine());
                    Room room = getRoomByName(scanner.nextLine());
                    temp_person = new Person(id, name, isMurder, accusationResponse, askName, time, log);
                    persons_list.add(temp_person);
                    room.addPerson(temp_person);
                    temp_person.setWelcome(scanner.nextLine());
                    break;
                case LOAD_QUESTIONS: //case, that get questions
                    temp_person.setQuestions(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                    break;

                case LOAD_ANSWERS://case, that get answers
                    temp_person.setAnswers(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                    break;
                default:
                    break;
            }
        }
    }
}
