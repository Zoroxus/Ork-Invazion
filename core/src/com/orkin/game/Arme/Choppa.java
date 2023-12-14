package com.orkin.game.Arme;

/**
 * Represents a Choppa weapon in a Warhammer 40000 inspired game.
 * The Choppa class is designed to portray a specific type of melee weapon, 
 * often used by certain factions within the game. It defines the weapon's 
 * characteristics for close combat scenarios.
 */
public class Choppa extends Arme {
    
    /**
     * Constructs a Choppa weapon with specified attributes for melee engagement.
     * These attributes include its combat range, damage potential, and other game-related specifics.
     */
    public Choppa(){
        super(0, "3", 3, 4, 1, "1", true);
    }

    /**
     * Provides a string representation of the Choppa weapon.
     * 
     * @return A string that signifies the name of the weapon.
     */
    @Override
    public String toString(){
        return "Choppa";
    }
}