package worldofzuulsemesterprojekt;
import java.util.HashMap;

/**
 * 
 * @author Marie
 */
public class CommandWords{
    private HashMap<String, CommandWord> validCommands;

    /**
     * Constructor
     * Fills up the HashMap, validCommands with all of our commands from the CommandWord class.
     * The HashMap contains a "Key" which is the enum command variable.
     * The Key refers to a "value" which is the content of the command variable
     * 
     */
    public CommandWords(){
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }
    

    /**
     * 
     * @param commandWord
     * @return The CommandWord is returned
     */
    public CommandWord getCommandWord(String commandWord){
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        } else {
            return CommandWord.UNKNOWN;
        }
    }
    
    /**
     * 
     * @param aString
     * @return 
     */
    public boolean isCommand(String aString){
        return validCommands.containsKey(aString);
    }

    /**
     * showAll() method will print all commands available for the user
     * The method will not print UNKNOWN command.
     */
    public void showAll(){
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
