/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.Scanner;

/**
 *
 * @author chris
 */
public class PrintUtility {
    private final Parser parser;
    private final Scanner input;
    
    public PrintUtility(){
        this.parser = new Parser();
        this.input = new Scanner(System.in);
    }
    
    public void printIntroMessage(){
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
        parser.showCommands();
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
    }
    
    public void printWinMessage(){
        System.out.println("After that moment, it was like he snapped. From the mansion all the way \n"
                    + "to prison, he had the expression of “regret” plastered all over his face. \n");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("The murder is now solved, very well done. We hoped you enjoyed our game, if \n"
                + "not, then return == null. We had a lot of fun making this short game, thank you for playing!\n");
    }
    
    public void printLoseMessage(){
        System.out.println("Well, that was a surprise, he/she didn’t do it. I wonder who did then?\n" +
                               "You have used the one try you have, as you fail the guess a sharp sting is felt on your shoulder.\n");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("Blood arising down you right chest and your vision becomes blurry. "
                + "The real murderer killed you, you damn fool. ");
    }
    
    public void printHelp(){
        System.out.println("You are the detective at a party.");
        System.out.println("Your job is to figure out who comitted the murder");
        System.out.println("Throughout the game you can interact with items and interrogate persons.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    void printAccuseErrorMsg() {
        System.out.println("You haven't gathered enough evidence to point out a murderer yet!\n"
                         + "You ought to ask all the guests and find the murder weapon.\n");
    }
    
}
