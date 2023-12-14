package com.orkin.game.Unite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.orkin.game.Arme.*;

public class Deathmark extends Unite{
    public Deathmark() {
        super(200,4,3,2,6,2,5,1,new Bolt_Rifle(),new Close_Combat_Weapons(), new Sprite(new Texture("marine.png")));
    }

    @Override
    public String toString(){
        return "Intercessors";
    }
}