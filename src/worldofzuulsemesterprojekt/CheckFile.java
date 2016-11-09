/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kristian
 */
public class CheckFile {

    public static boolean rightFormat(File file, int expectedLinesPerObject) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CheckFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(scanner!=null) {
            int count = 0;
            while(scanner.hasNextLine()) {
                scanner.nextLine();
                count++;
            }
            return count % expectedLinesPerObject == 0; // returns false if error in file
        } else {
            return false;
        }
    }
}
