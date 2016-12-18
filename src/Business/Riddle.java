/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Reads a riddle from riddle.txt, and returns a random one. Won't ever return the same riddle in the same playthrough.
 *
 * @author kristian
 */
public class Riddle {

    private ArrayList<Riddle> RIDDLES = new ArrayList(); // A database filled with all of the riddles. !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private String path;                                         // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private String question; //one question per riddle
    private String correctAnswer; //one answer per riddle
    private String[] wrongAnswers = new String[2]; //2 wrong answers pr riddle.

    /**
     * Loads a riddle from the path, by the name riddles.txt
     */
    private void load() {

        File file = new File(path + "/riddles.txt"); //Hold file of the riddles. riddles.txt should be placed in the root folder.
        Scanner scanner = null; //if the scanner can't load the file.

        try {
            scanner = new Scanner(file); // scanner for the file
        } catch (FileNotFoundException ex) {
            try {
                //if not such file exists create it.
                file.createNewFile();
            } catch (IOException ex1) {
                Logger.getLogger(Riddle.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }

        while (scanner.hasNext()) {
            Riddle temp = new Riddle(); //temporary object to hold the riddle. Will be put the in the RIDDLES database
            temp.question = scanner.nextLine(); //reads question
            temp.correctAnswer = scanner.nextLine(); //reads the one correct answer
            temp.wrongAnswers[0] = scanner.nextLine(); //wrong answers:
            temp.wrongAnswers[1] = scanner.nextLine();
            RIDDLES.add(temp); //put reference in RIDDLE database.
        }
    }

    /**
     * Returns a random riddle object when called.
     *
     * @return a random riddle object.
     */
    Riddle getRandomRiddle() {
        if (RIDDLES.isEmpty()) { //loads all the riddles, if not used before.
            load();
        }
        if (RIDDLES.isEmpty()) { //checks if there is placed any riddles in the riddles.txt file.
            throw new NullPointerException("No riddles are placed in the riddles.txt file...");
        }

        int random_index = (int) (Math.random() * RIDDLES.size()); //get random index
        Riddle temp = RIDDLES.get(random_index); //get riddle from index.
        RIDDLES.remove(random_index); //removes the riddle from the list, so it won't be drawn twice on the same playthrough.

        return temp;  //returns a random riddle object
    }

    /**
     * Sets the path to the folder containing the riddles file.
     *
     * @param path to the folder containing the riddles.txt file
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the question
     */
    String getQuestion() {
        return question;
    }

    /**
     * @return the correctAnswer
     */
    String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * @return the wrongAnswers
     */
    String[] getWrongAnswers() {
        return wrongAnswers;
    }
}
