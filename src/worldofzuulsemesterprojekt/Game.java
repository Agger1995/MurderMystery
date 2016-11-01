package worldofzuulsemesterprojekt;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The game class is the class which connects all the classes and uses them appropriately
 It uses class Item to create new instances of the type Item, the same goes for Person
 * @author chris
 */
public class Game {
    private final Parser parser;
    private final PrintUtility printer;
    private Room currentRoom; 
    private final LogBook logbook;
    private final Inventory inventory;
    private boolean isCorrectAccusation;
    private Room ballRoom, bathroom, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, bedroom, library, secretRoom;
    private Person tyrion, phein, alfred, veronica, stephanie;
    private Item carpet, body, toiletPaper, whiskey, bloodyKnife, plasmaTv, emptyKnifeholder, ratPoison, apple, keys, golfClub, flowers, rope, bookshelf, oddBook, pistol, ratCorpse, skull;
    private boolean deadByDrink;
    private Point pointSystem;
    private Highscore highScore;
    public static final HashMap<Integer, Item> itemDatabase = new HashMap<>();
    public static final HashMap<Integer, Person> personDatabase = new HashMap<>();
        

    /**
     * The Game class' constructor
     * Calls the method createRooms(), and initiates the game's parser
     * @throws java.io.FileNotFoundException
     */
    public Game() throws FileNotFoundException{
        this.createRooms();
        this.createItems();
        this.createPersons();
        this.highScore = new Highscore();
        this.pointSystem = new Point();
        this.deadByDrink = false;
        this.inventory = new Inventory(10);
        this.logbook = new LogBook();
        this.parser = new Parser();
        this.printer = new PrintUtility();
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
        bathroom = new Room("the bathroom");
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
        
        ballRoom.setExit("bathroom",bathroom);
        ballRoom.setExit("hall",groundFloorHall);
        ballRoom.setExit("kitchen",kitchen);
        
        bathroom.setExit("ballroom",ballRoom);
        
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
        library.setExit("bedroom",bedroom);
        
        secretRoom.setExit("exit",null);
        
        currentRoom = bathroom;
    }
    
    private void createItems() {
        carpet = new Item(0,"carpet",false, "UNUSED", "Hmm, that’s a ugly carpet compared to the rest of this mansion. \n"
                                        + "*Wind gushing from the open windows* suddenly a trapdoor \n"
                                        + "became visible as the wind grabbed a bit of the carpet.\n", false, "Dusty, revealed trapdoor",0, false);
        
        body = new Item(1, "body", false, "UNUSED", "Such a shame, Agnes known for her beauty resembling from her \n"
                                        + "mother, now had a face resembling massacred raw beef with \n"
                                        + "hair. A bit of dust is around her face, could be anything from \n"
                                        + "flour to baby powder *sniff* hmm fits the case, it has a very distinctive smell.\n", false, "Poisoned knife caused it, Bathroom.",0, false);

        toiletPaper = new Item(2, "toilet paper", true, "You pick up some toilet paper","Hmm, appropriately placed toilet paper. It would be a shame, if someone, stole it.\n",false,"once a tree, what a cruel destiny, Bathroom",1, false);

        whiskey = new Item(3, "whiskey", true, "You pick up the whiskey", "A bottle of whiskey on the floor in hallway on \n"
                                                        + "the 1st floor, who could be leaving such a beauty there? *drink* ahh… \n"
                                                        + "that’s the stuff. Oh, better get back to work, otherwise \n"
                                                        + "I’ll be spending all my time here.\n", false, "very strong taste, but good",3, true);
        
        bloodyKnife = new Item(4, "bloody knife", true, "You pick up the bloody knife", "A knife wrapped in a handkerchief soaked in blood... \n"
                                                            + "in Mr. Phine’s room... the knife seems to also have some powder on it. \n"
                                                            + "It looks like the same powder as the one on the body. \n"
                                                            + "*sniff*, yeah this is ratpoison. Odd to find something like \n"
                                                            + "that in his room, I wonder who dropped this here.\n", true, "bloody and powdered, suspicious, bedroom",0, false);
        
        plasmaTv = new Item(5, "plasma tv", true, "You pick up a Plasma TV", "Hmm, very fine and expensive looking plasma TV. Might be worth something.\n", false,"new expensive tv, bedroom",7, false);

        emptyKnifeholder = new Item(6, "empty knifeholder", false, "UNUSED", "Hmm, an empty knifeholder in the kitchen. Wonder where the knife is?\n",false, "Knife-holder with missing knife, Kitchen",0, false);
        
        ratPoison = new Item(7, "rat poison", true, "You pick up the rat poison", "Odd to find an empty bottle of rat poison in the kitchen, \n"
                                                            + "the powdery substance matches the one found by the body. \n"
                                                            + "The amount is rather small to cause the murder. \n"
                                                            + "*Sniff* ratpoison, I can always tell, ratpoison has a very distinct smell, \n"
                                                            + "only alfred have been in the kitchen all night. \n"
                                                            + "This could be a good idea to keep this in mind though.\n", true, "powder, resemble powder on knife, kitchen.",0, false);
        apple = new Item(8, "apple", true, "You pick up an apple", "Hmm, a delicious red apple. Wonder if it has anything to do with the murder?\n", false,"red apple, kitchen",1, false);

        keys = new Item(9, "keys", true, "You pick up a set of rusty keys", "These are some old rusty looking keys\n", false, "rusty keys, says 'kitchen', garden",2, false);
        
        golfClub = new Item(10, "golf club", true, "You pick up a golf club", "A golf club in the garden, doesn’t seems suspicious so far. There is \n"
                                                        + "no blood on it, and it seems to be clean and shiny, yet it \n"
                                                        + "has been left alone in the garden.\n", true, "dirty with grass, no blood, garden.",0, false);
        
        flowers = new Item(11, "flowers", true, "You pick up a beautiful bouquet of flowers", "Those are some great looking flowers, i could bring back a bouquet \n"
                                                                            + "to my darling wife, once i solve this mystery.\n", false,"beautiful flowers, garden",4, false);
        
        rope = new Item(12, "rope", true, "You pick up a rope", "A rope with a tied knot, not usually something you’d find in a hallway \n"
                                                        + "on the 2nd hall. Perhaps someone were trying to off themselves. \n"
                                                        + "Interesting case, but irrelevant right now. We gotta find the \n"
                                                        + "murderer of Agnes, before that monster escapes. \n", true, "unused with a knot, 2nd floor Hall.",0, false);

        bookshelf = new Item(13, "bookshelf", false, "UNUSED", "As I inspected the bookshelf it cracks and creaks. The bookshelf slowly \n"
                                        + "moves itself and a door appears, I wonder where it leads.\n", false, "revealed secret room, library",0, false);
        
        pistol = new Item(14, "pistol", true, "You pick up a pistol", "Finding a pistol in the library, odd place to find a pistol. \n"
                                                    + "All bullets are still in the case, but no bullets \n"
                                                    + "or gunpowder were found by the scene. It didn’t seem \n"
                                                    + "to have caused the murder.\n", true, "all bullets intact, Library",0, false);
        
        oddBook = new Item(15, "odd book", true, "You pick up a book", "Hmm, that's curious. A crime book about a murder happening in a mansion at a \n"
                                                    + "dinner party, just like this! That's a bit odd.\n", false,"book regarding murder, library",2, false);
        
        ratCorpse = new Item(16, "rat corpse", true, "You pick up a rat corpse","Ew, a disgusting rat corpse.. Looks like it has been lying here for quite some time now.\n",false,"rotten rat corpse, dungeon",3, false);
        
        skull = new Item(17, "skull", true, "You pick up a human skull", "Hmm, a human skull. I wonder what sick history this mansion has, surely \n"
                                                        + "this can only mean that another murder has happened here in the past.\n",false,"human skull, dungeon",5, false);

        itemDatabase.put(body.getID(), body);
        itemDatabase.put(carpet.getID(), carpet);
        itemDatabase.put(toiletPaper.getID(), toiletPaper);
        itemDatabase.put(whiskey.getID(), whiskey);
        itemDatabase.put(rope.getID(), rope);
        itemDatabase.put(bloodyKnife.getID(), bloodyKnife);
        itemDatabase.put(plasmaTv.getID(), plasmaTv);
        itemDatabase.put(emptyKnifeholder.getID(), emptyKnifeholder);
        itemDatabase.put(ratPoison.getID(), ratPoison);
        itemDatabase.put(apple.getID(), apple);
        itemDatabase.put(keys.getID(), keys);
        itemDatabase.put(golfClub.getID(), golfClub);
        itemDatabase.put(flowers.getID(), flowers);
        itemDatabase.put(bookshelf.getID(), bookshelf);
        itemDatabase.put(pistol.getID(), pistol);
        itemDatabase.put(oddBook.getID(), oddBook);
        itemDatabase.put(ratCorpse.getID(), ratCorpse);
        itemDatabase.put(skull.getID(), skull);
        
        ballRoom.addItem(carpet.getID());
        bathroom.addItem(body.getID());
        bathroom.addItem(toiletPaper.getID());
        upstairsHall.addItem(whiskey.getID());
        upstairsHall.addItem(rope.getID());
        bedroom.addItem(bloodyKnife.getID());
        bedroom.addItem(plasmaTv.getID());
        kitchen.addItem(emptyKnifeholder.getID());
        kitchen.addItem(ratPoison.getID());
        kitchen.addItem(apple.getID());
        garden.addItem(keys.getID());
        garden.addItem(golfClub.getID());
        garden.addItem(flowers.getID());
        library.addItem(bookshelf.getID());
        library.addItem(pistol.getID());
        library.addItem(oddBook.getID());
        dungeon.addItem(ratCorpse.getID());
        dungeon.addItem(skull.getID());
    }

    /**
     * createPersons is responsible for creating persons for the game
     * They are instantiated objects of the data type Person.
     * 
     */
    private void createPersons() {
        phein = new Person(0,"Mr. Phein", true, "Thank you for helping me, Detective! \n"
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
        

        tyrion = new Person(1,"Tyrion Lannister","I do have something; Veronica doesn’t seem to \n"
                                + "like Agnes very much, she’s been talking about her constantly, if I \n"
                                + "didn’t know any better I’d say she did it!\n","Veronica hates Agnes, talking about her.",
                                "What the hell?! Goddammit, I did not do it. You’re making \n"
                                + "some seriously far-stretched assumptions here, my little boy!\n", "tyrion");
        
        

        alfred = new Person(2,"Alfred the Butler","I don’t know who did it, but I did see Veronica Mars, \n"
                                + "walking quickly away from that room with a guilty smug on her face.\n","Veronica, smug on face, walked from ballroom.",
                                "I’m afraid I didn’t have anything to do with the murder, Detective. Agnes \n"
                                + "was a wonderful girl, such an angel to have in the mansion, I could never \n"
                                + "do anything like that to her. How dare you ask that question?\n", "alfred");
        

        veronica = new Person(3,"Veronica Mars","Gush! I haven’t heard anything about this! Oh my god, that is horrible! \n"
                                + "*Cell phone rings* Oh, I better take this, it’s an \n"
                                + "important call for an investigation, if you need \n"
                                + "me for anything, please ask!\n","Surprised response, not ruling her out yet.",
                                "Are you stupid? Can’t you tell that the others are lying?! I have \n"
                                + "tried just like you to solve this, yet you accuse me? This is stupid, \n"
                                + "as you can see the evidence clearly doesn’t point towards any of the guest, \n"
                                + "I might have planted some ratpoison on the weapon, but that was only to lead \n"
                                + "you to obvious murderer!\n", "veronica");
        
        stephanie = new Person(4,"Stephanie Richburg","Wow, what the hell! Damn, I do have one thing to say though; Veronica seems \n"
                                + "to have been involving herself a lot with that room.\n","Veronica being a lot in ballroom.",
                                "OMG!!! you think i did it? I don’t know who you think \n"
                                + "you are, but I damn well didn’t do it, the evidence you’ve say you \n"
                                + "have does NOT have anything to with me, and if you accuse me again i will sue you.\n", "stephanie");
        
        personDatabase.put(phein.getID(), phein);
        personDatabase.put(tyrion.getID(), tyrion);
        personDatabase.put(alfred.getID(), alfred);
        personDatabase.put(stephanie.getID(), stephanie);
        personDatabase.put(veronica.getID(), veronica);
        
        ballRoom.addPerson(phein.getID());
        library.addPerson(tyrion.getID());
        kitchen.addPerson(alfred.getID());
        garden.addPerson(stephanie.getID());
        bedroom.addPerson(veronica.getID());
    }
    
    /**
     * The game's main method. When the game is played, this method is always running.
     * It's main function is to get the players input in form of commands.
     * Also checks if the player is done playing the game, if yes. The game will stop.
     * Calls processCommand method, for processing the command typed by the player.
     */
    public void play(){
        highScore.readHighscore();
        
        this.printWelcome();
        
        boolean finished = false;
        while (! finished) {
            Command command = this.parser.getCommand();
            finished = this.processCommand(command);
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Press ENTER to continue..");
        input.nextLine();
        if(isCorrectAccusation){
            pointSystem.addPoints(100);
            printer.printWinMessage();
        } else if(deadByDrink){
            pointSystem.addPoints(-100);
        } else {
            printer.printLoseMessage();
            pointSystem.addPoints(-100);
        }
        System.out.println("GAME OVER!");
    }

    /**
     * The game's welcome message. printWelcome() is called when game.play(); is called.
     * Displays the general information regarding the game.
     * Command list and a description of the room the player is in at the beginning.
     */
    private void printWelcome(){
        printer.printIntroMessage();
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
        } else if( commandWord == CommandWord.DRINK){
            wantToQuit = this.drink(command);
        }
        return wantToQuit;
    }

    /**
     * Prints a brief help description about the game and it's objectives.
     * Prints all the commands available to the player.
     */
    private void printHelp(){
        printer.printHelp();
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
            for(int itemID : inventory.getInventory()){
                if(itemID == 9){
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
            Room[] roomArray = {ballRoom, bathroom, kitchen, groundFloorHall, dungeon, dungeonHall1, dungeonHall2, garden, upstairsHall, library, bedroom, secretRoom};
            
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
        System.out.println("Your inventory contains: " + inventory.getInventory().size() + " items. Total weight is: " + inventory.getInventorySize() + "/" + inventory.getMaxInventorySize() + ".");
        if(!inventory.getInventory().isEmpty()){
            for(Integer itemID : inventory.getInventory()){
                Item tempItemObject = itemDatabase.get(itemID);
                
                System.out.print(tempItemObject.getName() + ": Weight: " + tempItemObject.getWeight() + ".\n");  // Print information about each item in inventory
            }
            System.out.println("\n");
        }
    }
    
    public void printLog(Command command){
        if(command.hasSecondWord()){
            if("weapons".equals(command.getSecondWord().toLowerCase())){
                System.out.println("You have found " + logbook.getMurderWeapons().size() + " potential murder weapons.");
                if(!logbook.getMurderWeapons().isEmpty()){
                    for(Integer itemID : logbook.getMurderWeapons()){
                        Item tempItemObject = itemDatabase.get(itemID);
                        System.out.print(tempItemObject.getName());
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
            for(Integer itemID : inventory.getInventory()){
                Item tempItemObject = itemDatabase.get(itemID);
                if(tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase())){
                    inventory.removeItem(itemID, tempItemObject.getWeight());
                    currentRoom.addItem(itemID);
                    System.out.println("You have dropped: " + tempItemObject.getName() + "\n");
                    return;
                }
            }
            for(Integer itemID : logbook.getMurderWeapons()){
                Item tempItemObject = itemDatabase.get(itemID);
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
            for(Integer itemID : currentRoom.getItems()){
                Item tempItemObject = itemDatabase.get(itemID);
                if(tempItemObject.getName().equals(command.getSecondWord())){
                    System.out.println(tempItemObject.getMsgOnInspect());
                    logbook.addItemDescription(itemID, tempItemObject.getKeyWords());
                    if(!tempItemObject.isInspected()){
                        tempItemObject.setHasBeenInspected(true);
                        pointSystem.addPoints(1);
                    }
                    if("carpet".equals(tempItemObject.getName())){
                        ballRoom.setExit("trapdoor",dungeon);
                    } else if("bookshelf".equals(tempItemObject.getName())){
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
            Integer idToRemoveFromRoom = null;
            for(Integer itemID : currentRoom.getItems()){
                Item tempItemObject = itemDatabase.get(itemID);
                if(tempItemObject.getName().equals(command.getSecondWord())){
                    if(tempItemObject.isActive()){
                        if(inventory.isInventoryFull(tempItemObject.getWeight())){
                            System.out.println(tempItemObject.getMsgOnPickup() + ". It weights: " + tempItemObject.getWeight() + "\n");
                            inventory.addInventory(itemID, tempItemObject.getWeight());
                            if(tempItemObject.isMurderweapon()){
                                logbook.addMurderWeapons(itemID);
                            }
                            idToRemoveFromRoom = itemID;
                        } else {
                            System.out.println("Inventory is full! You are carrying to much weight.\n");
                        }
                    } else {
                        System.out.println("You could not pick up the " + tempItemObject.getName() + "\n");
                    }
                }
            }
            if(idToRemoveFromRoom != null){
                currentRoom.getItems().remove(idToRemoveFromRoom);
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
                for(Integer personID : currentRoom.getPersonsInRoom()){
                    Person tempPersonObject = personDatabase.get(personID);
                    
                   if(tempPersonObject.getAskName().toLowerCase().equals(whoToAccuse) || tempPersonObject.getName().toLowerCase().equals(whoToAccuse)){
                       if(tempPersonObject.isMurder()){
                           isCorrectAccusation = true;
                       }
                       System.out.println(tempPersonObject.getAccusationResponse());
                       return true; // end game
                   }
               }
               return true;
            }
            for(Integer itemID : currentRoom.getItems()){
                Item tempItemObject = itemDatabase.get(itemID);
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
        
        if(!currentRoom.getPersonsInRoom().isEmpty()){
            String whoToAsk = command.getSecondWord().toLowerCase();
            for(Integer personID : currentRoom.getPersonsInRoom()){
                Person tempPersonObject = personDatabase.get(personID);
                if(tempPersonObject.getAskName().toLowerCase().equals(whoToAsk) || tempPersonObject.getName().toLowerCase().equals(whoToAsk)){
                    System.out.println(tempPersonObject.getResponse());
                    logbook.addPersonResponse(personID, tempPersonObject.getKeyWords());
                    if(!tempPersonObject.isAsked()){
                        tempPersonObject.setHasBeenAsked(true);
                        pointSystem.addPoints(5);
                    }
                    return;
                }
           }
           System.out.println("There is no person here named: " + command.getSecondWord() + "\n");
           return;
        }
        System.out.println("There are no persons in the room.\n");
    }

    private boolean drink(Command command) {
        if(command.hasSecondWord()){
           for(Integer itemID : currentRoom.getItems()){//tjekker items som er i current room, men manger at den ogsÃ¥ tjeker inventory
               Item tempItemObject = itemDatabase.get(itemID);
                if(tempItemObject.getName().toLowerCase().equals(command.getSecondWord().toLowerCase()) && tempItemObject.isDrinkable()){//tjek if there is an item with the same name as the input and if it is drinkabel
                    if(logbook.isDrinkMax()){//tjek if max drink is true
                        logbook.addDrink();//if max drik true, we add 1 to drinkCount
                    } else{
                        System.out.println("*Drink* I feel kind of wierd maybe i shouldn't have been drinking so much tonight.");//if drink is > maxDrink den we die
                       deadByDrink = true; //end game
                       return true;
                    }
                    System.out.println("*Drink* mmmm... that was a good tasting " + command.getSecondWord() + ".\n");//if drink is < maxDrink den we drink
                    return false; //continue game
                } else {
                   System.out.println("You can not drink the " + command.getSecondWord() + ".\n");//if the item is not drikabel we can't drink it
                   return false; //continue game
                }
            }  
        }
       return false;//continue game
    }
}