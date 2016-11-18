/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Business.LogBook;
import Business.Person;
import Business.PrintUtility;
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
public final class LoadPersons extends ScenarioLoader {

    private int state;
    private final int LOAD_ATTRIBUTES = 0;
    private final int LOAD_QUESTIONS = 1;
    private final int LOAD_ANSWERS = 2;

    public LoadPersons(String path, LogBook log, ArrayList<Room> rooms_list, ArrayList<Person> persons_list, PrintUtility printer, Time time) {
        super(path, log, rooms_list, persons_list, printer, time);
        load();
    }



    public void load() {
        File file = new File(path + "/" + "persons.txt"); //Hold file of the riddles. riddles.txt should be placed in the root folder.
        Scanner scanner = null; //if the scanner can't load the file.
        if (!CheckFile.rightFormat(file, 20)) {
            throw new IllegalArgumentException("File is probably corrupt, check if the lines count is correct.");
        }
        
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
                case "[Person]:":
                    state = LOAD_ATTRIBUTES;
                    break;
                case "[Questions]:":
                    state = LOAD_QUESTIONS;
                    break;
                case "[Answers]:":
                    state = LOAD_ANSWERS;
                    break;

                default:
                    break;
            }
            switch (state) {
                case LOAD_ATTRIBUTES:
                    int id = Integer.parseInt(scanner.nextLine());
                    String name = scanner.nextLine();
                    boolean isMurder = Boolean.parseBoolean(scanner.nextLine());
                    String keyWords1 = scanner.nextLine();
                    String keyWords2 = scanner.nextLine();
                    String keyWords3 = scanner.nextLine();
                    String accusationResponse = Util.stringConvertSmaller(scanner.nextLine());
                    String askName = scanner.nextLine();
                    int time = Integer.parseInt(scanner.nextLine());
                    Room room = getRoomByName(scanner.nextLine());
                    temp_person = new Person(id, name, isMurder, keyWords1, keyWords2, keyWords3, accusationResponse, askName, time, log);
                    persons_list.add(temp_person);
                    room.addPerson(temp_person);
                    temp_person.setWelcome(scanner.nextLine());
                    break;
                case LOAD_QUESTIONS:
                    temp_person.setQuestions(scanner.nextLine(), scanner.nextLine(), scanner.nextLine());
                    break;

                case LOAD_ANSWERS:
                    temp_person.setAnswers(Util.stringConvertSmaller(scanner.nextLine()), Util.stringConvertSmaller(scanner.nextLine()), Util.stringConvertSmaller(scanner.nextLine()));
                    break;
                default:
                    break;
            }
        }
    }
}
