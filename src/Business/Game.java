package Business;

import GUI.CommandWord;
import Persistence.ScenarioLoader;
import Persistence.Highscore;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import javafx.scene.control.TextArea;

/**
 * The game class is the class which connects all the classes and uses them appropriately
 It uses class Item to create new instances of the type Item, the same goes for Person
 * @author chris
 */
public class Game{
    private Parser parser;
    private TextHandler printer;
    private Room currentRoom; 
    private LogBook logbook;
    private Inventory inventory;
    private boolean isCorrectAccusation;
    private boolean deadByDrink;
    private Point pointSystem;
    private Highscore highScore;
    private String playerName;
    private Time time;
    private boolean timeRanOut;
    private final ArrayList<Room> ROOMS = new ArrayList<>();
    private ArrayList<Person> PERSONS = new ArrayList<>();
    TextArea gameText;

    /**
     * The Game class' constructor
     * Calls the method createRooms(), and initiates the game's parser
     * @param logToParse
     * @param chosenScenarioPath
     */
    public Game(LogBook logToParse, String chosenScenarioPath){
        this.logbook = logToParse;
        this.printer = new TextHandler();
        this.time = new Time(18*60, 14*60);
        ScenarioLoader loader = new ScenarioLoader(chosenScenarioPath, logToParse, this.ROOMS, this.PERSONS, printer, time);
        loader.load();
        currentRoom = this.ROOMS.get(0);
        this.highScore = new Highscore(chosenScenarioPath);
        this.pointSystem = new Point();
        this.deadByDrink = false;
        this.inventory = new Inventory(10);
        this.parser = new Parser();
        
        //this.logbook.addItemDescription(currentRoom.getSpecialItems().get(0),currentRoom.getSpecialItems().get(0).getKeyWords());                           // logbook gui test
        this.logbook.addPersonResponse(this.PERSONS.get(0));     // logbook gui test
    }
    
    /**
     * The game's main method. When the game is played, this method is always running.
     * It's main function is to get the players input in form of commands.
     * Also checks if the player is done playing the game, if yes. The game will stop.
     * Calls processCommand method, for processing the command typed by the player.
     * @throws java.io.FileNotFoundException
     */
    public void play() throws FileNotFoundException{
        //try{
            highScore.createHighscoreFile();
            highScore.readHighscoreTable();
        //} catch (FileNotFoundException e){
            //System.out.println("Error in highscore system.");
        //}
        this.printWelcome();
        
        boolean finished = false;
        while (!finished || timeRanOut) {
            System.out.println("kl: " + time.getTime() + "\n");
            Command command = this.parser.getCommand();
            finished = this.processCommand(command);
            this.whenTimeRunsOut();
            this.generateRandomPersonMovement();
        }
    }
    
    private void afterGameActions() throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
        System.out.println("Press ENTER to continue..");
        input.nextLine();
        if(isCorrectAccusation){
            pointSystem.addPoints(100);
            printer.printWinMessage();
            pointSystem.addPoints(time.PointsIfWin());
        } else if(deadByDrink){
            pointSystem.addPoints(-100);
        } else if(timeRanOut){
            printer.printLoseTimeRanOutMessage();//if lose you get a lose message
            pointSystem.addPoints(-100);//if you lose you lose 100 points
        } else {
            printer.printLoseMessageAcussation();
            pointSystem.addPoints(-100);
        }
        if(highScore.isFinalPointsHigher(pointSystem.getPoints())){
            System.out.println("You have earned enough points to get on the highscore.");
            System.out.println("Please enter your name for the highscore:");
            this.playerName = input.nextLine();
            //try{
                highScore.writeToHighscore(this.playerName, pointSystem.getPoints());
                highScore.writeToFile();
            //} catch(FileNotFoundException fnferr){
            //    System.out.println("Error in highscore System");
            //}
        }
        System.out.println("GAME OVER!");
        //try{
            highScore.readHighscoreTable();
        //} catch(FileNotFoundException fnferr){
        //    System.out.println("Error in highscore System");
        //}
    }

    /**
     * The game's welcome message. printWelcome() is called when game.play(); is called.
     * Displays the general information regarding the game.
     * Command list and a description of the room the player is in at the beginning.
     * @return 
     */
    public ArrayList<String> printWelcome(){
        return printer.printIntroMessage();// + "\n" + currentRoom.getLongDescription + "\nMaybe i should inspect the body for clues.";
//        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * processCommand() is in charge of figuring out what to do with the user's input command.
     * For each of the enum values in the enum class CommandWord, there must be a corresponding if statement in this method.
     * @param command The parameter it takes is a command of type Command. It is checked up against the enum class CommandWord
     * @return false if command is = UNKNOWN, returns false if any of the commands are recognized
     */
    public boolean processCommand(Command command){
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord != null) switch (commandWord) {
            case INSPECT:
                this.inspect(command);
                break;
            case ASK:
                this.interrogate(command);
                break;
            case ACCUSE:
                wantToQuit = this.accuse(command);
                break;
            case TAKE:
                this.takeItem(command);
                break;
            case DROP:
                this.dropItem(command);
                break;
            case DRINK:
                wantToQuit = this.drink(command);
                break;
            default:
                break;
        }
        return wantToQuit;
    }

    /**
     * Prints a brief help description about the game and it's objectives.
     * Prints all the commands available to the player.
     */
    public void printHelp(){
        this.gameText.appendText("asdf"/*printer.printHelp()*/);
    }

    /**
     * 
     * @param command 
     */
    private void goRoom(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Go where?\n");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.println("There is no door!\n");
            return;
        }
        
        if(nextRoom.isLocked() && nextRoom.getlockedFrom().equals(currentRoom)){
            if(inventory.containsItem(nextRoom.getItemToUnlock()) && currentRoom.getShortDescription().equals(nextRoom.getlockedFrom().getShortDescription())){
                nextRoom.setIsLocked(false);
                System.out.println("You unlock " + nextRoom.getShortDescription() + " and enter.");
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
                time.addMinute(currentRoom.getTimeToMove());
                return;
            }
            System.out.println("The door is locked! You need the keys for it.\n");
            return;
        }
        
        if(currentRoom.isTransportRoom()){
            currentRoom = this.ROOMS.get((int) ((Math.random() * this.ROOMS.size())));
            System.out.println(currentRoom.getLongDescription());
            time.addMinute(currentRoom.getTimeToMove());
            return;
        }
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());
        time.addMinute(currentRoom.getTimeToMove());
    }
    
//    public void inventory(){
//        System.out.println("Your inventory contains: " + inventory.getInventory().size() + " items. Total weight is: " + inventory.getInventorySize() + "/" + inventory.getMaxInventorySize() + ".");
//        if(!inventory.getInventory().isEmpty()){
//            for(Item tempItemObject : inventory.getInventory()){
//                
//                System.out.print(tempItemObject.getName() + ": Weight: " + tempItemObject.getWeight() + ".\n");  // Print information about each item in inventory
//            }
//            System.out.println("\n");
//        }
//    }
    
//    public void printLog(Command command){
//        if(command.hasSecondWord()){
//            if("weapons".equals(command.getSecondWord().toLowerCase())){
//                System.out.println("You have found " + logbook.getMurderWeapons().size() + " potential murder weapons.");
//                if(!logbook.getMurderWeapons().isEmpty()){
//                    for(Item tempItemObject : logbook.getMurderWeapons()){
//                        System.out.print(tempItemObject.getName());
//                    }
//                }
//                System.out.println("\n");
//                return;
//            }
//        }
//        //logbook.printAll();
//    }

    public void dropItem(Command command){
        if(command.hasSecondWord()){
            for(Item tempItemObject : inventory.getInventory()){;
                if(tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase())){
                    inventory.removeItem(tempItemObject);
                    currentRoom.addItem(tempItemObject);
                    System.out.println("You have dropped: " + tempItemObject.getName() + "\n");
                    time.addMinute(tempItemObject.getTimeToTake());
                    return;
                }
            }
            for(Item tempItemObject : logbook.getMurderWeapons()){
                if(tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase()) && tempItemObject.isMurderweapon()){
                    System.out.println("You cannot drop potential murder weapons");
                    return;
                }
            }
            System.out.println("You have no item named: " + command.getSecondWord() + "\n");
        } else {
            System.out.println("Drop what?\n");
        }
    }
    
//    private boolean quit(Command command){
//        if(command.hasSecondWord()) {
//            System.out.println("Quit what?\n");
//            return false;
//        } else {
//            return true;
//        }
//    }
    
    private void handleInspection(Item workItem){
        System.out.println(workItem.getMsgOnInspect());
        if(!workItem.isInspected()){
            workItem.setHasBeenInspected(true);
            pointSystem.addPoints(1);
        }
        time.addMinute(workItem.getTimeToInspect());
    }

    private void inspect(Command command) {
        if(command.hasSecondWord()){
            for(Item tempItemObject : currentRoom.getItems()){
                if(tempItemObject.getName().equals(command.getSecondWord())){
                    this.handleInspection(tempItemObject);
                    return;
                }
            }
            for(SpecialItem tempSpecialItemObject : currentRoom.getSpecialItems()){
                if(tempSpecialItemObject.getName().equals(command.getSecondWord())){
                    this.handleInspection((Item)tempSpecialItemObject);
                    if(tempSpecialItemObject.isSecretEntrance()){
                        currentRoom.setExit(tempSpecialItemObject.getSecretExitFirst(), tempSpecialItemObject.getSecretExitSecond());
                    }
                    return;
                }
            }
            for(Item tempItemObject : this.inventory.getInventory()){
                if(tempItemObject.getName().equals(command.getSecondWord())){
                    this.handleInspection(tempItemObject);
                    return;
                }
            }
            
            System.out.println("There is no item named: " + command.getSecondWord() + "\n");
        } else {
            System.out.println("Inspect what item?\n");
        }
    }

    private void takeItem(Command command) {
        if(command.hasSecondWord()){
            Item itemToRemoveFromRoom = null;
            for(Item tempItemObject : currentRoom.getItems()){
                if(tempItemObject.getName().equals(command.getSecondWord())){
                    if(tempItemObject.isActive()){
                        if(inventory.isInventoryFull(tempItemObject.getWeight())){
                            System.out.println(tempItemObject.getMsgOnPickup() + ". It weights: " + tempItemObject.getWeight() + "\n");
                            inventory.addInventory(tempItemObject);
                            if(tempItemObject.isMurderweapon()){
                                logbook.addMurderWeapons(tempItemObject);
                            }
                            itemToRemoveFromRoom = tempItemObject;
                            time.addMinute(tempItemObject.getTimeToTake());
                        } else {
                            System.out.println("Inventory is full! You are carrying to much weight.\n");
                        }
                    } else {
                        System.out.println("You could not pick up the " + tempItemObject.getName() + "\n");
                    }
                }
            }
            if(itemToRemoveFromRoom != null){
                currentRoom.getItems().remove(itemToRemoveFromRoom);
            }
            SpecialItem specialItemToRemoveFromRoom = null;
            for(Item tempSpecialItemObject : currentRoom.getSpecialItems()){
                if(tempSpecialItemObject.getName().equals(command.getSecondWord())){
                    if(tempSpecialItemObject.isActive()){
                        if(inventory.isInventoryFull(tempSpecialItemObject.getWeight())){
                            System.out.println(tempSpecialItemObject.getMsgOnPickup() + ". It weights: " + tempSpecialItemObject.getWeight() + "\n");
                            inventory.addInventory(tempSpecialItemObject);
                            if(tempSpecialItemObject.isMurderweapon()){
                                logbook.addMurderWeapons(tempSpecialItemObject);
                            }
                            itemToRemoveFromRoom = tempSpecialItemObject;
                            time.addMinute(tempSpecialItemObject.getTimeToTake());
                        } else {
                            System.out.println("Inventory is full! You are carrying to much weight.\n");
                        }
                    } else {
                        System.out.println("You could not pick up the " + tempSpecialItemObject.getName() + "\n");
                    }
                }
            }
            if(specialItemToRemoveFromRoom != null){
                currentRoom.getSpecialItems().remove(specialItemToRemoveFromRoom);
            }
        } else {
            System.out.println("Take what item?\n");
        }
    }

    private boolean accuse(Command command) {
        if(!command.hasSecondWord()){
            System.out.println("Accuse who?\n");
            return false;
        }
        if(true/*logbook.askedAllPersons() && logbook.gatheredAllWeapons()*/){
            String whoToAccuse = command.getSecondWord().toLowerCase();
            isCorrectAccusation = false;

            if(!currentRoom.getPersonsInRoom().isEmpty()){
                for(Person tempPersonObject : currentRoom.getPersonsInRoom()){
                    
                   if(tempPersonObject.getAskName().toLowerCase().equals(whoToAccuse) || tempPersonObject.getName().toLowerCase().equals(whoToAccuse)){
                       if(tempPersonObject.isMurder()){
                           isCorrectAccusation = true;
                       }
                       System.out.println(tempPersonObject.getAccusationResponse());
                       return true; // end game
                   }
               }
               System.out.println("There are no persons here named: " + whoToAccuse + "\n");
               return false;
            }
            for(Item tempItemObject : currentRoom.getItems()){
                if(tempItemObject.getName().equals(whoToAccuse)){
                    System.out.println("You cannot accuse an item.\n");
                    return false;
                }
            }
            System.out.println("There is no person here named: " + command.getSecondWord() + "\n");
            return false;
        }
        printer.printAccuseErrorMsg();
        return false;
    }

    private void interrogate(Command command) {
        if(!command.hasSecondWord()){
            System.out.println("Ask who?\n");
            return;
        }
        
        if(currentRoom.getPersonsInRoom().isEmpty() && currentRoom.getRiddlersInRoom().isEmpty()) {
            System.out.println("There are no persons here!\n");
            return;
        }
        for(Person tempPersonObject : currentRoom.getPersonsInRoom()){
            String whoToAsk = command.getSecondWord().toLowerCase();
            if(tempPersonObject.getAskName().toLowerCase().equals(whoToAsk) || tempPersonObject.getName().toLowerCase().equals(whoToAsk)){
                System.out.println(tempPersonObject.getWelcome());
                tempPersonObject.returnQuestions();
                String logBookStringToAdd = "";
                while(tempPersonObject.chosenAnswer != 4) {
                    System.out.println(tempPersonObject.conversation());
                    logBookStringToAdd += tempPersonObject.getPersonKeywordsForQuestion(tempPersonObject.chosenAnswer);
                    if(tempPersonObject.chosenAnswer !=4){
                        time.addMinute(tempPersonObject.getTimeItTakes());//we add 5 minuts for each question we ask the suspect. (- farewell option)
                        tempPersonObject.addToLogBook();
                    }
                }
                System.out.println(currentRoom.getLongDescription());
                if(!tempPersonObject.isAsked()){
                    pointSystem.addPoints(5);
                    tempPersonObject.setHasBeenAsked(true);
                }
            }
        }
        for(PersonWithRiddle tempPersonWithRiddleObject : currentRoom.getRiddlersInRoom()){
            String whoToAsk = command.getSecondWord().toLowerCase();
            if(tempPersonWithRiddleObject.getName().toLowerCase().equals(whoToAsk)){
                tempPersonWithRiddleObject.runRiddle();
            }
        }
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean drink(Command command) {
        if(command.hasSecondWord()){
           for(Item tempItemObject : currentRoom.getItems()){//tjekker items som er i current room, men manger at den ogsÃ¥ tjeker inventory
                if(tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase()) && tempItemObject.isDrinkable()){//tjek if there is an item with the same name as the input and if it is drinkabel
                    if(logbook.isDrinkMax()){//tjek if max drink is true
                        logbook.addDrink();//if max drik true, we add 1 to drinkCount
                    } else{
                        System.out.println("*Drink* I feel kind of wierd maybe i shouldn't have been drinking so much tonight.");//if drink is > maxDrink den we die
                       deadByDrink = true; //end game
                       time.addMinute(tempItemObject.getTimeToDrink());
                       return true;
                    }
                    System.out.println("*Drink* mmmm... that was a good tasting " + command.getSecondWord() + ".\n");//if drink is < maxDrink den we drink
                    time.addMinute(tempItemObject.getTimeToDrink());
                    return false; //continue game
                } else {
                   System.out.println("You can not drink the " + command.getSecondWord() + ".\n");//if the item is not drikabel we can't drink it
                   return false; //continue game
                }
            }
        }
       return false;//continue game
    }
    
    private void whenTimeRunsOut(){
        if (time.getTimeElapsed() >= 14*60){//time runs out at kl: 08:00
            timeRanOut = true; //end game
        }
    }
    
    private void generateRandomPersonMovement(){
        if((int) (Math.random() * 4) == 0){
            Set<String> nearbyRooms = currentRoom.getAllExits().keySet();
            ArrayList<Person> personsWhoCantMove = new ArrayList<>();
            int randomGeneratedPerson;
            Person foundPerson;
            for(String roomString : nearbyRooms){
                personsWhoCantMove.addAll(currentRoom.getExit(roomString).getPersonsInRoom());
            }
            do{
                randomGeneratedPerson = (int) (Math.random() * this.PERSONS.size());
                foundPerson = this.PERSONS.get(randomGeneratedPerson);
            } while (personsWhoCantMove.contains(foundPerson));
            for(Room tempRoom : this.ROOMS){
                if(tempRoom.getPersonsInRoom().contains(foundPerson)){
                    tempRoom.movePerson(foundPerson);
                }
            }
        }
    }
    
    public Highscore getHighscoreRef(){
        return this.highScore;
    }

    public String getTime() {
        return this.time.getTime();
    }
    
    public void setTextAreaRef(TextArea ref){
        this.gameText = ref;
    }
    
    //Method that create new ArrayList with Interactable type, that get all items and persons in the current room
    public ArrayList<Interactable> getObjectsInCurrentRoom() {
        ArrayList<Interactable> allObjects = new ArrayList();
        ArrayList<Interactable> items = (ArrayList<Interactable>)(ArrayList<?>)currentRoom.getItems();
        ArrayList<Interactable> specialItems = (ArrayList<Interactable>) (ArrayList<?>) currentRoom.getSpecialItems();
        ArrayList<Interactable> persons = (ArrayList<Interactable>)(ArrayList<?>)currentRoom.getPersonsInRoom();
        allObjects.addAll(persons);
        allObjects.addAll(specialItems);
        allObjects.addAll(items);
        return allObjects;
    }
    
    public Inventory getInventory() {
        return inventory;
    }
}