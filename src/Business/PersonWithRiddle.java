/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.HashMap;
import static java.lang.Math.random;

/**
 * A special kind of person, only interactable through "ask" command. Gives you a riddle and rewards you, if it's correctly answered.
 *
 * @author kristian
 */
public class PersonWithRiddle implements Interactable {

    private HashMap<Integer, String> answers; //answers for the riddle: both wrong and right
    private boolean hasRiddle; //one riddle per person
    private String name; //name of the riddle person. ie. Ghost
    private Riddle riddle; //the riddle object reference
    private int timeWin; //time restored when correct answer
    private int timeLost; // time lost...
    private Time time; //refence to the time object used
    private String rightAnswerMessage; // message when answered correctly
    private String wrongAnswerMessage; // ^ when answered poorly
    private String introMessage; //This is the message received, when the person is first seen
    private boolean hasToldIntroMessage;
    private Riddle riddleRef;

    /**
     * Creates a special person, that has a riddle. Can't be accused, only asked.
     *
     * @param name name of the riddle person
     * @param rightAnswerMessage string if right answer is chosen
     * @param wrongAnswerMessage string if wrong answer is chosen
     * @param timeWin time gained on right answer
     * @param timeLost time lost on wrong answer
     * @param time time to add extra time to.
     * @param riddleRef reference to riddle object.
     */
    public PersonWithRiddle(String name, String rightAnswerMessage, String wrongAnswerMessage, int timeWin, int timeLost, Time time, Riddle riddleRef) {
        this.rightAnswerMessage = rightAnswerMessage;
        this.wrongAnswerMessage = wrongAnswerMessage;
        this.timeWin = timeWin;
        this.timeLost = timeLost;
        this.name = name;
        this.time = time;
        this.hasRiddle = true;
        this.riddleRef = riddleRef;
        this.riddle = this.riddleRef.getRandomRiddle();
        this.answers = new HashMap();
        this.hasToldIntroMessage = false;
    }

    /**
     * @return string, name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns a question
     *
     * @return string, question.
     */
    public String getRiddle() {
        return this.riddle.getQuestion();
    }

    /**
     * fills up the hashmap: answers, with all of the answers. Both wrong and right.
     */
    void createRiddle() {
        answers = getAnswers(); //get hashmap with all of the answer
    }

    /**
     * Returns true, if the person has a riddle left.
     *
     * @return boolean
     */
    public boolean hasRiddle() {
        return this.hasRiddle;
    }

    /**
     * Mixes a riddle answers and questions, so the same question won't have the same order.
     *
     * @return HashMap that contains both wrong and right answers.
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
     * @return string of all of the answers.
     */
    public String printAnswers() {
        String toReturn = "";
        for (int i = 1; i < 4; i++) {
            toReturn += i + ": " + answers.get(i) + "\n"; //prints all the answers.
        }
        return toReturn;
    }

    /**
     * Checks if the answer answered is answered correctly. Gives or takes time.
     *
     * @param key of the answer chosen
     * @return string of the response of the person.
     */
    String processAnswer(int key) {
        this.hasRiddle = false; //makes it so the person won't tell another riddle until next playthrough.
        if (this.answers.get(key).equals(this.riddle.getCorrectAnswer())) { //prints reward message
            this.time.addMinute(-this.timeWin);
            return this.name + ": " + this.rightAnswerMessage;
        } else {                                             //prints penalty message
            this.time.addMinute(this.timeLost);
            return this.name + ": " + this.wrongAnswerMessage;
        }
    }

    /**
     * Sets the intro message of the person, when first met.
     *
     * @param str
     */
    public void setIntroMessage(String str) {
        this.introMessage = str; //Set's intro message.
    }

    /**
     * Tells the first intro message, when the person is first met.
     *
     * @return
     */
    public String tellIntroMessage() {
        if (!hasToldIntroMessage) {
            hasToldIntroMessage = true;
            return introMessage;
        }
        return "";
    }

    /**
     * returns the name of the person.
     *
     * @return string, name
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * returns the type of the person.
     *
     * @return String, type.
     */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
