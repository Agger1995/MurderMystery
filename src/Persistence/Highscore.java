/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author chris
 */
public class Highscore {

    private String[] savedName;
    private int[] savedPoints;
    private int actualSavedArrayLength;
    private String path;

    /**
     * Sets the attributes, like the path to the scenario.
     *
     * @param path of the scenario.
     */
    public Highscore(String path) {
        this.savedName = new String[10];
        this.savedPoints = new int[10];
        this.actualSavedArrayLength = 0;
        this.path = path;
    }

    /**
     * Returns a string of the highscores for a given scenario to one big
     * String.
     *
     * @return highscore string.
     */
    public String getHighscoreData() {
        String returnString = "";
        for (int i = 0; i < this.actualSavedArrayLength; i++) {
            returnString += (i + 1) + " : " + this.savedName[i] + " : " + this.savedPoints[i] + "\n";
        }
        return returnString;
    }

    /**
     * Reads the file at the path given in the constructor. The file name is:
     * highscore.txt
     */
    public void readHighscoreTable() {
        try {
            Scanner fileReader = new Scanner(new File(this.path + "/highscore.txt"));
            int internalCounter = 0;

            while (fileReader.hasNextLine()) {
                int points = Integer.parseInt(fileReader.nextLine());
                String name = fileReader.nextLine();

                savedName[internalCounter] = name;
                savedPoints[internalCounter] = points;

                internalCounter++;

                this.actualSavedArrayLength++;
            }
        } catch (FileNotFoundException FNFErr) {
            System.out.println("Error loading highscore data");
        }
    }

    /**
     * If the player has enough points, he will be written to the highscores.
     * This method doesn't save the highscore to the file.
     *
     * @param playerName
     * @param finalPoints
     */
    public void writeToHighscore(String playerName, int finalPoints) {
        int finalGamePoints = finalPoints;

        if (this.actualSavedArrayLength == 0) { //If there are no scores in highscore, save players name and score
            this.savedPoints[this.actualSavedArrayLength] = finalGamePoints;
            this.savedName[this.actualSavedArrayLength] = playerName;
            this.actualSavedArrayLength++;
            return;
        }

        if (finalGamePoints > this.savedPoints[this.actualSavedArrayLength - 1] && this.actualSavedArrayLength == 10) {
            this.savedPoints[this.actualSavedArrayLength - 1] = finalGamePoints;
            this.savedName[this.actualSavedArrayLength - 1] = playerName;
        } else {
            this.savedPoints[this.actualSavedArrayLength] = finalGamePoints;
            this.savedName[this.actualSavedArrayLength] = playerName;
            this.actualSavedArrayLength++;
        }

        for (int itemsSorted = 1; itemsSorted < this.savedPoints.length; itemsSorted++) {
            int tempIntVar = this.savedPoints[itemsSorted];
            String tempStringVar = this.savedName[itemsSorted];
            int location = itemsSorted - 1;

            while (location >= 0 && this.savedPoints[location] < tempIntVar) {
                this.savedPoints[location + 1] = this.savedPoints[location];
                this.savedName[location + 1] = this.savedName[location];
                location--;
            }
            this.savedPoints[location + 1] = tempIntVar;
            this.savedName[location + 1] = tempStringVar;
        }

    }

    /**
     * Writes the highscore to the file: highscore.txt.
     *
     * @throws FileNotFoundException
     */
    public void writeToFile() throws FileNotFoundException { //method that writer highscore to the list
        try (PrintWriter fileWriter = new PrintWriter(this.path + "/highscore.txt")) {
            for (int i = 0; i < this.actualSavedArrayLength; i++) {
                fileWriter.println(this.savedPoints[i]);
                fileWriter.println(this.savedName[i]);
            }
        }
    }

    /**
     * Checks if the player has negative points. Checks if the highscore table
     * is less than 10. Finally checks if the player qualities as a highscore
     * owner.
     *
     * @param finalPoints
     * @return
     */
    public boolean isFinalPointsHigher(int finalPoints) {

        if (finalPoints < 0) {
            return false;
        }
        if (this.actualSavedArrayLength < 10) {
            return true;
        } else {
            return finalPoints > this.savedPoints[this.actualSavedArrayLength - 1] && finalPoints > 0;
        }
    }

    /**
     * Gets the names of the highscore owners.
     * @return names of winners.
     */
    public String[] getStringArray() {
        return this.savedName;
    }
    /**
     * Gets the points of the highscore owners.
     * @return points of winners.
     */
    public int[] getIntArray() {
        return this.savedPoints;
    }
}
