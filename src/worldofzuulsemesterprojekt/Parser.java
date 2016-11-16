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
        this.commands = new CommandWords();
        this.reader = new Scanner(System.in);
    }

    /**
     * getCommand is the command that listens for input
     * Splits up the input into more than one word
     * The first word is assigned to the variable word1, the second word is assigned to the variable word2
     * The method is made so it interprets everything that comes after the first word, as a whole sentence.
     * This sentence is the assigned to word2.
     * This is made so that we can interact with items and persons whose name consists of more than 1 word.
     * @return an Object of type Command, containing the input words as commands
     */
    public Command getCommand(){
        String inputLine;
        String word1 = "";
        String word2 = "";
        
        System.out.print("> "); 

        inputLine = this.reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next().toLowerCase();
            while(tokenizer.hasNext()) {
                word2 += tokenizer.next().toLowerCase() + " ";
            }
        }

        return new Command(this.commands.getCommandWord(word1), word2.trim());
    }

    /**
     * Calls the method showAll in CommandWords class.
     */
    public void showCommands(){
        this.commands.showAll();
    }
}