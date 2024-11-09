package com.orkin.game.Unite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.orkin.game.Arme.*;

public class Intercessor extends Unite{
    public Intercessor() {
        super(6,4,3,2,6,2,5,1,new Close_Combat_Weapons(),new Boltgun(), new Sprite(new Texture("SpaceMarineSprite.png")));
        this.knuckles = new Sprite(new Texture("knuckles_right.png"));
    }

    @Override
    public String toString(){
        return "Intercessors";
    }
}
