package worldofzuulsemesterprojekt;

import java.util.Scanner;

/**
 * The Parser class is used for fetching user input through Scanner.
 * @author FrameWork
 */
public class Parser{
    private CommandWords commands;
    private Scanner reader;

    /**
     * Constructor for Parser class
     * Initializes the required classes that is needed for this class to function properly
     * Uses the Scanner import class for user input
     * Uses the CommandWord class for the command words
     */
    public Parser(){
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * getCommand is the command that listens for input and interprets the input given
     * Splits up the input into more than one word
     * @return an Object of type Command, containing the input words as commands
     */
    public Command getCommand(){
        String inputLine;
        String word1 = "";
        String word2 = "";
        
        System.out.print("> "); 

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            while(tokenizer.hasNext()) {
                word2 += tokenizer.next().toLowerCase() + " ";
            }
        }

        return new Command(commands.getCommandWord(word1), word2.trim());
    }

    /**
     * Shows the commands available to the player
     */
    public void showCommands(){
        commands.showAll();
    }
}
