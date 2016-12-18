/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

/**
 * An interface, that tells whether or not object is interactable.
 *
 * @author chris
 */
public interface Interactable {

    public String getName();//method that get a name of all Items and Persons

    @Override
    public String toString();

    public String getType();
}
