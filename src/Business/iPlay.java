/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 *
 * @author chris
 */
public interface iPlay {
    public void processCommand(Command cmd);
    
    // Methods to run after every action
    public boolean timeRunsOut();
    public void generateRandomPersonMovement();
}
