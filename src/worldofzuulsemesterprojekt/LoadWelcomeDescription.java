/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kristian
 */
public class LoadWelcomeDescription {

    private ArrayList<String> descriptions;
    private String path;
    private PrintUtility printer;

    public LoadWelcomeDescription(String path, PrintUtility printer) {
        this.descriptions = new ArrayList();
        this.path = path;
        this.printer = printer;
        load();
        set();
    }
    
    private void load() {
        File file = new File(path + "/" + "description.txt");
        Scanner scanner = null; 

        try {
            scanner = new Scanner(file); // scanner for the file
        } catch (FileNotFoundException ex) {
            try {
                //if not such file exists create it.
                file.createNewFile();
            } catch (IOException ex1) {
                Logger.getLogger(LoadItems.class.getName()).log(Level.SEVERE, null, ex1);
                return;
            }
        }
        while (scanner.hasNextLine()) {
            descriptions.add(Util.stringConvertSmaller(scanner.nextLine()));
        }
    }
    
    private void set() {
        printer.setIntroMessage(descriptions);
    }
}
