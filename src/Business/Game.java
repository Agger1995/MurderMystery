package Business;

import GUI.CommandWord;
import Persistence.ScenarioLoader;
import Persistence.Highscore;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.scene.control.TextArea;

/**
 * The game class is the class which connects all the classes and uses them
 * appropriately It uses class Item to create new instances of the type Item,
 * the same goes for Person
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
    private enum GameState { GAMEOVER, PLAYING; }

    /**
     * The Game class' constructor Calls the method createRooms(), and initiates
     * the game's parser
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
    
    public ArrayList<Room> getRooms(){
        return this.ROOMS;
    }
    
    public int getInventorySize(){
        return this.inventory.getInventorySize();
    }
    
    public int getInventoryMaxSize(){
        return this.inventory.getMaxInventorySize();
    }

    public String getScenarioName() {
        return this.scenarioName;
    }

    public int getPoints() {
        return this.pointSystem.getPoints();
    }

    public String getHighscoreData() {
        return this.highScore.getHighscoreData();
    }

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

    public boolean canGetOnHighscore() {
        return highScore.isFinalPointsHigher(pointSystem.getPoints());
    }

    /**
     * The game's welcome message. printWelcome() is called when game.play(); is
     * called. Displays the general information regarding the game. Command list
     * and a description of the room the player is in at the beginning.
     *
     * @return
     */
    public ArrayList<String> printWelcome() {
        return printer.printIntroMessage();
    }

    /**
     * processCommand() is in charge of figuring out what to do with the user's
     * input command. For each of the enum values in the enum class CommandWord,
     * there must be a corresponding if statement in this method.
     *
     * @param command The parameter it takes is a command of type Command. It is
     * checked up against the enum class CommandWord
     * @return false if command is = UNKNOWN, returns false if any of the
     * commands are recognized
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
     *
     * @param command
     */
    private void goRoom(Command command) {
        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            this.gameText.appendText("There is no door!\n");
            return;
        }

        if (nextRoom.isLocked() && nextRoom.getlockedFrom().equals(currentRoom)) {
            if (inventory.containsItem(nextRoom.getItemToUnlock()) && currentRoom.getShortDescription().equals(nextRoom.getlockedFrom().getShortDescription())) {
                nextRoom.setIsLocked(false);
                this.gameText.appendText("You unlock the " + nextRoom.getShortDescription() + " and enter.\n");
                currentRoom = nextRoom;
                time.addMinute(currentRoom.getTimeToMove());
                return;
            }
            this.gameText.appendText("The door is locked! You need the keys for it.\n");
            return;
        }

        if (currentRoom.isTransportRoom()) {
            Room chosenRoom = this.ROOMS.get((int) ((Math.random() * this.ROOMS.size())));;
            currentRoom = chosenRoom;
            this.gameText.appendText("You enter the " + currentRoom.getShortDescription() + ".\n");
            time.addMinute(currentRoom.getTimeToMove());
            return;
        }
        currentRoom = nextRoom;
        time.addMinute(currentRoom.getTimeToMove());
        this.gameText.appendText("You enter the " + currentRoom.getShortDescription() + ".\n");
    }

    private void dropItem(Command command) {
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

    private void handleInspection(Item workItem) {
        this.gameText.appendText("\n" + workItem.getMsgOnInspect() + "\n");
        if (!workItem.isInspected()) {
            workItem.setHasBeenInspected(true);
            pointSystem.addPoints(1);
        }
        time.addMinute(workItem.getTimeToInspect());
    }

    private void inspect(Command command) {
        for (Item tempItemObject : currentRoom.getItems()) {
            if (tempItemObject.getName().equals(command.getSecondWord())) {
                this.handleInspection(tempItemObject);
                return;
            }
        }
        for (SpecialItem tempSpecialItemObject : currentRoom.getSpecialItems()) {
            if (tempSpecialItemObject.getName().equals(command.getSecondWord())) {
                this.handleInspection((Item) tempSpecialItemObject);
                if (tempSpecialItemObject.isSecretEntrance()) {
                    currentRoom.setExit(tempSpecialItemObject.getSecretExitFirst(), tempSpecialItemObject.getSecretExitSecond());
                    currentRoom.setExitDir(tempSpecialItemObject.getDirectionalKey(), tempSpecialItemObject.getDirectionalExit());
                }
                return;
            }
        }
        for (Item tempItemObject : this.inventory.getInventory()) {
            if (tempItemObject.getName().equals(command.getSecondWord())) {
                this.handleInspection(tempItemObject);
                return;
            }
        }
    }

    private void takeItem(Command command) {
        Item itemToRemoveFromRoom = null;
        for (Item tempItemObject : currentRoom.getItems()) {
            if (tempItemObject.getName().equals(command.getSecondWord())) {
                if (inventory.isInventoryFull(tempItemObject.getWeight())) {
                    this.gameText.appendText("\n" + tempItemObject.getMsgOnPickup() + ".\nIt weights: " + tempItemObject.getWeight() + "\n");
                    if (tempItemObject.isMurderweapon()) {
                        logbook.addMurderWeapons(tempItemObject);
                    } else {
                        inventory.addInventory(tempItemObject);
                    }
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

    private boolean accuse(Command command) {
        String whoToAccuse = command.getSecondWord().toLowerCase();
        isCorrectAccusation = false;

        if (!currentRoom.getPersonsInRoom().isEmpty()) {
            for (Person tempPersonObject : currentRoom.getPersonsInRoom()) {

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

    public String handleRiddle(PersonWithRiddle personInRiddle, int chosenAnswer) {
        return personInRiddle.processAnswer(chosenAnswer);
    }

    public String handleInterrogation(Person personToInterrogate, int chosenQuestion) {
        time.addMinute(personToInterrogate.getTimeItTakes());

        if (!personToInterrogate.isAsked()) {
            pointSystem.addPoints(5);
            personToInterrogate.setHasBeenAsked(true);
        }
        return personToInterrogate.getAnswer(chosenQuestion);
    }

    private boolean drink(Command command) {
        for (Item tempItemObject : inventory.getInventory()) {//tjekker items som er i current room, men manger at den ogsÃ¥ tjeker inventory
            if (tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase())) {//tjek if there is an item with the same name as the input and if it is drinkabel
                if(!tempItemObject.isDrinkable()){
                    this.gameText.appendText("\nYou can not drink the " + command.getSecondWord() + ".\n");//if the item is not drikabel we can't drink it
                    return false; //continue game
                }
                if (logbook.isDrinkMax()) {//tjek if max drink is true
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

    private void whenTimeRunsOut() {
        if (time.getTimeElapsed() >= 14 * 60) {//time runs out at kl: 08:00
            this.state = GameState.GAMEOVER; //end game
        }
    }

    private void generateRandomPersonMovement() {
        if ((int) (Math.random() * 4) == 0) {
            Person foundPerson = this.PERSONS.get((int) (Math.random() * this.PERSONS.size()));
            for (Room tempRoom : this.ROOMS) {
                if (tempRoom.getPersonsInRoom().contains(foundPerson)) {
                    tempRoom.movePerson(foundPerson);
                }
            }
        }
    }

    public Highscore getHighscoreRef() {
        return this.highScore;
    }

    public String getTime() {
        return this.time.getTime();
    }

    public void setTextAreaRef(TextArea ref) {
        this.gameText = ref;
    }

    public ArrayList<Interactable> getObjectsInCurrentRoom() {
        ArrayList<Interactable> allObjects = new ArrayList();
        ArrayList<Interactable> items = (ArrayList<Interactable>) (ArrayList<?>) currentRoom.getItems();
        ArrayList<Interactable> specialItems = (ArrayList<Interactable>) (ArrayList<?>) currentRoom.getSpecialItems();
        ArrayList<Interactable> persons = (ArrayList<Interactable>) (ArrayList<?>) currentRoom.getPersonsInRoom();
        ArrayList<Interactable> personsWithRiddle = (ArrayList<Interactable>) (ArrayList<?>) currentRoom.getRiddlersInRoom();
        allObjects.addAll(persons);
        allObjects.addAll(specialItems);
        allObjects.addAll(items);
        allObjects.addAll(personsWithRiddle);
        return allObjects;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory.getInventory();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void createRiddle(PersonWithRiddle personInDialog) {
        personInDialog.createRiddle();
    }

    private void updateBackend() {
        this.generateRandomPersonMovement();
        this.whenTimeRunsOut();
    }

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

    public boolean addToHighscore(String name) {
        try {
            this.highScore.writeToHighscore(name, this.pointSystem.getPoints());
            this.highScore.writeToFile();
        } catch (FileNotFoundException FNFErr) {
            return false;
        }
        return true;
    }
}
