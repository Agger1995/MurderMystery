package Business;

import java.util.HashMap;
import java.util.Scanner;

/**
 * An interactable person, that you can "ask", "accuse", ie.
 *
 * @author kristian
 */
public class Person implements Interactable {

    private int ID;
    private String name; //Name of the person
    private boolean isMurder; //Is the person the murderer?
    private boolean hasBeenAsked;
    private String accusationResponse;
    private String askName;
    private HashMap<Integer, String> questions;
    private HashMap<Integer, String> answers;
    public int chosenAnswer;
    private String welcome;
    private Scanner input = new Scanner(System.in);
    private LogBook LogConnection;
    private int timeToAsk;

    /**
     * Creates a person with the following attributes set at start.
     *
     * @param ID int, special identification
     * @param name string, name of person
     * @param isMurder boolean, is the person a murderer?
     * @param accusationResponse string, response on accuse.
     * @param askName string, name when asked.
     * @param time int, time reference
     * @param Log LogBook, log reference.
     */
    public Person(int ID, String name, boolean isMurder, String accusationResponse, String askName, int time, LogBook Log) {
        this.ID = ID;
        this.name = name;
        this.isMurder = isMurder;
        this.accusationResponse = accusationResponse;
        this.askName = askName;
        this.hasBeenAsked = false;
        this.questions = new HashMap<>();
        this.answers = new HashMap<>();
        this.chosenAnswer = 0;
        this.welcome = "";
        this.timeToAsk = time;
        this.LogConnection = Log;
    }

    /**
     * Returns the name of the person
     *
     * @return string, name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns if the person has been asked before.
     *
     * @return boolean, true if asked before.
     */
    boolean isAsked() {
        return this.hasBeenAsked;
    }

    /**
     * Sets if the person has been asked before.
     *
     * @param condition boolean, true = has been asked before.
     */
    void setHasBeenAsked(boolean condition) {
        this.hasBeenAsked = condition;
    }

    /**
     * Gets the id of the person
     *
     * @return int, id.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Gets the name of the person.
     *
     * @return string, askName.
     */
    String getAskName() {
        return this.askName;
    }

    /**
     * Returns whether or not the person is a murderer.
     *
     * @return boolean, true = murder.
     */
    boolean isMurder() {
        return this.isMurder;
    }

    /**
     * Returns a string giving the response. Is the person a murderer, then he might say: "oh you got me".
     *
     * @return string, accusationResponse.
     */
    String getAccusationResponse() {
        return this.accusationResponse;
    }

    /**
     * Sets the available questions to ask.
     *
     * @param q1
     * @param q2
     * @param q3
     */
    public void setQuestions(String q1, String q2, String q3) {
        this.questions.put(1, q1);
        this.questions.put(2, q2);
        this.questions.put(3, q3);
        this.questions.put(4, "Goodbye. \n");
    }

    /**
     * Sets the answered received after a given question has been asked.
     *
     * @param a1
     * @param a2
     * @param a3
     */
    public void setAnswers(String a1, String a2, String a3) {
        this.answers.put(1, a1);
        this.answers.put(2, a2);
        this.answers.put(3, a3);
    }

    /**
     * Returns all the questions
     *
     * @return String, questions.
     */
    public String returnQuestions() {
        this.chosenAnswer = -1;
        String toReturn = "";
        toReturn += "1: " + this.questions.get(1) + "\n";
        toReturn += "2: " + this.questions.get(2) + "\n";
        toReturn += "3: " + this.questions.get(3) + "\n";
        return toReturn;
    }

    /**
     * Returns the chosen answer.
     *
     * @return int, chosenAnswer
     */
    int chosenAnswers() {
        return this.chosenAnswer;
    }

    /**
     * Gets the answer of a given index.
     *
     * @param i int, 1-4
     * @return String, answer.
     */
    String getAnswer(int i) {
        return this.answers.get(i);
    }

    /**
     * Sets a welcome message for the given person
     *
     * @param welcome String, welcome
     */
    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    /**
     * Gets the welcome message of the given person
     *
     * @return String, welcome message.
     */
    String getWelcome() {
        return this.welcome;
    }

    /**
     * Time it takes to ask the person
     *
     * @return int, timeToAsk.
     */
    int getTimeItTakes() {
        return this.timeToAsk;
    }

    /**
     * Adds information to the logbook concerning the answers to the questions.
     *
     * @param toAdd String, answer to add.
     */
    public void addToLogBook(String toAdd) {
        this.LogConnection.addPersonResponse(this, toAdd);
    }

    /**
     * Returns the name of the person
     *
     * @return String, name
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns the type of the person
     *
     * @return String, type
     */
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
