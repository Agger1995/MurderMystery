package worldofzuulsemesterprojekt;

public class Person {
    private final String    name; //Name of the person
    private final boolean   isMurder; //Is the person the murderer?
    private final String    response; //Response when talked to.
    private final String    keyWords;
    private final String    accusationResponse;
    private final String    askName;
    

    public Person(String name, boolean isMurder, String response, String keyWords, String accusationResponse, String askName) {
        this.name = name;
        this.isMurder = isMurder;
        this.response = response;
        this.keyWords = keyWords;
        this.accusationResponse = accusationResponse;
        this.askName = askName;
    }
    
    public Person(String name, String response, String keyWords, String accusationResponse, String askName) {
        this.name = name;
        this.response = response;
        this.keyWords = keyWords;
        this.accusationResponse = accusationResponse;
        this.askName = askName;
        this.isMurder = false;
    }

    /*
    Getter methods for the attributes!   
     */
    public String getName() {
        return this.name;
    }
    
    public String getAskName(){
        return this.askName;
    }

    public boolean isMurder() {
        return this.isMurder;
    }

    public String getResponse() {
        return this.response;
    }
    
    public String getKeyWords(){
        return this.keyWords;
    }
    
    public String getAccusationResponse(){
        return this.accusationResponse;
    }
}