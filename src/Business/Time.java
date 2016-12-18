/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * Time class is used to store the time and time elapsed of a playthrough.
 *
 * @author amaliehoff
 */
public class Time {

    private int time; //this is time in minuts
    private int timeElapsed; //total time elapsed
    private int endTime; //the hour that the game i won on.

    /**
     * Create a new Time object. The object has a time: ie 18*60 = 18:00. endHour = time to solve the mystery. i.e. 14*60.
     *
     * @param time int, time of the day
     * @param endHour int, time to solve.
     */
    public Time(int time, int endHour) {
        this.time = time;//this is the constructor for time in minuts
        this.timeElapsed = 0;
        this.endTime = endHour;
    }

    /**
     * Adds or removes time. if integer is negative, we get more time.
     *
     * @param time int, minutes
     */
    void addMinute(int time) {
        this.time += time;//adds some minutes to the clock
        timeElapsed += time; //adds minutes to total elapsed time.
        if (this.time >= 1440) { //resets the clock
            this.time -= 1440;//when the time reaches midnight the clock wil count up from 00:00 --> 08:00
        }
    }

    /**
     * Returns the hour of the time. I.e 18.
     *
     * @return int, hour
     */
    private int getHour() {
        int hour = (int) (time / 60); //calculate hours from the clock
        return hour;
    }

    /**
     * Returns the mminutes of the time
     *
     * @return
     */
    private int getMinutes() {
        int minutes = (int) (time % 60); //calculate minutes.
        return minutes;
    }

    /**
     * Returns the time in a humanly readable fashion.
     *
     * @return String, time
     */
    String getTime() {
        int hour = getHour();//get the hour of the current time
        int minutes = getMinutes();//get the muinits of the current time
        String timeString = "";//start with an empty string

        if (hour < 10) {
            timeString = "0" + Integer.toString(hour);//set the 0 ifront of fx. 9 i 09:00
        } else {
            timeString = Integer.toString(hour);// if the time is more than 10 it vil print out the time without the 0 fx. 10:00
        }
        timeString += ":";// sets the : in fx. 10:00 
        if (minutes < 10) {
            timeString += "0" + Integer.toString(minutes);//set the 0 ifront of fx. 9 i 09:00
        } else {
            timeString += Integer.toString(minutes);// if the time is more than 10 it vil print out the time without the 0 fx. 10:00
        }
        return timeString;//returns the time in a readabel manner.
    }

    /**
     * Returns the time elapsed since the beginning.
     *
     * @return int, time elapsed
     */
    int getTimeElapsed() {
        return timeElapsed;
    }

    /**
     * Get total time restriction
     *
     * @return int, endTime
     */
    int getEndHour() {
        return endTime;
    }

    /**
     * Returns points generated when won
     *
     * @return int, points gianed when won.
     */
    int PointsIfWin() {
        int score = (int) ((14 * 60 - timeElapsed) / 4);
        return score;
    }
}
