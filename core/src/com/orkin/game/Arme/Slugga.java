package com.orkin.game.Arme;

/**
 * Represents a Slugga weapon in a Warhammer 40000 inspired game. 
 * This class encapsulates the characteristics and functionalities of the Slugga weapon, 
 * defining its role and usage within the game's setting.
 */
public class Slugga extends Arme {
    
    /**
     * Constructs a Slugga weapon with default game-specific attributes.
     * Initializes the weapon with certain values that define its performance in the game.
     */
    public Slugga(){
        super(12, "1", 5, 4, 0, "1", false);
    }

    /**
     * Provides a string representation of the Slugga weapon.
     * 
     * @return A string indicating the weapon's name.
     */
    @Override
    public String toString(){
        return "Slugga";
    }
}
