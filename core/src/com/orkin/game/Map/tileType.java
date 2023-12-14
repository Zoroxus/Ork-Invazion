package com.orkin.game.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class tileType {
    protected String name;
    protected float[] color = {1, 1, 1, 1};
    protected boolean obscurant = false;
    protected boolean passable = true;
    protected TextureRegion texture;

    /**
     * Constructor for tileType, with everything set manually
     * @param name
     * @param red
     * @param green
     * @param blue
     * @param alpha
     * @param obscurant
     * @param passable
     */
    protected tileType(String name, float red, float green, float blue, float alpha, boolean obscurant, boolean passable) {
        this.name = name;
        this.color[0] = red;
        this.color[1] = green;
        this.color[2] = blue;
        this.color[3] = alpha;
        this.obscurant = obscurant;
        this.passable = passable;
    }

    protected String getName() {
        return this.name;
    }
    protected boolean isPassable() {
        return passable;
    }
    protected boolean isObscurant() {
        return obscurant;
    }
}
