package worldofzuulsemesterprojekt;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private LogBook logbook;
    protected boolean isCorrectAccusation;
    protected Room ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, bedroom, library, secretRoom;
        

    /**
     * The Game class' constructor
     * Calls the method createRooms(), and initiates the game's parser
     */
    public Game(){
        createRooms();
        createItems();
        createPersons();
        logbook = new LogBook();
        parser = new Parser();
    }


    /**
     * When createRooms is called, it is responsible for creating the rooms
     * It is done with the class Room. 
     * When defining the object instance of class Room, it takes 2 parameters currently
     * Parameter 1 is type String and is a brief description of the Room.
     * Parameter 2 is type String and is a description of the Room's contents.
     * Each Room's exits are later defined in the createRoom's method.
     * Lastly it defines where the player begins his mission.
     */
    private void createRooms(){
        ballRoom = new Room("the ball room");
        toilet = new Room("the toilet");
        kitchen = new Room("the kitchen");
        groundFloorHall = new Room("the ground floor hall");
        dungeon = new Room("the dungeon");
        dungeonHall1 = new Room("the western dungeon hall");
        dungeonHall2 = new Room("the eastern dungeon hall");
        garden = new Room("the garden");
        upstairsHall = new Room("the upstairs hall");
        bedroom = new Room("the bedroom");
        library = new Room("the library");
        secretRoom = new Room("the secret room");
        
        ballRoom.setExit("toilet",toilet);
        ballRoom.setExit("hall",groundFloorHall);
        ballRoom.setExit("kitchen",kitchen);
        
        toilet.setExit("ballroom",ballRoom);
        
        kitchen.setExit("ballroom",ballRoom);
        kitchen.setExit("garden",garden);
        
        garden.setExit("kitchen",kitchen);      //
        
        dungeon.setExit("westhall",dungeonHall1);
        
        dungeonHall1.setExit("easthall",dungeonHall2);
        
        dungeonHall2.setExit("westhall",dungeonHall1);
        dungeonHall2.setExit("garden",garden);
        
        groundFloorHall.setExit("ballroom",ballRoom);
        groundFloorHall.setExit("upstairs",upstairsHall);
        
        upstairsHall.setExit("downstairs",groundFloorHall);
        upstairsHall.setExit("bedroom",bedroom);
        upstairsHall.setExit("library",library);
        
        bedroom.setExit("hall",upstairsHall);
        bedroom.setExit("library",library);
        
        library.setExit("hall",upstairsHall);
        
        secretRoom.setExit("exit",null);
        
        currentRoom = ballRoom;
    }
    
    private void createItems() {
        ballRoom.addItem(false, "none", "This is a carpet, upon inspection you find a secret trapdoor underneath", false, "carpet", "dusty, revealed trapdoor");
        ballRoom.addItem(false,"none", "This is Mr. Pheins daughters body", false, "body", "");
        ballRoom.addItem(true, "none", "This is a drink, you feel a bit thirsty and take a sip", false, "drink", "");
        bedroom.addItem(true, "You pick up the bloody knife", "A bloody knife, quite possibly a murder weapon", true, "bloody knife", "");
        
        kitchen.addItem(true, "You pick up the rat poison", "Odd to find an empty bottle of rat poison in the kitchen, \n"
                                                            + "the powdery substance matches the one found by the body. \n"
                                                            + "The amount is rather small to cause the murder. \n"
                                                            + "*Sniff* ratpoison, I can always tell, ratpoison has a very distinct smell, \n"
                                                            + "only alfred have been in the kitchen all night. \n"
                                                            + "This could be a good idea to keep this in mind though. ", true, "rat poison", "powder, resemble powder on knife, kitchen.");
        
        garden.addItem(true, "You pick up a set of rusty keys", "These are some old rusty looking keys", false, "keys", "rusty keys, says 'kitchen', garden");//
        
        library.addItem(false, "none", "This is a bookshelf", false, "bookshelf", "revealed secret room, library");
    }

    private void createPersons() {
        
    }

    /**
     * The game's main method. When the game is played, this method is always running.
     * It's main function is to get the players input in form of commands.
     * Also checks if the player is done playing the game, if yes. The game will stop.
     * Calls processCommand method, for processing the command typed by the player.
     */
    public void play(){            
        printWelcome();
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing our murder mystery game. Hope you enjoyed it.");
    }

    /**
     * The game's welcome message. printWelcome() is called when game.play(); is called.
     * Displays the general information regarding the game.
     * Command list and a description of the room the player is in at the beginning.
     */
    private void printWelcome(){
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Welcome to Murder Mansion!");
        System.out.println();
        System.out.println("You’re a snarky detective, who discovered a dead body during a party after a couple of drinks.");
        System.out.println("Your job as a now slightly intoxicated detective is to find the murderer, and what weapon he/she used and not die in the process.");
        System.out.println("Good luck and try your best, now for the introduction!");
        System.out.println("Press ENTER to hear the story.");
        input.nextLine();
        System.out.println("I wish I wasn’t at this party…");
        System.out.println();
        System.out.println("Working with these people through years, lousy work and cold coffee");
        System.out.println("you’d imagine their parties wouldn’t be a blast. Well that’s because they aren’t.");
        System.out.println("Press ENTER to continue the story.");
        input.nextLine();
        System.out.println("*DRINK* as the whisky ran down my throat I felt better but more is necessary to cope with this.");
        System.out.println("*DRINK* Better, but damn I need more.");
        System.out.println("*DRINK* Finally, as the whisky ran its course I got a sudden urge to go to the bathroom, finally giving me an excuse to leave this party.");
        System.out.println("Press ENTER to continue the story.");
        input.nextLine();
        System.out.println("Coming back after the quick relief, I was met by an empty room, growing cold and dark.");
        System.out.println("Everyone was gone, and a body, smeared in blood, had appeared in the middle the table.");
        System.out.println("As if it couldn’t get any worse, what the hell are am I going to do now?!?");
        System.out.println();
        System.out.println("Let the mystey begin!");
        System.out.println("Press ENTER to begin the game!");
        input.nextLine();
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * processCommand() is in charge of figuring out what to do with the user's input command.
     * For each of the enum values in the enum class CommandWord, there must be a corresponding if statement in this method.
     * @param command The parameter it takes is a command of type Command. It is checked up against the enum class CommandWord
     * @return false if command is = UNKNOWN, returns false if any of the commands are recognized
     */
    private boolean processCommand(Command command){
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("Unknown command!");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.INSPECT){
            inspect(command);
        } else if (commandWord == CommandWord.ASK){
            interrogate(command);
        } else if (commandWord == CommandWord.ACCUSE){
            wantToQuit = accuse(command);
        } else if (commandWord == CommandWord.LOGBOOK){
            printLog(command);
        } else if (commandWord == CommandWord.TAKE){
            takeItem(command);
        } else if (commandWord == CommandWord.DROP){
            dropItem(command);
        }
        return wantToQuit;
    }

    /**
     * Prints a brief help description about the game and it's objectives.
     * Prints all the commands available to the player.
     */
    private void printHelp(){
        System.out.println("You are the detective at a party.");
        System.out.println("Your job is to figure out who comitted the murder");
        System.out.println("Throughout the game you can interact with items and interrogate persons.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /**
     * 
     * @param command 
     */
    private void goRoom(Command command){
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);
        
        if("the garden".contentEquals(currentRoom.getShortDescription()) && "kitchen".contentEquals(direction)){
            ArrayList<Items> inv = logbook.getInventory();
            for(Items item : inv){
                if(item.getName().equals("keys")){
                    System.out.println("You unlock the kitchen door and enter.");
                    currentRoom = nextRoom;
                    System.out.println(currentRoom.getLongDescription());
                    return;
                }
            }
            System.out.println("The door is locked! You need the keys for it.");
            return;
        }
        if("the secret room".contentEquals(currentRoom.getShortDescription()) && "exit".contentEquals(direction)){
            Room[] roomArray = {ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, library, bedroom, secretRoom};
            
            currentRoom = roomArray[(int)(Math.random() * 12)];
            
            System.out.println(currentRoom.getLongDescription());
            return;
        }
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }

    }
    
    public void printLog(Command command){
        if(command.hasSecondWord()){
            if("inventory".equals(command.getSecondWord().toLowerCase())){
                System.out.println("Your inventory contains: " + logbook.getInventory().size() + " items.");
                if(!logbook.getInventory().isEmpty()){
                    for(Items item : logbook.getInventory()){
                        System.out.println(item.getName());  // Print information about each item in inventory
                    }
                }
                return;
            } else if("weapons".equals(command.getSecondWord().toLowerCase())){
                System.out.println("You have found " + logbook.getMurderWeapons().size() + " potential murder weapons.");
                if(!logbook.getMurderWeapons().isEmpty()){
                    for(Items weapon : logbook.getMurderWeapons()){
                        System.out.println(weapon.getName());
                    }
                }
                return;
            } else if("suspects".equals(command.getSecondWord().toLowerCase())){
                System.out.println("You have found " + logbook.getSuspects().size() + " suspects.");
                if(!logbook.getSuspects().isEmpty()){
                    for(Person person : logbook.getSuspects()){
                        System.out.println(person.getName());
                    }
                }
                return;
            }
        }
        
        logbook.printAll();
    }

    public void dropItem(Command command){
        if(command.hasSecondWord()){
            for(Items item : logbook.getInventory()){
                if(item.getName().toLowerCase().equals(command.getSecondWord().toLowerCase())){
                    logbook.removeItem(item);
                    currentRoom.addItem(item.isActive(),item.getUse(),item.getDescription(),item.isMurderweapon(),item.getName(), item.getKeyWords());
                    System.out.println("You have dropped: " + item.getName());
                    return;
                }
            }
            System.out.println("You have no item named: " + command.getSecondWord());
        } else {
            System.out.println("Drop what?");
        }
    }
    
    private boolean quit(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    private void inspect(Command command) {
        if(command.hasSecondWord()){
            ArrayList<Items> itemsInCurrentRoom = currentRoom.getItems();
            for(Items item : itemsInCurrentRoom){
                if(item.getName().equals(command.getSecondWord())){
                    System.out.println(item.getDescription());
                    logbook.addItemDescription(item);
                    if("carpet".equals(item.getName())){
                        ballRoom.setExit("trapdoor",dungeon);
                    } else if("bookshelf".equals(item.getName())){
                        library.setExit("bookshelf",secretRoom);
                    }
                    return;
                }
            }
            System.out.println("There is no item named: " + command.getSecondWord());
        } else {
            System.out.println("Inspect what item?");
        }
    }

    private void takeItem(Command command) {
        if(command.hasSecondWord()){
            ArrayList<Items> itemsInCurrentRoom = currentRoom.getItems();
            ArrayList<Items> toRemoveFromOriginalList = new ArrayList<>();
            for(Items item : itemsInCurrentRoom){
                if(item.getName().equals(command.getSecondWord())){
                    if(item.isActive()){
                        System.out.println(item.getUse());
                        logbook.addInventory(item);
                        toRemoveFromOriginalList.add(item);
                    } else {
                        System.out.println("You could not pick up the " + item.getName());
                    }
                }
            }
            currentRoom.getItems().removeAll(toRemoveFromOriginalList);
        } else {
            System.out.println("Take what item?");
        }
    }

    private boolean accuse(Command command) {
        if(!command.hasSecondWord()){
            System.out.println("Accuse who?");
            return false;
        }
        String whoToAccuse = command.getSecondWord();
        isCorrectAccusation = false;
        
        if(!currentRoom.getPersonsInRoom().isEmpty()){
            for(Person person : currentRoom.getPersonsInRoom()){
               System.out.println(whoToAccuse + " : " + person.getName());
               if(!whoToAccuse.equals(person.getName())){
                   return false;
               }
           }
           return true;   
        }
        System.out.println("There is no person here named: " + command.getSecondWord());
        return false;
    }

    private void interrogate(Command command) {
        if(!command.hasSecondWord()){
            System.out.println("Ask who?");
            return;
        }
        
    }
}