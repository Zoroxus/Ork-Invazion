package com.orkin.game.Unite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.orkin.game.Arme.*;

public class Warboss extends Unite{
        public Warboss() {
        super(6,5,4,6,6,1,1,1,new Power_Klaw(),new Twin_Slugga(), new Sprite(new Texture("warboss.png")));
    }

    @Override
    public String toString(){
        return "Warboss";
    }
}
