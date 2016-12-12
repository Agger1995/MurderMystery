package Business;

import java.util.HashMap;
import java.util.Scanner;

public class Person implements Interactable{
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

    /*
    Getter methods for the attributes!   
     */
    @Override
    public String getName() {
        return this.name;
    }
    
    boolean isAsked(){
        return this.hasBeenAsked;
    }
    
    void setHasBeenAsked(boolean condition){
        this.hasBeenAsked = condition;
    }
    
    public int getID(){
        return this.ID;
    }
    
    String getAskName(){
        return this.askName;
    }

    boolean isMurder() {
        return this.isMurder;
    }
    
    String getAccusationResponse(){
        return this.accusationResponse;
    }
    
    public void setQuestions(String q1, String q2, String q3) {
        this.questions.put(1, q1);
        this.questions.put(2, q2);
        this.questions.put(3, q3);
        this.questions.put(4, "Goodbye. \n");
    }
    
    public void setAnswers(String a1, String a2, String a3) {
        this.answers.put(1, a1);
        this.answers.put(2, a2);
        this.answers.put(3, a3);
    }
    
    public String returnQuestions() {
        this.chosenAnswer = -1;
        String toReturn = "";
        toReturn += "1: " + this.questions.get(1) + "\n";
        toReturn += "2: " + this.questions.get(2) + "\n";
        toReturn += "3: " + this.questions.get(3) + "\n";
        return toReturn;
    }
    
    int chosenAnswers() {
        return this.chosenAnswer;
    }
    
    String getAnswer(int i){
        return this.answers.get(i);
    }
    
    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }
    
    String getWelcome() {
        return this.welcome;
    }
    
    int getTimeItTakes(){
        return this.timeToAsk;
    }

    public void addToLogBook(String toAdd) {
        this.LogConnection.addPersonResponse(this, toAdd);
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}