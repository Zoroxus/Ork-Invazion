package com.orkin.game.Unite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.orkin.game.Arme.*;

public class Nobz extends Unite {

    public Nobz() {
        super(6,5,4,2,7,1,5,1,new Power_Klaw(),new Slugga(), new Sprite(new Texture("nobz.png")));
    }

    @Override
    public String toString(){
        return "Nobz";
    }
}