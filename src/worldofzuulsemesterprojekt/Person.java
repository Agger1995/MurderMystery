package worldofzuulsemesterprojekt;

public class Person {
    private final String    name; //Name of the person
    //private final String    profession; //Profession of the person
    private final boolean   isMurder; //Is the person the murderer?
    //private final Items     weapon; //Which weapon was used in the murder? Item name
    private final String    response; //Response when talked to.
    private final String    keyWords;
    private final String    accusationResponse;
    private final String    askName;
    

    public Person(String name, /*String profession, Items weapon,*/ boolean isMurder, String response, String keyWords, String accusationResponse, String askName) {
        this.name = name;
        //this.profession = profession;
        this.isMurder = isMurder;
        //this.weapon = weapon;
        this.response = response;
        this.keyWords = keyWords;
        this.accusationResponse = accusationResponse;
        this.askName = askName;
    }
    
    public Person(String name, /*String profession,*/ String response, String keyWords, String accusationResponse, String askName) {
        this.name = name;
        //this.profession = profession;
        this.response = response;
        this.keyWords = keyWords;
        this.accusationResponse = accusationResponse;
        this.askName = askName;
        
        this.isMurder = false;
        //this.weapon = null; //if weapon is not specified, then it is set to null.
    }

    /*
    Getter methods for the attributes!   
     */
    public String getName() {
        return name;
    }
    
    public String getAskName(){
        return askName;
    }

    /*public String getProfession() {
        return profession;
    }*/

    public boolean isMurder() {
        return isMurder;
    }

    /*public Items getWeapon() {
        return weapon;
    }*/

    public String getResponse() {
        return response;
    }
    
    public String getKeyWords(){
        return keyWords;
    }
    
    public String getAccusationResponse(){
        return accusationResponse;
    }
}
