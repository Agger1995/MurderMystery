package worldofzuulsemesterprojekt;

import java.util.Scanner;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private LogBook logbook;
    protected boolean isCorrectAccusation;
    private Room ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, bedroom, library, secretRoom;
    private Person tyrion, phein, alfred, veronica, stephanie;
        

    /**
     * The Game class' constructor
     * Calls the method createRooms(), and initiates the game's parser
     */
    public Game(){
        this.createRooms();
        this.createItems();
        this.createPersons();
        this.logbook = new LogBook();
        this.parser = new Parser();
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
        
        currentRoom = toilet;
    }
    
    private void createItems() {
        ballRoom.addItem(false, "UNUSED", "Hmm, that’s a ugly carpet compared to the rest of this mansion. \n"
                                        + "*Wind gushing from the open windows* suddenly a trapdoor \n"
                                        + "became visible as the wind grabbed a bit of the carpet.\n", false, "carpet", "Dusty, revealed trapdoor");
        
        toilet.addItem(false, "UNUSED", "Such a shame, Agnes known for her beauty resembling from her \n"
                                        + "mother, now had a face resembling massacred raw beef with \n"
                                        + "hair. A bit of dust is around her face, could be anything from \n"
                                        + "flour to baby powder *sniff* hmm fits the case, it has a very distinctive smell.\n", false, "body", "Poisoned knife caused it, Toilet.");
        
        upstairsHall.addItem(true, "You pick up the whiskey\n", "A bottle of whiskey on the floor in hallway on \n"
                                                        + "the 1st floor, who could be leaving such a beauty there? *drink* ahh… \n"
                                                        + "that’s the stuff. Oh, better get back to work, otherwise \n"
                                                        + "I’ll be spending all my time here.\n", false, "whiskey", "very strong taste, but good");
        
        bedroom.addItem(true, "You pick up the bloody knife\n", "A knife wrapped in a handkerchief soaked in blood... \n"
                                                            + "in Mr. Phine’s room... the knife seems to also have some powder on it. \n"
                                                            + "It looks like the same powder as the one on the body. \n"
                                                            + "*sniff*, yeah this is ratpoison. Odd to find something like \n"
                                                            + "that in his room, I wonder who dropped this here.\n", true, "bloody knife", "bloody and powdered, suspicious, bedroom.");
        
        
        kitchen.addItem(false, "UNUSED", "Hmm, an empty knifeholder in the kitchen. Wonder where the knife is?\n",false, "empty knifeholder", "Knife-holder with missing knife, Kitchen");
        kitchen.addItem(true, "You pick up the rat poison\n", "Odd to find an empty bottle of rat poison in the kitchen, \n"
                                                            + "the powdery substance matches the one found by the body. \n"
                                                            + "The amount is rather small to cause the murder. \n"
                                                            + "*Sniff* ratpoison, I can always tell, ratpoison has a very distinct smell, \n"
                                                            + "only alfred have been in the kitchen all night. \n"
                                                            + "This could be a good idea to keep this in mind though.\n", true, "rat poison", "powder, resemble powder on knife, kitchen.");
        
        garden.addItem(true, "You pick up a set of rusty keys\n", "These are some old rusty looking keys\n", false, "keys", "rusty keys, says 'kitchen', garden");//
        garden.addItem(true, "You pick up a golf club\n", "A golf club in the garden, doesn’t seems suspicious so far. There is \n"
                                                        + "no blood on it, and it seems to be clean and shiny, yet it \n"
                                                        + "has been left alone in the garden.\n", true, "golf club", "dirty with grass, no blood, garden.");
        upstairsHall.addItem(true, "You pick up a rope\n", "A rope with a tied knot, not usually something you’d find in a hallway \n"
                                                        + "on the 2nd hall. Perhaps someone were trying to off themselves. \n"
                                                        + "Interesting case, but irrelevant right now. We gotta find the \n"
                                                        + "murderer of Agnes, before that monster escapes. \n", true, "rope", "unused with a knot, 2nd floor Hall.");
        
        library.addItem(false, "UNUSED", "As I inspected the bookshelf it cracks and creaks. The bookshelf slowly \n"
                                        + "moves itself and a door appears, I wonder where it leads.\n", false, "bookshelf", "revealed secret room, library");
        library.addItem(true, "You pick up a pistol\n", "Finding a pistol in the library, odd place to find a pistol. \n"
                                                    + "All bullets are still in the case, but no bullets \n"
                                                    + "or gunpowder were found by the scene. It didn’t seem \n"
                                                    + "to have caused the murder.\n", true, "pistol", "all bullets intact, Library");
    }

    /**
     * createPersons is responsible for creating persons for the game
     * They are instantiated objects of the data type Person.
     * 
     */
    private void createPersons() {
        phein = new Person("Mr. Phein", true, "Thank you for helping me, Detective! \n"
                                + "I hope that you find my daughter's murderer as quickly as possible. \n"
                                + "I don’t know anything about the murder I just heard a scream, ran and saw \n"
                                + "the body of my daughter, and I’ve been down here ever since. The gruesome \n"
                                + "body, I can’t even look at it!\n","Thanks you, heard scream, been by body since.",
                                "She was an angel, my daughter. An angel, so wonderful and beautiful, just like her mother. \n"
                                + "After you found her, the angel, she, had a lost her beauty and innocence, I tried to \n"
                                + "take her to therapy, but my angel never became the same again. Just like her mother \n"
                                + "sacrificed herself to give birth to Agnes, my angel, she, must sacrifice herself so \n"
                                + "that the beautiful angel she once was can be restored. But the angel never came back, \n"
                                + "she just died, never to return to life.\n", "phein");
        

        tyrion = new Person("Tyrion Lannister","I do have something; Veronica doesn’t seem to \n"
                                + "like Agnes very much, she’s been talking about her constantly, if I \n"
                                + "didn’t know any better I’d say she did it!\n","Veronica hates Agnes, talking about her.",
                                "What the hell?! Goddammit, I did not do it. You’re making \n"
                                + "some seriously far-stretched assumptions here, my little boy!\n", "tyrion");
        
        

        alfred = new Person("Alfred the Butler","I don’t know who did it, but I did see Veronica Mars, \n"
                                + "walking quickly away from that room with a guilty smug on her face.\n","Veronica, smug on face, walked from ballroom.",
                                "I’m afraid I didn’t have anything to do with the murder, Detective. Agnes \n"
                                + "was a wonderful girl, such an angel to have in the mansion, I could never \n"
                                + "do anything like that to her. How dare you ask that question?\n", "alfred");
        

        veronica = new Person("Veronica Mars","Gush! I haven’t heard anything about this! Oh my god, that is horrible! \n"
                                + "*Cell phone rings* Oh, I better take this, it’s an \n"
                                + "important call for an investigation, if you need \n"
                                + "me for anything, please ask!\n","Surprised response, not ruling her out yet.",
                                "Are you stupid? Can’t you tell that the others are lying?! I have \n"
                                + "tried just like you to solve this, yet you accuse me? This is stupid, \n"
                                + "as you can see the evidence clearly doesn’t point towards any of the guest, \n"
                                + "I might have planted some ratpoison on the weapon, but that was only to lead \n"
                                + "you to obvious murderer!\n", "veronica");
        
        stephanie = new Person("Stephanie Richburg","Wow, what the hell! Damn, I do have one thing to say though; Veronica seems \n"
                                + "to have been involving herself a lot with that room.\n","Veronica being a lot in ballroom.",
                                "OMG!!! you think i did it? I don’t know who you think \n"
                                + "you are, but I damn well didn’t do it, the evidence you’ve say you \n"
                                + "have does NOT have anything to with me, and if you accuse me again i will sue you.\n", "stephanie");
        
        
        ballRoom.addPerson(phein);
        library.addPerson(tyrion);
        kitchen.addPerson(alfred);
        garden.addPerson(stephanie);
        bedroom.addPerson(veronica);
    }
    
    /**
     * The game's main method. When the game is played, this method is always running.
     * It's main function is to get the players input in form of commands.
     * Also checks if the player is done playing the game, if yes. The game will stop.
     * Calls processCommand method, for processing the command typed by the player.
     */
    public void play(){            
        this.printWelcome();
                
        boolean finished = false;
        while (! finished) {
            Command command = this.parser.getCommand();
            finished = this.processCommand(command);
        }
        Scanner input = new Scanner(System.in);
        if(isCorrectAccusation){
            System.out.println("After that moment, it was like he snapped. From the mansion all the way \n"
                    + "to prison, he had the expression of “regret” plastered all over his face. \n");
            System.out.println("Press ENTER to continue.");
            input.nextLine();
            System.out.println("The murder is now solved, very well done. We hoped you enjoyed our game, if \n"
                    + "not, then return == null. We had a lot of fun making this short game, thank you for playing!\n");
            
        } else {
            System.out.println("Well, that was a surprise, he/she didn’t do it. I wonder who did then?\n" +
                               "You have used the one try you have, as you fail the guess a sharp sting is felt on your shoulder.\n");
            System.out.println("Press ENTER to continue.");
            input.nextLine();
            System.out.println("Blood arising down you right chest and your vision becomes blurry. "
                    + "The real murderer killed you, you damn fool. ");
            System.out.println("Press ENTER to continue.");
            input.nextLine();
            System.out.println("GAME OVER!");
        }
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
        System.out.println("*DRINK* Finally, the whisky is taking its toll on my body and I had to go to the bathroom.");
        System.out.println("Press ENTER to continue the story.");
        input.nextLine();
        System.out.println("I run towards the bathroom in desperate need, only to find a dead body in the middle of the bathroom, ");
        System.out.println("smeared in blood, it appears to be Mr. Phein’s daughter Agnes.");
        System.out.println("Press ENTER to continue the story.");
        input.nextLine();
        System.out.println("The room was empty, growing cold, dark and silent except for the faint shock in my mind. ");
        System.out.println("I will find the monster who did this, this is too gruesome to let be.");
        System.out.println();
        System.out.println("Here comes a small tutorial on how to play the game.");
        System.out.println("Press ENTER to begin the tutorial.");
        input.nextLine();
        System.out.println("Throughout this game you can use many different commands, they are listed here:");
        this.smallPrintHelp();
        System.out.println();
        System.out.println("You can move around to the different rooms using the command 'go' followed by the exit you want to go to.");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("Throughout the mansion you will find various items with which you can 'inspect' or 'take'.");
        System.out.println("You will also find various persons in the mansion, you can 'ask' these persons for clues.");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("All clues you gather and all items you 'inspect' will be added to your 'logbook'.");
        System.out.println("You can always look at your logbook with the command 'logbook'.");
        System.out.println("Use it to make a qualified guess on who the murderer is.");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("Once you have gathered all the clues from all the persons and gathered all the murder weapons, you can 'accuse' a person.");
        System.out.println("But beware, you only have 1 accusation. If you pick the wrong person to accuse, you will die and lose the game.");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("That was all the tutorial had to offer.");
        System.out.println();
        System.out.println("Type '" + CommandWord.HELP + "' if you need help, or cannot remember the commandwords.");
        System.out.println("Let the mysteries begin!");
        System.out.println("Press ENTER to begin.");
        input.nextLine();
        System.out.println(currentRoom.getLongDescription());
        System.out.println("Maybe i should inspect the body for clues.");
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
            this.printHelp();
        } else if (commandWord == CommandWord.GO) {
            this.goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = this.quit(command);
        } else if (commandWord == CommandWord.INSPECT){
            this.inspect(command);
        } else if (commandWord == CommandWord.ASK){
            this.interrogate(command);
        } else if (commandWord == CommandWord.ACCUSE){
            wantToQuit = this.accuse(command);
        } else if (commandWord == CommandWord.LOGBOOK){
            this.printLog(command);
        } else if (commandWord == CommandWord.TAKE){
            this.takeItem(command);
        } else if (commandWord == CommandWord.DROP){
            this.dropItem(command);
        } else if (commandWord == CommandWord.INVENTORY){
            this.inventory();
        } else if (commandWord == CommandWord.INFORMATION){
            System.out.println(currentRoom.getLongDescription());
        }
        return wantToQuit;
    }

    /**
     * Prints a brief help description about the game and it's objectives.
     * Prints all the commands available to the player.
     * gsdfgsdfg
     * sdgdfgsd
     * fgsdfg
     * sdfg
     * f
     * dfg
     * sdfgsdfgsdfgsf
     * h
     */
    private void printHelp(){
        System.out.println("You are the detective at a party.");
        System.out.println("Your job is to figure out who comitted the murder");
        System.out.println("Throughout the game you can interact with items and interrogate persons.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    private void smallPrintHelp(){
        parser.showCommands();
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
        
        if("the garden".contentEquals(currentRoom.getShortDescription()) && "kitchen".contentEquals(direction)){
            for(Items item : logbook.getInventory()){
                if(item.getName().equals("keys")){
                    System.out.println("You unlock the kitchen door and enter.");
                    currentRoom = nextRoom;
                    System.out.println(currentRoom.getLongDescription());
                    return;
                }
            }
            System.out.println("The door is locked! You need the keys for it.\n");
            return;
        }
        if("the secret room".contentEquals(currentRoom.getShortDescription()) && "exit".contentEquals(direction)){
            Room[] roomArray = {ballRoom, toilet, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, library, bedroom, secretRoom};
            
            currentRoom = roomArray[(int)(Math.random() * 12)];
            
            System.out.println(currentRoom.getLongDescription());
            return;
        }
        if (nextRoom == null) {
            System.out.println("There is no door!\n");
        } else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    public void inventory(){
        System.out.println("Your inventory contains: " + logbook.getInventory().size() + " items.");
        if(!logbook.getInventory().isEmpty()){
            for(Items item : logbook.getInventory()){
                System.out.print(item.getName());  // Print information about each item in inventory
            }
            System.out.println("\n");
        }
    }
    
    public void printLog(Command command){
        if(command.hasSecondWord()){
            if("weapons".equals(command.getSecondWord().toLowerCase())){
                System.out.println("You have found " + logbook.getMurderWeapons().size() + " potential murder weapons.");
                if(!logbook.getMurderWeapons().isEmpty()){
                    for(Items weapon : logbook.getMurderWeapons()){
                        System.out.print(weapon.getName());
                    }
                }
                System.out.println("\n");
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
                    currentRoom.addItem(item.isActive(),item.getMsgOnPickup(),item.getMsgOnInspect(),item.isMurderweapon(),item.getName(), item.getKeyWords());
                    System.out.println("You have dropped: " + item.getName() + "\n");
                    return;
                }
            }
            System.out.println("You have no item named: " + command.getSecondWord() + "\n");
        } else {
            System.out.println("Drop what?\n");
        }
    }
    
    private boolean quit(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Quit what?\n");
            return false;
        } else {
            return true;
        }
    }

    private void inspect(Command command) {
        if(command.hasSecondWord()){
            for(Items item : currentRoom.getItems()){
                if(item.getName().equals(command.getSecondWord())){
                    System.out.println(item.getMsgOnInspect());
                    logbook.addItemDescription(item);
                    if("carpet".equals(item.getName())){
                        ballRoom.setExit("trapdoor",dungeon);
                    } else if("bookshelf".equals(item.getName())){
                        library.setExit("bookshelf",secretRoom);
                    }
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
            Items toRemoveFromRoom = null;
            for(Items item : currentRoom.getItems()){
                if(item.getName().equals(command.getSecondWord())){
                    if(item.isActive()){
                        System.out.println(item.getMsgOnPickup());
                        logbook.addInventory(item);
                        toRemoveFromRoom = item;
                    } else {
                        System.out.println("You could not pick up the " + item.getName() + "\n");
                    }
                }
            }
            if(toRemoveFromRoom != null){
                currentRoom.getItems().remove(toRemoveFromRoom);
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
        if(logbook.askedAllPersons() && logbook.gatheredAllWeapons()){
            String whoToAccuse = command.getSecondWord().toLowerCase();
            isCorrectAccusation = false;

            if(!currentRoom.getPersonsInRoom().isEmpty()){
                for(Person person : currentRoom.getPersonsInRoom()){
                    
                   if(person.getAskName().toLowerCase().equals(whoToAccuse) || person.getName().toLowerCase().equals(whoToAccuse)){
                       if(person.isMurder()){
                           System.out.println(person.getAccusationResponse());
                           isCorrectAccusation = true;
                           return true;
                       }
                   }
               }
               for(Items item : currentRoom.getItems()){
                   if(item.getName().equals(whoToAccuse)){
                       System.out.println("You cannot accuse an item.");
                       return false;
                   }
               }
               return true;
            }
            System.out.println("There is no person here named: " + command.getSecondWord() + "\n");
            return false;
        }
        System.out.println("You haven't gathered enough evidence to point out a murderer yet!\n"
                         + "You ought to ask all the guests and find the murder weapon.\n");
        return false;
    }

    private void interrogate(Command command) {
        if(!command.hasSecondWord()){
            System.out.println("Ask who?\n");
            return;
        }
        
        if(!currentRoom.getPersonsInRoom().isEmpty()){
            String whoToAsk = command.getSecondWord().toLowerCase();
            for(Person person : currentRoom.getPersonsInRoom()){
               if(person.getAskName().toLowerCase().equals(whoToAsk) || person.getName().toLowerCase().equals(whoToAsk)){
                   System.out.println(person.getResponse());
                   logbook.addPersonResponse(person, person.getKeyWords());
                   return;
               }
           }
           System.out.println("There is no person here named: " + command.getSecondWord() + "\n");
           return;
        }
        System.out.println("There are no persons in the room.\n");
    }
}