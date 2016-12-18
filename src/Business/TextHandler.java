/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import java.util.ArrayList;

/**
 * Handles different messages, like the ones you get, when you win, lose etc. It is also responsible for the intro message.
 *
 * @author chris
 */
public class TextHandler {

    private ArrayList<String> descriptions;

    /**
     * Returns an ArrayList containing the full intro message in small bites.
     *
     * @return ArrayList of the intro message, so it can be printed out little by little.
     */
    public ArrayList<String> printIntroMessage() {
        ArrayList<String> toReturn = new ArrayList<>();
        int index = 0;
        for (String s : descriptions) {
            toReturn.add(index, s);
            index++;
        }
        return toReturn;
    }

    /**
     * returns this string if won.
     *
     * @return string, win message
     */
    String printWinMessage() {
        return "After that moment, it was like he snapped. From the mansion all the way to prison, he had the expression of “regret” plastered all over his face. The murder is now solved, very well done. We hoped you enjoyed our game, if not, then return == null. We had a lot of fun making this short game, thank you for playing!\n";
    }

    /**
     * returns this string if lost.
     *
     * @return string, lose message.
     */
    String printLoseMessageAcussation() {
        return "Well, that was a surprise, you acussed the wrong person. I wonder who did then? just as you realise who the correct murdere is, you feel a sharp sting in you bag. Blood arising down you right chest and your vision becomes blurry. The real murderer killed you, you damn fool. ";
    }

    /**
     * This message is returned, when the time runs out.
     *
     * @return string, message
     */
    String printLoseTimeRanOutMessage() {
        return "Oh my, the time! It's already kl. 08.00, and the cops are arriving, with their usual 'hard' work and rusty understanding of Mr. Phein's current loss and subsequently his financial situation I doubt this case will be solved, buried in money and secrets so that Mr. Pheins family name remains intact. It will forever remain a Mystery, a Mystery Mansion.";
    }

    /**
     * Unused. Back in time, you had to collect all evidence
     *
     * @return string, not done yet.
     */
    String printAccuseErrorMsg() {
        return "You haven't gathered enough evidence to point out a murderer yet! You ought to ask all the guests and find the murder weapon.\n";
    }

    /**
     * Sets the description of a given scenario.
     *
     * @param descriptions, descriptions
     */
    public void setIntroMessage(ArrayList<String> descriptions) {
        this.descriptions = descriptions;
    }
}
