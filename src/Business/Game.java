package Business;

import GUI.CommandWord;
import Persistence.ScenarioLoader;
import Persistence.Highscore;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.TextArea;

/**
 * The game class is the class which connects all the classes and uses them appropriately It uses class Item to create new instances of the type Item, the same goes for Person
 *
 * @author chris
 */
public class Game {

    private TextHandler printer;
    private Room currentRoom;
    private LogBook logbook;
    private Inventory inventory;
    private boolean isCorrectAccusation;
    private boolean deadByDrink;
    private Point pointSystem;
    private Highscore highScore;
    private Time time;
    private boolean timeRanOut;
    private final ArrayList<Room> ROOMS = new ArrayList<>();
    private final ArrayList<Person> PERSONS = new ArrayList<>();
    private String scenarioName;
    private TextArea gameText;
    private GameState state;

    private enum GameState {
        GAMEOVER, PLAYING;
    }

    /**
     * The Game class' constructor Calls the method createRooms(), and initiates the game's parser
     *
     * @param logToParse
     * @param chosenScenarioPath
     * @param riddleRef
     */
    public Game(LogBook logToParse, String chosenScenarioPath, Riddle riddleRef) {
        this.scenarioName = chosenScenarioPath.split("/")[1];
        this.logbook = logToParse;
        this.printer = new TextHandler();
        this.time = new Time(18 * 60, 14 * 60);
        ScenarioLoader loader = new ScenarioLoader(chosenScenarioPath, logToParse, this.ROOMS, this.PERSONS, printer, time, riddleRef);
        loader.load();
        this.currentRoom = this.ROOMS.get(0);
        this.highScore = new Highscore(chosenScenarioPath);
        this.pointSystem = new Point();
        this.deadByDrink = false;
        this.inventory = new Inventory(10);
        this.state = GameState.PLAYING;
        this.highScore.readHighscoreTable();
    }

    /**
     * Returns an ArrayList of the rooms in the game
     *
     * @return ArrayList of rooms.
     */
    public ArrayList<Room> getRooms() {
        return this.ROOMS;
    }

    /**
     * Returns the inventory size of the player.
     *
     * @return int, Inventory size
     */
    public int getInventorySize() {
        return this.inventory.getInventorySize();
    }

    /**
     * Returns the max size of the inventory.
     *
     * @return int, max inventory size.
     */
    public int getInventoryMaxSize() {
        return this.inventory.getMaxInventorySize();
    }

    /**
     * Returns name of scenario
     *
     * @return String, scenario name
     */
    public String getScenarioName() {
        return this.scenarioName;
    }

    /**
     * Returns an integer of the total points
     *
     * @return int, points
     */
    public int getPoints() {
        return this.pointSystem.getPoints();
    }

    /**
     * Returns a string of the highscore data.
     *
     * @return String, highscore
     */
    public String getHighscoreData() {
        return this.highScore.getHighscoreData();
    }

    /**
     * Adds points to the total amount of points.
     *
     * @throws FileNotFoundException
     */
    public void addPoints() throws FileNotFoundException {
        if (isCorrectAccusation) {
            pointSystem.addPoints(100);
            pointSystem.addPoints(time.PointsIfWin());
        } else if (deadByDrink) {
            pointSystem.addPoints(-100);
        } else if (timeRanOut) {
            pointSystem.addPoints(-100);
        } else {
            pointSystem.addPoints(-100);
        }
    }

    /**
     * Returns the end game message chosen by the way you win or lose.
     *
     * @param key
     * @return String, win/lose message
     */
    public String getEndGameMsg(String key) {
        switch (key) {
            case "correctAccuse":
                return this.printer.printWinMessage();
            case "time":
                return this.printer.printLoseTimeRanOutMessage();
            case "wrongAccuse":
                return this.printer.printLoseMessageAcussation();

            default:
                break;
        }
        return null;
    }

    /**
     * Returns whether or not you can be on the highscore.
     *
     * @return boolean, true = highscore
     */
    public boolean canGetOnHighscore() {
        return highScore.isFinalPointsHigher(pointSystem.getPoints());
    }

    /**
     * The game's welcome message. printWelcome() is called when game.play(); is called. Displays the general information regarding the game. Command list and a description of the room the player is
     * in at the beginning.
     *
     * @return
     */
    public ArrayList<String> printWelcome() {
        return printer.printIntroMessage();
    }

    /**
     * processCommand() is in charge of figuring out what to do with the user's input command. For each of the enum values in the enum class CommandWord, there must be a corresponding if statement in
     * this method.
     *
     * @param command The parameter it takes is a command of type Command. It is checked up against the enum class CommandWord
     * @return false if command is = UNKNOWN, returns false if any of the commands are recognized
     */
    public boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord != null) {
            switch (commandWord) {
                case INSPECT:
                    this.inspect(command);
                    break;
                case GO:
                    this.goRoom(command);
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
        }
        this.updateBackend();
        if (this.state == GameState.GAMEOVER) {
            return true;
        }
        return wantToQuit;
    }

    /**
     * Goes to the room specified in the command, if accessable.
     *
     * @param command
     */
    private void goRoom(Command command) {
        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);
        //Checks if the room is 
        if (nextRoom == null) {
            this.gameText.appendText("There is no door!\n");
            return;
        }
        //Checks the rooms that the player is entering is locked, if the player has the key and adds time to the action. 
        if (nextRoom.isLocked() && nextRoom.getlockedFrom().equals(currentRoom)) {
            if (inventory.containsItem(nextRoom.getItemToUnlock()) && currentRoom.getShortDescription().equals(nextRoom.getlockedFrom().getShortDescription())) {
                nextRoom.setIsLocked(false);
                //Message appended to the gameText Textarea. 
                this.gameText.appendText("You unlock the " + nextRoom.getShortDescription() + " and enter.\n");
                currentRoom = nextRoom;
                time.addMinute(currentRoom.getTimeToMove());
                return;
            }

            this.gameText.appendText("The door is locked! You need the keys for it.\n");
            return;
        }
        //Checks if the current room is the "teleportation room", then teleports the player to a random room via a randomizer. 
        if (currentRoom.isTransportRoom()) {
            Room chosenRoom = this.ROOMS.get((int) ((Math.random() * this.ROOMS.size())));;
            currentRoom = chosenRoom;
            this.gameText.appendText("You enter the " + currentRoom.getShortDescription() + ".\n");
            //Adds the amount of the time used by entering the teleportation room.
            time.addMinute(currentRoom.getTimeToMove());
            return;
        }
        //Sets the current room to be equal to the next room the player is entering.
        currentRoom = nextRoom;
        time.addMinute(currentRoom.getTimeToMove());
        this.gameText.appendText("You enter the " + currentRoom.getShortDescription() + ".\n");
    }

    /**
     * Drops item found in the Command command
     *
     * @param command
     */
    private void dropItem(Command command) {
        //Iterates through the inventory of items and checks if they're equal to the items in the inventory.
        for (Item tempItemObject : inventory.getInventory()) {;
            if (tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase())) {
                inventory.removeItem(tempItemObject);
                currentRoom.addItem(tempItemObject);
                this.gameText.appendText("\nYou have dropped: " + tempItemObject.getName() + "\n");
                time.addMinute(tempItemObject.getTimeToTake());
                return;
            }
        }
    }

    /**
     * Handles the inspections, checks if the item is inspected and adds points to the inspection.
     *
     * @param workItem
     */
    private void handleInspection(Item workItem) {
        this.gameText.appendText("\n" + workItem.getMsgOnInspect() + "\n");
        if (!workItem.isInspected()) {
            workItem.setHasBeenInspected(true);
            pointSystem.addPoints(1);
        }
        time.addMinute(workItem.getTimeToInspect());
    }

    /**
     * Inspects the item found in the command.
     *
     * @param command
     */
    private void inspect(Command command) {
        //Iterates through the items in the current room.
        for (Item tempItemObject : currentRoom.getItems()) {
            //Checks if the item in currentRoom is equal to the players item.
            if (tempItemObject.getName().equals(command.getSecondWord())) {
                //Uses the handleInspection method, setting the item to "inspected".
                this.handleInspection(tempItemObject);
                return;
            }
        }
        //Iterating through the special items in the current room.
        for (SpecialItem tempSpecialItemObject : currentRoom.getSpecialItems()) {
            if (tempSpecialItemObject.getName().equals(command.getSecondWord())) {
                this.handleInspection((Item) tempSpecialItemObject);
                //Utilizes the specialItem attributes, to check isSectretEntrace meaning "is the door locked" (in this case).
                if (tempSpecialItemObject.isSecretEntrance()) {
                    //Sets the exits, if the player has picked up the specialItem (key in this case).    
                    currentRoom.setExit(tempSpecialItemObject.getSecretExitFirst(), tempSpecialItemObject.getSecretExitSecond());
                    currentRoom.setExitDir(tempSpecialItemObject.getDirectionalKey(), tempSpecialItemObject.getDirectionalExit());
                }
                return;
            }
        }
        //Iterating through the inventory for items. 
        for (Item tempItemObject : this.inventory.getInventory()) {
            if (tempItemObject.getName().equals(command.getSecondWord())) {
                //Checks if the item is inspected.    
                this.handleInspection(tempItemObject);
                return;
            }
        }
    }

    /**
     * Takes the item specified in the command.
     *
     * @param command
     */
    private void takeItem(Command command) {
        //Runs a series of checks to check if the inventory is full.
        //It then adds the item to the inventory, it the inventory is not full and the weight is not too much.
        Item itemToRemoveFromRoom = null;
        for (Item tempItemObject : currentRoom.getItems()) {
            if (tempItemObject.getName().equals(command.getSecondWord())) {
                //Checks if the inventory is full by getting the weight of the item.
                if (inventory.isInventoryFull(tempItemObject.getWeight())) {
                    this.gameText.appendText("\n" + tempItemObject.getMsgOnPickup() + ".\nIt weights: " + tempItemObject.getWeight() + "\n");
                    //Adds the item to the logbook if it's a murder weapon, puts the item in the inventory if it's not.
                    if (tempItemObject.isMurderweapon()) {
                        logbook.addMurderWeapons(tempItemObject);
                    } else {
                        inventory.addInventory(tempItemObject);
                    }
                    //Removes the item, once it's been added to the inventory or the logbook.
                    itemToRemoveFromRoom = tempItemObject;
                    time.addMinute(tempItemObject.getTimeToTake());
                } else {
                    this.gameText.appendText("\nInventory is full! You are carrying to much weight.\n");
                }
            }
        }
        if (itemToRemoveFromRoom != null) {
            currentRoom.getItems().remove(itemToRemoveFromRoom);
        }
        //Iterates through specialItems and checks the weight and if the inventory is full.
        SpecialItem specialItemToRemoveFromRoom = null;
        for (Item tempSpecialItemObject : currentRoom.getSpecialItems()) {
            if (tempSpecialItemObject.getName().equals(command.getSecondWord())) {
                if (inventory.isInventoryFull(tempSpecialItemObject.getWeight())) {
                    this.gameText.appendText(tempSpecialItemObject.getMsgOnPickup() + ". It weights: " + tempSpecialItemObject.getWeight() + "\n");
                    inventory.addInventory(tempSpecialItemObject);
                    if (tempSpecialItemObject.isMurderweapon()) {
                        logbook.addMurderWeapons(tempSpecialItemObject);
                    }
                    itemToRemoveFromRoom = tempSpecialItemObject;
                    time.addMinute(tempSpecialItemObject.getTimeToTake());
                } else {
                    this.gameText.appendText("\nInventory is full! You are carrying to much weight.\n");
                }
            }
        }
        if (specialItemToRemoveFromRoom != null) {
            currentRoom.getSpecialItems().remove(specialItemToRemoveFromRoom);
        }
    }

    /**
     * Accuses the person found in the command.
     *
     * @param command
     * @return boolean, true if rightly accused.
     */
    private boolean accuse(Command command) {
        String whoToAccuse = command.getSecondWord().toLowerCase();
        isCorrectAccusation = false;
        //Iterates through the persons in currentRoom.
        if (!currentRoom.getPersonsInRoom().isEmpty()) {
            for (Person tempPersonObject : currentRoom.getPersonsInRoom()) {
                //Checks if the person accused is the murdere. If he/she's not, the game ends.
                if (tempPersonObject.getAskName().toLowerCase().equals(whoToAccuse) || tempPersonObject.getName().toLowerCase().equals(whoToAccuse)) {
                    if (tempPersonObject.isMurder()) {
                        isCorrectAccusation = true;
                    }
                    this.gameText.appendText(tempPersonObject.getAccusationResponse());
                    this.state = GameState.GAMEOVER;
                    return true;
                }
            }
        }
        this.gameText.appendText("\n" + printer.printAccuseErrorMsg() + "\n");
        return false;
    }

    /**
     * A method which returns the check on if the riddle answer was correct and adds or subtracts time accordingly.
     *
     * @param personInRiddle
     * @param chosenAnswer
     * @return String, a response of the chosen answer to the question
     */
    public String handleRiddle(PersonWithRiddle personInRiddle, int chosenAnswer) {
        return personInRiddle.processAnswer(chosenAnswer);
    }

    /**
     * Handles the interrogation of a person.
     *
     * @param personToInterrogate
     * @param chosenQuestion
     * @return String, answer of a given question.
     */
    public String handleInterrogation(Person personToInterrogate, int chosenQuestion) {
        time.addMinute(personToInterrogate.getTimeItTakes());
        //Checks if the person that is asked and adds points accordingly.
        if (!personToInterrogate.isAsked()) {
            pointSystem.addPoints(5);
            //This is an important set, since it allows the answers to be displayed after the questions have been asked.    
            personToInterrogate.setHasBeenAsked(true);
        }
        return personToInterrogate.getAnswer(chosenQuestion);
    }

    /**
     * Drinks from an item, if drinkable.
     *
     * @param command
     * @return boolean, false if continue game. true = drunk.
     */
    private boolean drink(Command command) {
        for (Item tempItemObject : inventory.getInventory()) {//Iterates through the items in the inventory.
            if (tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase())) {//tjek if there is an item with the same name as the input and if it is drinkabel
                if (!tempItemObject.isDrinkable()) {
                    this.gameText.appendText("\nYou can not drink the " + command.getSecondWord() + ".\n");//if the item is not drikabel we can't drink it
                    return false; //continue game
                }
                if (logbook.isDrinkMax()) {//check if max drink is true
                    logbook.addDrink();//if max drik true, we add 1 to drinkCount
                } else {
                    this.gameText.appendText("\n*Drink* I feel kind of wierd maybe i shouldn't have been drinking so much tonight.\n");//if drink is > maxDrink den we die
                    deadByDrink = true; //end game
                    this.state = GameState.GAMEOVER;
                    time.addMinute(tempItemObject.getTimeToDrink());
                    return true;
                }
                this.gameText.appendText("\n*Drink* mmmm... that was a good tasting " + command.getSecondWord() + ".\n");//if drink is < maxDrink den we drink
                time.addMinute(tempItemObject.getTimeToDrink());
                return false; //continue game
            }
        }
        return false;//continue game
    }

    /**
     * Checks whether or not there is time left. If there isn't any time left - end the game.
     */
    private void whenTimeRunsOut() {
        if (time.getTimeElapsed() >= 14 * 60) {//time runs out at kl: 08:00
            this.state = GameState.GAMEOVER; //end game
        }
    }

    /**
     * Generates movement for the persons placed in the game. For every "turn" there is a 25% chance, that the person moves.
     */
    private void generateRandomPersonMovement() {
        //Checks 
        if ((int) (Math.random() * 4) == 0) {
            Person foundPerson = this.PERSONS.get((int) (Math.random() * this.PERSONS.size()));
            for (Room tempRoom : this.ROOMS) {
                if (tempRoom.getPersonsInRoom().contains(foundPerson)) {
                    tempRoom.movePerson(foundPerson);
                }
            }
        }
    }

    /**
     * Get highscore reference
     */
    public Highscore getHighscoreRef() {
        return this.highScore;
    }

    /**
     * Get current time
     */
    public String getTime() {
        return this.time.getTime();
    }

    /**
     * Sets the reference to the TextArea found in the GUI package.
     *
     * @param ref TextArea, to be written to.
     */
    public void setTextAreaRef(TextArea ref) {
        this.gameText = ref;
    }

    /**
     * Returns an ArrayList of all of the interactable objects.
     *
     * @return ArrayList of interactable objects in a given room.
     */
    public ArrayList<Interactable> getObjectsInCurrentRoom() {
        ArrayList<Interactable> allObjects = new ArrayList();
        allObjects.addAll((ArrayList<? extends Interactable>) currentRoom.getPersonsInRoom());
        allObjects.addAll((ArrayList<? extends Interactable>) currentRoom.getSpecialItems());
        allObjects.addAll((ArrayList<? extends Interactable>) currentRoom.getItems());
        allObjects.addAll((ArrayList<? extends Interactable>) currentRoom.getRiddlersInRoom());
        return allObjects;
    }

    /**
     * Returns an ArrayList representing the items in the inventory.
     *
     * @return ArrayList of inventory.
     */
    public ArrayList<Item> getInventory() {
        return this.inventory.getInventory();
    }

    /**
     * Gets the room you're in.
     *
     * @return Room, currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Creates a riddle for a riddle person.
     *
     * @param personInDialog
     */
    public void createRiddle(PersonWithRiddle personInDialog) {
        personInDialog.createRiddle();
    }

    /**
     * This method as named, updates the backend part of the game-engine, so it's on par with the GUI.
     */
    private void updateBackend() {
        this.generateRandomPersonMovement();
        this.whenTimeRunsOut();
    }

    /**
     * A method that checks the cause of the game over that the over methods initialize.
     *
     * @return String, cause of the Game Over.
     */
    public String getGameOverCause() {
        if (this.deadByDrink) {
            return "drink";
        } else if (this.timeRanOut) {
            return "time";
        } else if (this.isCorrectAccusation) {
            return "correctAccuse";
        } else if (!this.isCorrectAccusation) {
            return "wrongAccuse";
        }
        return null;
    }

    /**
     * Adds the player to the highscore table.
     *
     * @param name
     * @return true if succesful - false if not.
     */
    public boolean addToHighscore(String name) {
        //Writes to the high score table.
        try {
            this.highScore.writeToHighscore(name, this.pointSystem.getPoints());
            this.highScore.writeToFile();
        } catch (FileNotFoundException FNFErr) {
            return false;
        }
        return true;
    }
}
