package com.orkin.game.Map;

import com.badlogic.gdx.math.Vector2;
import com.orkin.game.Turn_based_system;
import com.orkin.game.Arme.Arme;
import com.orkin.game.Unite.Unite;

public class Map {
    // Attributes
    private tileData[][] map = new tileData[20][15];
    private Decors decors = new Decors();
    private int sizeX = 20;
    private int sizeY = 15;
    private Turn_based_system game;


    public Map(Turn_based_system game) {
        this.game = game;
        // Generate the Grass Ground
        for (int i = 0; i < this.sizeX; i++) {
            for (int j = 0; j < this.sizeY; j++) {
                /*int randomNum = (int)(Math.random() * 10);  // 0 to 9
                switch(randomNum) {
                    case 0: case 3: case 4: case 5: case 6:case 7:case 8:case 9:
                        map[i][j] = new tileData(i, j, new Grass(), null);
                        break;
                    case 1:
                        map[i][j] = new tileData(i, j, new Water(), null);
                        break;
                    case 2:
                        map[i][j] = new tileData(i, j, new Rock(), null);
                        break;
                    default:
                        System.out.println(randomNum + " : WHY IS THERE A BLANK TILE HERE ?");
                }*/
                map[i][j] = new tileData(i, j, new Grass(), null);
            }
        }
        // Then the decors
        for (int i = 2; i < this.sizeX-4; i+=4) { for (int j = 1; j < this.sizeY-3; j+=5) {
            tileType[][] temp;
            if(j>9 && i>14) {
                System.out.println("Generate a 3x3 decor.");
                temp = decors.generateDecor33();
            } else if(j>9) {
                System.out.println("Generate a 5x3 decor.");
                temp = decors.generateDecor53();
            } else if(i>14){
                System.out.println("Generate a 3x5 decor.");
                temp = decors.generateDecor35();
            } else {
                System.out.println("Generate any decor.");
                temp = decors.generateDecor53and35();
            }
            System.out.println(temp.toString());
            drawDecor(temp, i, j);
        }}
        // Make sure that a little passage leads to the middle from all sides!
        for (int i = 0; i < 8; i+=1) { 
            map[i][7].setType(new Grass());;
            map[i][6].setType(new Grass());
        }
        for (int i = 12; i < 20; i+=1) { 
            map[i][7].setType(new Grass());;
            map[i][6].setType(new Grass());
        }
        for (int j = 0; j < 6; j+=1) { 
            map[9][j].setType(new Grass());;
            map[10][j].setType(new Grass());
        }
        for (int j = 10; j < 15; j+=1) { 
            map[9][j].setType(new Grass());;
            map[10][j].setType(new Grass());
        }
        //this.printMap();
    }

    // Methods
    /**
     * Draws a decor onto the map
     * @param decor
     * @param x
     * @param y
     */
    public void drawDecor(tileType[][] decor, int x, int y) {
        for (int k = 0; k < decor.length; k++) {
            for (int l = 0; l < decor[k].length; l++) {
                if(decor[k][l]!=null) map[x+k][y+l].setType(decor[k][l]);
            }
        }
    }

    // Getters
    public void printMap() {
        //System.out.println(Arrays.deepToString(this.map));
        System.out.println("Map (Tile x:y) : ");
        for (tileData[] tileDatas : map) {
            for (tileData tileData : tileDatas) {
                System.out.print(tileData.getType() + " ");
                tileData.printCoord();
                System.out.print(" | ");
            }
            System.out.println("");
        }
    }

    public tileData[][] getMap() {
        return this.map;
    }

    public String printTile(int i, int j) {
        return map[i][j].getTypeString();
    }
    public String getTileTypeString(int i, int j) {
        return map[i][j].getTypeString();
    }
    public tileType getTileType(int x, int y) {
        return map[x][y].getType();
    }

    public Arme getTileArme(int x, int y) {
        return map[x][y].getArme();
    }
    public Unite getTileUnite(int x, int y) {
        return map[x][y].getUnite();
    }
    public tileData getTile(int x, int y) {
        return map[x][y];
    }


    // Setters
    public void setTileArme(int x, int y, Arme arme) {
        map[x][y].setArme(arme);
    }
    public void setTileUnite(int x, int y, Unite unite) {
        map[x][y].setUnite(unite);
    }
    public boolean moveUnite(int startX, int startY, int endX, int endY) {
        Vector2 start = new Vector2(startX, startY);
        Vector2 end = new Vector2(endX, endY);
        if(map[startX][startY].getUnite()==null){
            System.out.println("No Unit Selected !");
            return false;
        }
        if(map[startX][startY].getUnite().getController()!=game.getTurnPlayer() /*&& game.getCurrentPhase()=="Pre-Game"*/) return false;
        // Check if hasMoved
        if(map[startX][startY].getUnite().hasMoved()==true) return false;
        // Check for good phase
        //if(game.getCurrentPhase()!="Movement") return false;
        // If obstacle to the left : can't go left
        if(endX<startX-1){
            for (int x = endX; x < startX; x++) {
                if(!getTileType(x, startY).isPassable()) {
                    System.out.println("Can't go this way ! It's a "+getTileTypeString(x, startY)+"!");
                    return false;
                }
            }
        }
        // If obstacle to the right : can't go right
        if(startX<endX-1){
            for (int x = startX; x < endX; x++) {
                if(!getTileType(x, startY).isPassable()) {
                    System.out.println("Can't go this way ! It's a "+getTileTypeString(x, startY)+"!");
                    return false;
                }
            }
        }
        // If obstacle to the down : can't go down
        if(endY<startY-1){
            for (int y = endY; y < startY; y++) {
                if(!getTileType(startX, y).isPassable()) {
                    System.out.println("Can't go this way ! It's a "+getTileTypeString(startX, y)+"!");
                    return false;
                }
            }
        }
        // If obstacle to the up : can't go up
        if(startY<endY-1){
            for (int y = startY; y < endY; y++) {
                if(!getTileType(startX, y).isPassable()) {
                    System.out.println("Can't go this way ! It's a "+getTileTypeString(startX, y)+"!");
                    return false;
                }
            }
        }
        Unite temp = getTileUnite(startX, startY);
        if(distance(start,end)*3>temp.getM()) {
            System.out.println("This unit can't move this far !");
            System.out.println("Distance :"+distance(start,end)*3+"\"");
            return false;
        }
        switch (getTileTypeString(endX, endY)) {
            case "Grass":
                System.out.println("Moving the unit !");
                setTileUnite(startX, startY, null);
                setTileUnite(endX, endY, temp);
                getTileUnite(endX, endY).setMoved(true);
                return true;
            default:
                System.out.println("Can't move here !");
                break;
        }
        System.out.println("Distance :"+distance(start,end)*3+"\"");
        return false;
    }



    /**
     * Check if you can move a Unit from a tile to another. The starting tile must have a Unit else it will return false.
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return true when the unit can move from the start tile to the end tile
     */
    public boolean canMoveUnite(int startX, int startY, int endX, int endY) {
        Vector2 start = new Vector2(startX, startY);
        Vector2 end = new Vector2(endX, endY);
        if(map[startX][startY].getUnite()==null) return false;
        Unite temp = getTileUnite(startX, startY);
        // Check if turn player's unit
        if(map[startX][startY].getUnite().getController()!=game.getTurnPlayer() /*&& game.getCurrentPhase()=="Pre-Game"*/) return false;
        // Check if hasMoved
        if(map[startX][startY].getUnite().hasMoved()==true) return false;
        // Check for good phase
        //if(game.getCurrentPhase()!="Movement") return false;
        if(distance(start,end)*3>temp.getM()) {
            return false;
        }
        if(map[endX][endY].getUnite()!=null) return false;
        // If obstacle to the left : can't go left
        if(endX<startX-1){
            for (int x = endX; x < startX; x++) {
                if(!getTileType(x, startY).isPassable()) {
                    //System.out.println("Can't go LEFT !");
                    return false;
                }
            }
        }
        // If obstacle to the right : can't go right
        if(startX<endX-1){
            for (int x = startX; x < endX; x++) {
                if(!getTileType(x, startY).isPassable()) {
                    //System.out.println("Can't go RIGHT !");
                    return false;
                }
            }
        }
        // If obstacle downwards : can't go down
        if(endY<startY-1){
            for (int y = endY; y < startY; y++) {
                if(!getTileType(startX, y).isPassable()) {
                    //System.out.println("Can't go DOWN !");
                    return false;
                }
            }
        }
        // If obstacle upwards : can't go up
        if(startY<endY-1){
            for (int y = startY; y < endY; y++) {
                if(!getTileType(startX, y).isPassable()) {
                    //System.out.println("Can't go UP !");
                    return false;
                }
            }
        }
        // 
        switch (getTileTypeString(endX, endY)) {
            case "Grass":
                return true;
            default:
                return false;
        }
    }

    public boolean attack(int startX, int startY, int endX, int endY) {
        Vector2 start = new Vector2(startX, startY);
        Vector2 end = new Vector2(endX, endY);
        // No attacker OR No defender = false
        if(map[startX][startY].getUnite()==null) {
            //System.out.println("No Attacker !");
            return false;
        }
        if (map[endX][endY].getUnite()==null) {
            //System.out.println("No Defender !");
            return false;
        }
        Unite tempAttacker = getTileUnite(startX, startY);
        Unite tempDefender = getTileUnite(endX, endY);
        // Attacker not of Player = False
        if(tempAttacker.getController()!=game.getTurnPlayer()) {
            System.out.println("I'm not in your faction !");
            return false;
        }
        // Attacker has fought = False
        if(map[startX][startY].getUnite().hasFought()==true || map[startX][startY].getUnite().hasShoot()==true) {
            System.out.println("I already have fought already !");
            return false;
        }
        // Defender of Player = False
        if(tempDefender.getController()==game.getTurnPlayer()) {
            return false;
        }
        Arme tempWeapon;
        int portee;
        if(distance(start, end)<1) {
            System.out.println("Can't attack myself !");
            return false;
        }

        // If obstacle to the left : can't fire left
        if(endX<startX-1){
            for (int x = endX; x < startX; x++) {
                if(getTileType(x, startY).isObscurant()) {
                    System.out.println("Can't attack through a wall !");
                    return false;
                }
            }
        }
        // If obstacle to the right : can't fire right
        if(startX<endX-1){
            for (int x = startX; x < endX; x++) {
                if(getTileType(x, startY).isObscurant()) {
                    System.out.println("Can't attack through a wall !");
                    return false;
                }
            }
        }
        // If obstacle downwards : can't fire down
        if(endY<startY-1){
            for (int y = endY; y < startY; y++) {
                if(getTileType(startX, y).isObscurant()) {
                    System.out.println("Can't attack through a wall !");
                    return false;
                }
            }
        }
        // If obstacle upwards : can't fire up
        if(startY<endY-1){
            for (int y = startY; y < endY; y++) {
                if(getTileType(startX, y).isObscurant()) {
                    System.out.println("Can't attack through a wall !");
                    return false;
                }
            }
        }

        System.out.println("Distance : " + distance(start,end));
        if(distance(start, end)<1.5) {
            tempWeapon = tempAttacker.getMeleeWeapon();
            portee = tempWeapon.getRange();
        } else {
            tempWeapon = tempAttacker.getRangeWeapon();
            portee = tempWeapon.getRange();
        }
        System.out.println("Range : " + portee);
        if(distance(start,end)*3>portee && tempWeapon.getismelee()==false) {
            System.out.println("Not enough range.");
            return false;
        }
        System.out.println("Attacking target Unit !");
        boolean killed = tempWeapon.attack(map[startX][startY], map[endX][endY]);
        tempAttacker.setFought(true);
        tempAttacker.setShoot(true);
        map[startX][startY].getUnite().setFought(true);
        return true;
    }

    /**
     * Returns true when the Unit from startX:startY can attack the Unit from endX:endY
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return boolean
     */
    public boolean canAttack(int startX, int startY, int endX, int endY) {
        Vector2 start = new Vector2(startX, startY);
        Vector2 end = new Vector2(endX, endY);
        // No attacker OR No defender = false
        if(map[startX][startY].getUnite()==null) {
            //System.out.println("No Attacker !");
            return false;
        }
        if (map[endX][endY].getUnite()==null) {
            //System.out.println("No Defender !");
            return false;
        }
        //System.out.println("Yes Defender !");
        Unite tempAttacker = getTileUnite(startX, startY);
        Unite tempDefender = getTileUnite(endX, endY);
        // Attacker not of Player = False
        if(tempAttacker.getController()!=game.getTurnPlayer()) {
            //System.out.println("Attacker not of our faction !");
            return false;
        }
        // Attacker has fought = False
        if(map[startX][startY].getUnite().hasFought()==true || map[startX][startY].getUnite().hasShoot()==true) {
            //System.out.println("Attacker has fought already !");
            return false;
        }
        // Defender of Player = False
        if(tempDefender.getController()==game.getTurnPlayer()) {
            return false;
        }
        Arme tempWeapon;
        int portee;
        // Attacker == Defender = False
        if(distance(start, end)<1) {
            //System.out.println("This is myself !");
            return false;
        }
        // If obstacle to the left : can't fire left
        if(endX<startX-1){
            for (int x = endX; x < startX; x++) {
                if(getTileType(x, startY).isObscurant()) {
                    return false;
                }
            }
        }
        // If obstacle to the right : can't fire right
        if(startX<endX-1){
            for (int x = startX; x < endX; x++) {
                if(getTileType(x, startY).isObscurant()) {
                    return false;
                }
            }
        }
        // If obstacle downwards : can't fire down
        if(endY<startY-1){
            for (int y = endY; y < startY; y++) {
                if(getTileType(startX, y).isObscurant()) {
                    return false;
                }
            }
        }
        // If obstacle upwards : can't fire up
        if(startY<endY-1){
            for (int y = startY; y < endY; y++) {
                if(getTileType(startX, y).isObscurant()) {
                    //System.out.println("Obstacle upside !");
                    return false;
                }
            }
        }
        //System.out.println("Ranged : "+tempAttacker.getRangeWeapon().toString());
        // Defender beside = Melee
        if(distance(start, end)*3<5) {
            tempWeapon = tempAttacker.getMeleeWeapon();
            portee = tempWeapon.getRange();
        // Defender too far = Range
        } else {
            tempWeapon = tempAttacker.getRangeWeapon();
            portee = tempWeapon.getRange();
        }
        // Much too far = false
        if(distance(start,end)*3>portee && !tempWeapon.getismelee()) {
            //System.out.println("Weapon : "+tempWeapon.toString()+" | Range : "+portee+" | Distance : "+distance(start,end)*3);
            //System.out.println(distance(new Vector2(1,1),new Vector2(2,2))*3);
            //System.out.println("Too far !");
            return false;
        }
        //System.out.println("Can attack !");
        return true;
    }

    public boolean canAttackAny(int startX, int startY) {
        for (tileData[] tileDatas : map) {
            for (tileData tileData : tileDatas) {
                if(canAttack(startX, startY, tileData.getX(), tileData.getY())) return true;
            }
        }
        return false;
    }

    public double distance(Vector2 object1, Vector2 object2){
        return Math.sqrt(Math.pow(((object2.x+32) - (object1.x+32)), 2) + Math.pow(((object2.y+32) - (object1.y+32)), 2));
    }
}