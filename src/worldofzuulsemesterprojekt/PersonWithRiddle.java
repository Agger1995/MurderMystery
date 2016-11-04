/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Math.random;

/**
 *
 * @author kristian
 */
public class PersonWithRiddle {

    private HashMap<Integer, String> answers; //answers for the riddle: both wrong and right
    private boolean hasRiddle; //one riddle per person
    private final String name; //name of the riddle person. ie. Ghost
    private final Riddle riddle; //the riddle object reference
    private final Scanner input = new Scanner(System.in); //input
    private final int timeWin; //time restored when correct answer
    private final int timeLost; // time lost...
    private final Time time; //refence to the time object used
    private final String rightAnswerMessage; // message when answered correctly
    private final String wrongAnswerMessage; // ^ when answered poorly

    /**
     *
     * @param name
     * @param rightAnswerMessage
     * @param wrongAnswerMessage
     * @param timeWin
     * @param timeLost
     * @param time
     */
    public PersonWithRiddle(String name, String rightAnswerMessage, String wrongAnswerMessage, int timeWin, int timeLost, Time time) {
        this.rightAnswerMessage = rightAnswerMessage;
        this.wrongAnswerMessage = wrongAnswerMessage;
        this.timeWin = timeWin;
        this.timeLost = timeLost;
        this.name = name;
        this.time = time;
        hasRiddle = true;
        riddle = Riddle.getRandomRiddle();
        answers = new HashMap();
    }
    
    public String getName(){
        return this.name;
    }
    /*
    call runRiddle(), when the player asks PersonWithRiddle.
    */
    public void runRiddle() {
        if (hasRiddle) { //checks if a the riddle has already been solved
            System.out.println(name + ": " + riddle.getQuestion()); //prints the question
            answers = getAnswers(); //get hashmap with all of the answers
            printAnswers(); //prints all the answers
            System.out.print("> ");

            int key = -1;
            try {
                key = this.input.nextInt(); //user input
                if (key >= 5 || key <= 0) {
                    throw new IllegalArgumentException();
                }
            } catch (InputMismatchException exception) {
                //Print "sets key to -1"
                //when user put other than integer in the input
                key = -1;
                this.input.next();
            } catch (IllegalArgumentException err) {
                key = -1;
            }
            processAnswer(key); //processes the answer and rewards if correct.
        } else {
            System.out.println(name + ": I have already given you a riddle earlier....");
        }
    }

    public HashMap<Integer, String> getAnswers() {
        HashMap<Integer, String> temp = new HashMap(); //temp hashmap
        int right_answer = (int) (random() * 3) + 1; //puts the right answer somewhere.
        String[] wrong_answers = riddle.getWrongAnswers();

        temp.put(right_answer, riddle.getCorrectAnswer()); //puts the correct answer somewhere random in the hashmap
        int index = 1;
        for (String s : wrong_answers) { //places the wrong answers where there is free 1 - 3
            boolean hasPlaced = false;
            while (hasPlaced == false) {
                if (temp.containsKey(index)) {
                    index++;
                } else {
                    temp.put(index, s);
                    hasPlaced = true;
                }
            }
        }
        return temp; //returns hashmap reference
    }

    public void printAnswers() {
        for (int i = 1; i < 4; i++) {
            System.out.println(i + ": " + answers.get(i)); //prints all the answers.
        }
    }

    public void processAnswer(int key) {
        if (answers.get(key) == riddle.getCorrectAnswer()) { //prints reward message
            System.out.println(name + ": " + rightAnswerMessage);
            time.addMinute(-timeWin);
        } else {                                             //prints penalty message
            System.out.println(name + ": " + wrongAnswerMessage);
            time.addMinute(timeLost);
        }
        hasRiddle = false; //makes it so the person won't tell another riddle until next playthrough.
    }
}
