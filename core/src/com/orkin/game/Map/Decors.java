package com.orkin.game.Map;

public class Decors {
    private tileType[][] nothing = 
    {
        {null, null, null},
        {null, null, null},
        {null, null, null},
        {null, null, null},
        {null, null, null}
    };
    private tileType[][] WATER = 
    {
        {new Water(), new Water(), new Water()},
        {new Water(), new Water(), new Water()},
        {new Water(), new Water(), new Water()}
    };
    private tileType[][] rockLongT0 = 
    {
        {null, null, null, null, new Rock()},
        {new Rock(), new Rock(), new Rock(), new Rock(), new Rock()},
        {null, null, null, null, new Rock()}
    };
    private tileType[][] rockLongT90 = 
    {
        {null, new Rock(), null},
        {null, new Rock(), null},
        {null, new Rock(), null},
        {null, new Rock(), null},
        {new Rock(), new Rock(), new Rock()}
    };
    private tileType[][] rockLongT180 = 
    {
        {new Rock(), null, null, null, null},
        {new Rock(), new Rock(), new Rock(), new Rock(), new Rock()},
        {new Rock(), null, null, null, null}
    };
    private tileType[][] rockLongT270 = 
    {
        {new Rock(), new Rock(), new Rock()},
        {null, new Rock(), null},
        {null, new Rock(), null},
        {null, new Rock(), null},
        {null, new Rock(), null}
    };
    private tileType[][] smallTunnel0 = 
    {
        {new Rock(), new Rock(), new Rock()},
        {null, null, null},
        {new Rock(), new Rock(), new Rock()}
    };
    private tileType[][] smallTunnel90 = 
    {
        {new Rock(), null, new Rock()},
        {new Rock(), null, new Rock()},
        {new Rock(), null, new Rock()}
    };
    private tileType[][] buildTunnel0 = 
    {
        {new Building(), new Grass(), new Building()},
        {new Building(), new Grass(), new Building()},
        {new Building(), new Grass(), new Building()},
        {new Building(), new Grass(), new Building()},
        {new Building(), new Grass(), new Building()}
    };
    private tileType[][] buildTunnel90 = 
    {
        {new Building(),  new Building(), new Building(), new Building(), new Building()},
        {new Grass(), new Grass(), new Grass(), new Grass(), new Grass()},
        {new Building(),  new Building(), new Building(), new Building(), new Building()}
    };

    /**
     * @return a random decor of size 3x5
     */
    public tileType[][] generateDecor35() {
        int randomNum = (int)(Math.random() * 4);  // 0 to 3
        switch(randomNum) {
            case 0:
                return rockLongT0;
            case 1:
                return rockLongT180;
            case 2:
                return buildTunnel90;
            default:
                System.out.println("Oh ! A 3x3 !");
                return generateDecor33();
        }
    }

    /**
     * @return a random decor of size 5x3
     */
    public tileType[][] generateDecor53() {
        int randomNum = (int)(Math.random() * 4);  // 0 to 3
        switch(randomNum) {
            case 0:
                return rockLongT90;
            case 1:
                return rockLongT270;
            case 2:
                return buildTunnel0;
            default:
                System.out.println("Oh ! A 3x3 !");
                return generateDecor33();
        }
    }

    /**
     * @return a random decor of size 5x3 or 3x5
     */
    public tileType[][] generateDecor53and35() {
        int randomNum = (int)(Math.random() * 5);  // 0 to 4
        switch(randomNum) {
            case 0:
                return rockLongT90;
            case 1:
                return rockLongT0;
            case 2:
                return rockLongT180;
            case 3:
                return rockLongT270;
            default:
                System.out.println("Oh ! A 3x3 !");
                return generateDecor33();
        }
    }

    /**
     * @return a random decor of size 3x3
     */
    public tileType[][] generateDecor33() {
        int randomNum = (int)(Math.random() * 4);  // 0 to 3
        switch(randomNum) {
            case 0:
                return WATER;
            case 1:
                return smallTunnel0;
            case 2:
                return smallTunnel90;
            default:
                return nothing;
        }
    }
}