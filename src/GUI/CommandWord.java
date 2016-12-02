package GUI;

/**
 * The CommandWord class is where we define new commands for the game.
 * It is an enum type class. Which basicly means that when we check the command the user typed,
 * we can recognize the command through this list of commands, if it is not there it is automatically refered to as UNKNOWN
 * @author FrameWork
 */
public enum CommandWord{
    GO("Go"), ASK("Ask"), INSPECT("Inspect"), ACCUSE("Accuse"), TAKE("Take"), DRINK("drink"), DROP("Drop");
    
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
        return this.commandString;
    }
    
    public static CommandWord get(String string) {
        for(CommandWord cmds : CommandWord.values()) {
            if (cmds.toString().equals(string)) {
                return cmds;
            }
        }
        return null;
    }
}
