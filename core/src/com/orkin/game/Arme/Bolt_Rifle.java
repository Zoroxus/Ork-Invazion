package com.orkin.game.Arme;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a Bolt Rifle in a Warhammer 40000 inspired game.
 * The Bolt Rifle class is designed to depict a long-range firearm, commonly used in the game.
 * It specifies the weapon's attributes such as range, accuracy, and damage, tailored for ranged combat.
 */
public class Bolt_Rifle extends Arme {
    
    public Bolt_Rifle(){
        super(24, "2", 3, 4, 1, "1", false);
        // Range    24" = 8 tiles
        // A        2
        // CT       3+
        // F        4
        // AP       1
        // D        1
        // Melee    false
        this.sprite = new Sprite(new Texture("bolter.png"));
    }

    /**
     * Provides a string representation of the Bolt Rifle.
     * 
     * @return A string that denotes the name of the weapon.
     */
    @Override
    public String toString(){
        return "Bolt Rifle";
    }
}
