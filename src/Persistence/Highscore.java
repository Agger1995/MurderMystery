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
 *
 * @author chris
 */
public class Highscore {
    private String[] savedName;
    private int[] savedPoints;
    private int actualSavedArrayLength;
    private String path;
    
    public Highscore(String path){
        this.savedName = new String[10];
        this.savedPoints = new int[10];
        this.actualSavedArrayLength = 0;
        this.path = path;
    }
    
    public String getHighscoreData(){
        String returnString = "";
        for(int i = 0; i < this.actualSavedArrayLength; i++){
            returnString += (i + 1) + " : " + this.savedName[i] + " : " + this.savedPoints[i] + "\n";
        }
        return returnString;
    }
    
    public void readHighscoreTable() {
        try{
            Scanner fileReader = new Scanner(new File(this.path + "/highscore.txt"));
            int internalCounter = 0;

            while(fileReader.hasNextLine()){
                int points = Integer.parseInt(fileReader.nextLine());
                String name = fileReader.nextLine();

                savedName[internalCounter] = name;
                savedPoints[internalCounter] = points;

                internalCounter++;

                this.actualSavedArrayLength++;
            }
        } catch(FileNotFoundException FNFErr){
            System.out.println("Error loading highscore data");
        }
    }
    
    public void writeToHighscore(String playerName, int finalPoints){
        int finalGamePoints = finalPoints;
        
        if(finalGamePoints > this.savedPoints[this.actualSavedArrayLength - 1] && this.actualSavedArrayLength == 10){
            this.savedPoints[this.actualSavedArrayLength - 1] = finalGamePoints;
            this.savedName[this.actualSavedArrayLength - 1] = playerName;
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
        try (PrintWriter fileWriter = new PrintWriter(this.path + "/highscore.txt")){
            for(int i = 0; i < this.actualSavedArrayLength; i++){
                fileWriter.println(this.savedPoints[i]);
                fileWriter.println(this.savedName[i]);
            }
        }
    }
    
    public boolean isFinalPointsHigher(int finalPoints){
        if(this.actualSavedArrayLength < 10){
            return true;
        } else {
            return finalPoints > this.savedPoints[this.actualSavedArrayLength - 1];
        }
    }
    
    public String[] getStringArray(){
        return this.savedName;
    }
    
    public int[] getIntArray(){
        return this.savedPoints;
    }
}