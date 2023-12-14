package com.orkin.game.Arme;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Represents a Shoota weapon in a Warhammer 40000 inspired game.
 * The Shoota class embodies the specific attributes and behaviors of this type of weapon,
 * detailing its operational features in the game environment.
 */
public class Shoota extends Arme {
    
    /**
     * Constructs a Shoota weapon with standard game-specific attributes.
     * This sets up the weapon with values indicating its range, damage potential, and other game-related characteristics.
     */
    public Shoota(){
        super(18, "2", 5, 4, 0, "1", false);
        this.sprite = new Sprite(new Texture("Shoota.png"));
    }

    /**
     * Provides a string representation of the Shoota weapon.
     * 
     * @return A string that represents the name of the weapon.
     */
    @Override
    public String toString(){
        return "Shoota";
    }
}
