package com.orkin.game.Unite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.orkin.game.Arme.*;

public class Boyz extends Unite {
    public Boyz() {
        super(6, 5, 5, 1, 7, 2, 10,1,new Choppa(),new Slugga(), new Sprite(new Texture("boyz.png")));
    }

    @Override
    public String toString(){
        return "Boyz";
    }
}