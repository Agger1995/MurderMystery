/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author chris
 */
public class PrintUtility {

    private final Parser parser;
    private final Scanner input;
    private ArrayList<String> descriptions;

    public PrintUtility() {
        this.parser = new Parser();
        this.input = new Scanner(System.in);
    }

    public void printIntroMessage() {
        for (String s : descriptions) {
            switch (s) {
                case "[break]:":
                    input.nextLine();
                    break;
                    
                case "":
                    System.out.println("");
                    break;
                    
                default:
                    System.out.println(s);
                    break;
            }
        }
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

    public void printWinMessage() {
        System.out.println("After that moment, it was like he snapped. From the mansion all the way \n"
                + "to prison, he had the expression of “regret” plastered all over his face. \n");
        System.out.println("Press ENTER to continue.");
        input.nextLine();
        System.out.println("The murder is now solved, very well done. We hoped you enjoyed our game, if \n"
                + "not, then return == null. We had a lot of fun making this short game, thank you for playing!\n");
    }

    public void printLoseMessageAcussation() {
        System.out.println("Well, that was a surprise, you acussed the wrong person. I wonder who did then?\n"
                + "just as you realise who the correct murdere is, you feel a sharp sting in you bag.\n");
        System.out.println("Blood arising down you right chest and your vision becomes blurry. "
                + "The real murderer killed you, you damn fool. ");
    }

    public void printLoseTimeRanOutMessage() {
        System.out.println("Fail message if time runs out:\n"
                + "Oh my, the time! Itâ€™s already kl.08.00, and the cops are arriving,"
                + "with their usual â€œhardâ€� work and â€œtrusty understanding of Mr. Pheinâ€™s current lossâ€�"
                + "and subsequently his financial situation I doubt this case will be solved, "
                + "buried in money and secrets so that Mr. Pheins family name remains intact. "
                + "It will forever remain a Mystery, a Mystery Mansion.");
    }

    public void printHelp() {
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

    public void setIntroMessage(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }

}
