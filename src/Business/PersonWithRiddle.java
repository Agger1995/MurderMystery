/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;
import java.util.Scanner;
import static java.lang.Math.random;

/**
 *
 * @author kristian
 */
public class PersonWithRiddle implements Interactable{

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
    private String introMessage; //This is the message received, when the person is first seen
    private boolean hasToldIntroMessage;

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
        hasToldIntroMessage = false;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return this.name;
    }
    
    public String getRiddle(){
        return this.riddle.getQuestion();
    }

    /*
    call runRiddle(), when the player asks PersonWithRiddle.
     */
    /**
     *
     */
    public void createRiddle() {
        answers = getAnswers(); //get hashmap with all of the answer
    }
    
    public boolean hasRiddle(){
        return this.hasRiddle;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @return 
     */
    public String printAnswers() {
        String toReturn = "";
        for (int i = 1; i < 4; i++) {
            toReturn += i + ": " + answers.get(i) + "\n"; //prints all the answers.
        }
        return toReturn;
    }

    /**
     *
     * @param key
     * @return 
     */
    public String processAnswer(int key) {
        this.hasRiddle = false; //makes it so the person won't tell another riddle until next playthrough.
        if (this.answers.get(key).equals(this.riddle.getCorrectAnswer())) { //prints reward message
            this.time.addMinute(-this.timeWin);
            return this.name + ": " + this.rightAnswerMessage;
        } else {                                             //prints penalty message
            this.time.addMinute(this.timeLost);
            return this.name + ": " + this.wrongAnswerMessage;
        }
    }

    public void setIntroMessage(String str) {
        this.introMessage = str; //Set's intro message.
    }

    public String tellIntroMessage() {
        if (!hasToldIntroMessage) {
            hasToldIntroMessage = true;
            return introMessage;
        }
        return "";
    }
    
    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
