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
public class TextHandler {

    private final Scanner input;
    private ArrayList<String> descriptions;

    public TextHandler() {
        this.input = new Scanner(System.in);
    }

    public ArrayList<String> printIntroMessage() {
        ArrayList<String> toReturn = new ArrayList<>();
        int index = 0;
        for (String s : descriptions) {
            toReturn.add(index, s);
            index++;
        }
        return toReturn;
    }

    public String printWinMessage() {
        return "After that moment, it was like he snapped. From the mansion all the way to prison, he had the expression of “regret” plastered all over his face. The murder is now solved, very well done. We hoped you enjoyed our game, if not, then return == null. We had a lot of fun making this short game, thank you for playing!\n";
    }

    public String printLoseMessageAcussation() {
        return "Well, that was a surprise, you acussed the wrong person. I wonder who did then? just as you realise who the correct murdere is, you feel a sharp sting in you bag. Blood arising down you right chest and your vision becomes blurry. The real murderer killed you, you damn fool. ";
    }

    public String printLoseTimeRanOutMessage() {
        return "Fail message if time runs out: Oh my, the time! It's already kl.08.00, and the cops are arriving, with their usual 'hard' work and rusty understanding of Mr. Phein's current loss and subsequently his financial situation I doubt this case will be solved, buried in money and secrets so that Mr. Pheins family name remains intact. It will forever remain a Mystery, a Mystery Mansion.";
    }

    public String printAccuseErrorMsg() {
        return "You haven't gathered enough evidence to point out a murderer yet! You ought to ask all the guests and find the murder weapon.\n";
    }

    public void setIntroMessage(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }
}
