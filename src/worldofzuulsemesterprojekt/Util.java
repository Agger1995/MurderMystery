/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulsemesterprojekt;

import java.util.ArrayList;

/**
 *
 * @author kristian
 */
public class Util {

    private static int stringSplitSize = 50;

    public static String stringConvertSmaller(String string) {
        String[] temp = string.split(" ");
        String temp_string = "";
        int counter = 0;
        for(int i = 0; i < temp.length; i++) {
            temp_string+=temp[i];
            temp_string+=" ";
            counter+=temp[i].length();
            if(counter >= stringSplitSize) {
                counter = 0;
                temp_string+="\n";
            }
        }
        if(temp_string!="") {
            temp_string = temp_string.substring(0, temp_string.length()-1);
        }
        return temp_string;
    }
}
