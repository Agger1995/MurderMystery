package worldofzuulsemesterprojekt;

import java.util.Scanner;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    protected Room ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, bedroom, library, secretRoom;
        

    /**
     * The Game class' constructor
     * Calls the method createRooms(), and initiates the game's parser
     */
    public Game()
    {
        createRooms();
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
        //Room ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, bedroom, library, secretRoom;

        ballRoom = new Room("the ball room","objects to place in room");
        toilet = new Room("the toilet","objects to place in room");
        kitchen = new Room("the kitchen","objects to place in room");
        groundFloorHall = new Room("the ground floor hall","objects to place in room");
        dungeon = new Room("the dungeon","objects to place in room");
        dungeonHall1 = new Room("the western dungeon hall","objects to place in room");
        dungeonHall2 = new Room("the eastern dungeon hall","objects to place in room");
        garden = new Room("the garden","objects to place in room");
        upstairsHall = new Room("the upstairs hall","objects to place in room");
        bedroom = new Room("the bedroom","objects to place in room");
        library = new Room("the library","objects to place in room");
        secretRoom = new Room("the secret room","objects to place in room");
        
        ballRoom.setExit("toilet",toilet);
        ballRoom.setExit("hall",groundFloorHall);
        ballRoom.setExit("kitchen",kitchen);
        //ballRoom.setExit("rug",dungeon);        //
        
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
        library.setExit("bookcase",secretRoom);     //
        
        secretRoom.setExit("exit",null);
        
        currentRoom = ballRoom;
    }

    /**
     * The game's main method. When the game is played, this method is always running.
     * It's main function is to get the players input in form of commands.
     * Also checks if the player is done playing the game, if yes. The game will stop.
     * Calls processCommand method, for processing the command typed by the player.
     */
    public void play(){            
        printWelcome();

        int i = 0;
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            /*
            i++;
            if(i == 5){
                ballRoom.setExit("rug",dungeon);
            }
            */
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
        System.out.println("Welcome to Mystery Murder mansion!");
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
        } else if (commandWord == CommandWord.CHECK){
            checkRoom(command);
        }
        return wantToQuit;
    }

    /**
     * Prints a brief help description about the game and it's objectives.
     * Prints all the commands available to the player.
     */
    private void printHelp(){
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
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

        //System.out.println(currentRoom + " " + direction);
        
        if("the secret room".contentEquals(currentRoom.getShortDescription()) && "exit".contentEquals(direction)){
            Room[] roomArray = {ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, library, bedroom, secretRoom};
            
            currentRoom = roomArray[(int)(Math.random()*11) + 1];
            
            System.out.println(currentRoom.getLongDescription());
        } else {
            if (nextRoom == null) {
                System.out.println("There is no door!");
            } else {
                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());
            }
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

    private void checkRoom(Command command) {       // Custom method, still to add is check for contents of room 
        if(!command.hasSecondWord()) {
            System.out.println("Check what?");
            return;
        }
        
        String direction = command.getSecondWord();
        
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null){
            System.out.println("There is nothing here to check!");
        } else {
            System.out.println("The room to the " + direction + " is " + nextRoom.getShortDescription() + ". In the room you can clearly see " + nextRoom.getContents() + ".");
        }
        
    }
}