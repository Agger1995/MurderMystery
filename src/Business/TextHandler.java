/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import GUI.CommandWord;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author chris
 */
public class TextHandler {

    private final Parser parser;
    private final Scanner input;
    private ArrayList<String> descriptions;

    public TextHandler() {
        this.parser = new Parser();
        this.input = new Scanner(System.in);
    }

    public ArrayList<String> printIntroMessage() {
        ArrayList<String> toReturn = new ArrayList<>();
        int index = 0;
        for (String s : descriptions) {
            toReturn.add(index, s);
            index++;
        }
//        System.out.println("Here comes a small tutorial on how to play the game.");
//        System.out.println("Press ENTER to begin the tutorial.");
//        input.nextLine();
//        System.out.println("Throughout this game you can use many different commands, they are listed here:");
//        parser.showCommands();
//        System.out.println();
//        System.out.println("You can move around to the different rooms using the command 'go' followed by the exit you want to go to.");
//        System.out.println("Press ENTER to continue.");
//        input.nextLine();
//        System.out.println("Throughout the mansion you will find various items with which you can 'inspect' or 'take'.");
//        System.out.println("You will also find various persons in the mansion, you can 'ask' these persons for clues.");
//        System.out.println("Press ENTER to continue.");
//        input.nextLine();
//        System.out.println("All clues you gather and all items you 'inspect' will be added to your 'logbook'.");
//        System.out.println("You can always look at your logbook with the command 'logbook'.");
//        System.out.println("Use it to make a qualified guess on who the murderer is.");
//        System.out.println("Press ENTER to continue.");
//        input.nextLine();
//        System.out.println("Once you have gathered all the clues from all the persons and gathered all the murder weapons, you can 'accuse' a person.");
//        System.out.println("But beware, you only have 1 accusation. If you pick the wrong person to accuse, you will die and lose the game.");
//        System.out.println("Press ENTER to continue.");
//        input.nextLine();
//        System.out.println("That was all the tutorial had to offer.");
//        System.out.println();
//        System.out.println("Type '" + CommandWord.HELP + "' if you need help, or cannot remember the commandwords.");
//        System.out.println("Let the mysteries begin!");
//        System.out.println("Press ENTER to begin.");
//        input.nextLine();
        return toReturn;
    }

    public String printWinMessage() {
        return "After that moment, it was like he snapped. From the mansion all the way \n"
                + "to prison, he had the expression of “regret” plastered all over his face. \n"
                + "The murder is now solved, very well done. We hoped you enjoyed our game, if \n"
                + "not, then return == null. We had a lot of fun making this short game, thank you for playing!\n";
    }

    public String printLoseMessageAcussation() {
        return "Well, that was a surprise, you acussed the wrong person. I wonder who did then?\n"
                + "just as you realise who the correct murdere is, you feel a sharp sting in you bag.\n"
                + "Blood arising down you right chest and your vision becomes blurry. "
                + "The real murderer killed you, you damn fool. ";
    }

    public String printLoseTimeRanOutMessage() {
        return "Fail message if time runs out:\n"
                + "Oh my, the time! Itâ€™s already kl.08.00, and the cops are arriving,"
                + "with their usual â€œhardâ€� work and â€œtrusty understanding of Mr. Pheinâ€™s current lossâ€�"
                + "and subsequently his financial situation I doubt this case will be solved, "
                + "buried in money and secrets so that Mr. Pheins family name remains intact. "
                + "It will forever remain a Mystery, a Mystery Mansion.";
    }

    public String printAccuseErrorMsg() {
        return "You haven't gathered enough evidence to point out a murderer yet!\n"
                + "You ought to ask all the guests and find the murder weapon.\n";
    }

    public void setIntroMessage(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }
}