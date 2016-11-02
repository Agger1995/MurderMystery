/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 * @author chris
 */
public class Highscore {
    private String[] savedName;
    private int[] savedPoints;
    private int actualSavedArrayLength;
    
    public Highscore(){
        this.savedName = new String[11];
        this.savedPoints = new int[11];
        this.actualSavedArrayLength = 0;
    }
    
    public void createHighscoreFile() throws FileNotFoundException{
        File highscoreFile = new File("highscore.txt");
    }
    
    public void readHighscoreTable() throws FileNotFoundException{
        Scanner fileReader = new Scanner(new File("highscore.txt"));
        int internalCounter = 0;
        
        System.out.println("Highscore table:");
        
        while(fileReader.hasNextLine()){
            int points = Integer.parseInt(fileReader.nextLine());
            String name = fileReader.nextLine();
            
            savedName[internalCounter] = name;
            savedPoints[internalCounter] = points;

            System.out.println((internalCounter + 1) + ": " + savedName[internalCounter] + ": " + savedPoints[internalCounter]);
            
            internalCounter++;
            
            this.actualSavedArrayLength++;
        }
    }
    
    public void writeToHighscore(String playerName, int finalPoints){
        int finalGamePoints = finalPoints;
        
        if(finalGamePoints > this.savedPoints[this.actualSavedArrayLength] && this.actualSavedArrayLength == 10){
            this.savedPoints[this.actualSavedArrayLength] = finalGamePoints;
            this.savedName[this.actualSavedArrayLength] = playerName;
        } else {
            this.savedPoints[this.actualSavedArrayLength + 1] = finalGamePoints;
            this.savedName[this.actualSavedArrayLength + 1] = playerName;
            this.actualSavedArrayLength++;
        }
        
        for(int itemsSorted = 1; itemsSorted < this.savedPoints.length; itemsSorted++){
            int tempIntVar = this.savedPoints[itemsSorted];
            String tempStringVar = this.savedName[itemsSorted];
            int location = itemsSorted - 1;
            
            while(location >= 0 && this.savedPoints[location] < tempIntVar){
                this.savedPoints[location + 1] = this.savedPoints[location];
                this.savedName[location + 1] = this.savedName[location];
                location--;
            }
            this.savedPoints[location + 1] = tempIntVar;
            this.savedName[location + 1] = tempStringVar;
        }
        
        
    }
    
    public void writeToFile() throws FileNotFoundException{
        try (PrintWriter fileWriter = new PrintWriter("highscore.txt")){
            for(int i = 0; i < this.actualSavedArrayLength; i++){
                fileWriter.println(this.savedPoints[i]);
                fileWriter.println(this.savedName[i]);
            }
        }
    }
    
    public boolean isFinalPointsHigher(int finalPoints){
        return finalPoints > this.savedPoints[this.actualSavedArrayLength];
    }
}