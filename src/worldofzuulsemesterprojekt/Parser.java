package worldofzuulsemesterprojekt;

import java.util.Scanner;

/**
 * The Parser class is used for fetching user input through Scanner.
 * 
 * @author FrameWork
 */
public class Parser{
    private CommandWords commands;
    private Scanner reader;

    public Parser(){
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand(){
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> "); 

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }

    /**
     * 
     */
    public void showCommands(){
        commands.showAll();
    }
}
