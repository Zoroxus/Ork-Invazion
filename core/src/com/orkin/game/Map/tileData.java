package com.orkin.game.Map;

import com.orkin.game.Arme.*;
import com.orkin.game.Unite.*;

public class tileData {
    // Attributes
    public int x;
    public int y;
    private tileType type;
    private Arme arme;
    private Unite unite;

    // Constructors
    /**
     * @param x
     * @param y
     * @param type
     * @param arme
     */
    public tileData(int x, int y, tileType type, Arme arme){
        this.x = x;
        this.y = y;
        this.type = type;
        this.arme = arme;
    }

    // Methods
    /**
     * prints the coordonates of the Tile as such : "x:y\n"
     */
    public void printlnCoord() {
        System.out.println(this.x + ":" + this.y);
    }
    /**
     * prints the coordonates of the Tile as such : "x:y"
     */
    public void printCoord() {
        System.out.print(this.x + ":" + this.y);
    }

    // Getters
    public String getTypeString() {
        return type.getName();
    }
    public tileType getType() {
        return this.type;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public Arme getArme() {
        return this.arme;
    }
    public Unite getUnite() {
        return this.unite;
    }

    /**
     * returns true if a Weapon is in the Tile
     * @return
     */
    public boolean hasArme() {
        if(arme == null) {
            return false;
        }
        return true;
    }

    // Setters
    /**
     * set the Weapon in the Tile (aka: dropped) to the given one
     * @param arme
     */
    public void setArme(Arme arme) {
        this.arme = arme;
    }
    /**
     * set the Unit in the Tile to the given one
     * @param unite
     */
    public void setUnite(Unite unite) {
        this.unite = unite;
    }
    /**
     * set the tileType of the tile
     * @param type
     */
    public void setType(tileType type) {
        this.type = type;
    }
}
