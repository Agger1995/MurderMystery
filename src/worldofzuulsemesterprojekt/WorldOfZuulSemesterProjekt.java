/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Begin the game class
 *
 * @author Agger
 */
public class WorldOfZuulSemesterProjekt {

    /**
     * Main method, the game is initialized from here, nothing more.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        boolean chosen = false;

        File file = new File("scenarios/");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        int i = 0;
        System.out.println("Please choose one of the following scenarios to play.");
        System.out.println("You choose by typing the ID of the scenario you wish to play.");
        for (String workingString : directories) {
            System.out.println(i + ": " + workingString);
            i++;
        }

        int chosenScenario = -1;
        while (!chosen) {
            try {
                chosenScenario = userInput.nextInt();
                if (!(chosenScenario >= directories.length) || chosenScenario < 0) {
                    chosen = true;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (InputMismatchException IMEerr) {
                System.out.println("Please choose on of the following scenarios by ID");
                int j = 0;
                for (String workingString : directories) {
                    System.out.println(j + ": " + directories[j]);
                    j++;
                }
                userInput.next();
            } catch (IllegalArgumentException IAEerr) {
                System.out.println("Please choose on of the following scenarios by ID");
                int j = 0;
                for (String workingString : directories) {
                    System.out.println(j + ": " + directories[j]);
                    j++;
                }
                chosenScenario = -1;
            }
        }

        String scenarioPath = "scenarios/" + directories[chosenScenario];
        Riddle.setPath(scenarioPath); //Sets the path for the riddles.

        LogBook log = new LogBook();
        Game game = new Game(log, scenarioPath);
        try {
            game.play();
        } catch (FileNotFoundException fnferr) {
            System.out.println("Error in highscore system.");
        }
    }
}
