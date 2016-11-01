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
    private PrintWriter fileWrite;
    private int finalPoints;
    private String playerName;
    
    public Highscore() throws FileNotFoundException{
        //this.fileWrite = new PrintWriter("highscore.txt");
        this.finalPoints = 0;
        this.playerName = "";
    }
    
    public void readHighscore(){
        try (PrintWriter zapis = new PrintWriter("highscore.txt")){
            zapis.println("test sejt program");
            zapis.println(123);
            zapis.println(1323);
            zapis.println(13131313);
            zapis.close();
            Scanner readFile = new Scanner(new File("highscore.txt"));
            //this.fileWrite.println("1-laura-100");
            //this.fileWrite.close();
            
            while(readFile.hasNext()){
                System.out.println("Highscore table:");
                String tempString = readFile.nextLine();
                
                System.out.println(tempString);
                System.out.println(tempString.substring(0, tempString.indexOf("-")) + ": " + tempString.substring(tempString.indexOf("-"), tempString.lastIndexOf(" ")) + "-" + tempString.substring(tempString.lastIndexOf(" points")));
            }
        } catch (FileNotFoundException err){
            System.out.println("Highscore table could not be found!");
        }
        
    }
    
    public void setPlayerName(String name){
        this.playerName = name;
    }
    
    public void setFinalPoints(int finalPoints){
        this.finalPoints = finalPoints;
    }
}
