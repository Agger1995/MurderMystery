/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two parts: a CommandWord and a string
 * (for example, if the command was "take map", then the two parts
 * are TAKE and "map").
 *
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 *
 * If the command had only one word, then the second word is <null>.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */
package Business;

import GUI.CommandWord;

/**
 * This class holds all of the Command data.
 *
 * @author kristian
 */
public class Command {

    private CommandWord commandWord;
    private String secondWord;

    /**
     * Constructor of the Command class.
     *
     * @param commandWord command word of the enum CommandWord
     * @param secondWord String as an extra command.
     */
    public Command(CommandWord commandWord, String secondWord) {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Returns the commandWord attribute
     *
     * @return CommandWord, commandWord
     */
    public CommandWord getCommandWord() {
        return this.commandWord;
    }

    /**
     * Returns the second part of the Command object
     *
     * @return String, command
     */
    public String getSecondWord() {
        return this.secondWord;
    }

    /**
     * Returns whether or not the command has a second word.
     *
     * @return boolean, true = has second word.
     */
    public boolean hasSecondWord() {
        return (!"".equals(this.secondWord));
    }
}
