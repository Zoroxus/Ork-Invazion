package com.orkin.game.Arme;

/**
 * Represents a Power Klaw weapon in a Warhammer 40000 inspired game.
 * The Power Klaw class encapsulates the unique attributes and functionality of this melee weapon,
 * highlighting its impact and use in the game's combat scenarios.
 */
public class Power_Klaw extends Arme {
    
    /**
     * Constructs a Power Klaw weapon with specific attributes tailored for close combat.
     * These attributes include its melee range, damage output, and other relevant game mechanics.
     */
    public Power_Klaw(){
        super(0, "4", 3, 10, 2, "2", true);
    }

    /**
     * Provides a string representation of the Power Klaw.
     * 
     * @return A string that represents the name of the weapon.
     */
    @Override
    public String toString(){
        return "Power Klaw";
    }
}
