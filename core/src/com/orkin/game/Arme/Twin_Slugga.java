package com.orkin.game.Arme;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * This class provides the basic functionalities of the Twin_Slugga weapon, 
 * including its attributes and behaviors within the game context.
 */
public class Twin_Slugga extends Arme {
    
    /**
     * Constructs a Twin_Slugga weapon with predefined game attributes.
     */
    public Twin_Slugga(){
        super(12, "2", 5, 4, 0, "1", false);
        this.sprite = new Sprite(new Texture("Twin_Shoota.png"));
    }

    /**
     * Returns the name of the weapon.
     * 
     * @return A string representing the weapon's name.
     */
    @Override
    public String toString(){
        return "Twin_Slugga";
    }
}
