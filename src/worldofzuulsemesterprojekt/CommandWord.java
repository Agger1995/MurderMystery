package worldofzuulsemesterprojekt;

/**
 * The CommandWord class is where we define new commands for the game.
 * It is an enum type class. Which basicly means that when we check the command the user typed,
 * we can recognize the command through this list of commands, if it is not there it is automatically refered to as UNKNOWN
 * @author FrameWork
 */
public enum CommandWord{
    GO("go"), QUIT("quit"), HELP("help"), ASK("ask"), INSPECT("inspect"), ACCUSE("accuse"), LOGBOOK("logbook"), TAKE("take"), UNKNOWN("?");
    
    private String commandString;
    
    /**
     * Constructor for the class
     * @param commandString 
     */
    CommandWord(String commandString){
        this.commandString = commandString;
    }
    
    @Override
    public String toString(){
        return commandString;
    }
}
