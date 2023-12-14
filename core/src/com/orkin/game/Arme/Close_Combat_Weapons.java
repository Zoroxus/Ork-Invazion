package com.orkin.game.Arme;

/**
 * Represents the generic class of Close Combat Weapons in a Warhammer 40000 inspired game.
 * This class covers a range of melee weapons, providing a base template for close-quarters combat weaponry,
 * with attributes suitable for hand-to-hand engagements in the game.
 */
public class Close_Combat_Weapons extends Arme {
    
    /**
     * Constructs a generic Close Combat Weapon with default attributes for melee combat.
     * These attributes dictate its effectiveness in close-range combat scenarios within the game.
     */
    public Close_Combat_Weapons(){
        super(0, "3", 3, 4, 0, "1", true);
    }

    /**
     * Provides a string representation of a generic Close Combat Weapon.
     * 
     * @return A string that represents the class of close combat weapons.
     */
    @Override
    public String toString(){
        return "Close Combat Weapon";
    }
}
