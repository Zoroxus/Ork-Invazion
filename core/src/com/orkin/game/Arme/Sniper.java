package com.orkin.game.Arme;

public class Sniper extends Arme{
    public Sniper(){
        super(100,"25",1,25,10,"50",false);
        // Range    24" = 8 tiles
        // A        2
        // CT       3+
        // F        4
        // AP       1
        // D        1
        // Melee    false
    }

    @Override
    public String toString(){
        return "Sniper";
    }
}
