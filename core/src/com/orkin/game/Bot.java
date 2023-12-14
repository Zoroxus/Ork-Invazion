package com.orkin.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.orkin.game.Map.Map;
import com.orkin.game.Map.tileData;
import com.orkin.game.Unite.Unite;

class Bot extends Player {
    //private tileData[][] route;
    private Map map;
    private ArrayList<tileData> allUnits = new ArrayList<>();
    private ArrayList<tileData> allEnemy = new ArrayList<>();

    public Bot(Map map) {
        super("CPU");
        this.map = map;
    }

    public void takeTurn(Turn_based_system game) {
        allUnits.removeAll(allUnits);
        allUnits.addAll(getAllUnit());
        //System.out.println("CPU: Getting all ennemy units . . .");
        allEnemy.removeAll(allEnemy);
        allEnemy.addAll(getAllEnemy());
        printAllUnitArray(allUnits);
        printAllUnitArray(allEnemy);
        for (tileData ally : allUnits) {
            if(allEnemy.size()==0) continue;
            //System.out.println("CPU: playing unit " + ally.getUnite().toString());
            //if(allEnemy.size()!=0) {
                tileData enemy = getNearestEnnemy(ally);
                tileData toMove = getBestTile(ally, enemy);
                if(map.canAttack(ally.x, ally.y, enemy.x, enemy.y)) {
                    System.out.println("CPU: will attack " + enemy.getUnite().toString());
                    map.attack(ally.x, ally.y, enemy.x, enemy.y);
                    continue;
                }
                //System.out.println("CPU: will move to " + toMove.x + ":" + toMove.y);
                map.moveUnite(ally.x, ally.y, toMove.x, toMove.y);
            //}
            //if(enemy.getUnite()!=null) System.out.println("CPU: will target " + enemy.getUnite().toString());
            //else System.out.println("CPU: will target NOBODY");
        }
    }

    public ArrayList<tileData> getAllUnit() {
        //System.out.println("CPU: Setting up a Unit Array . . .");
        ArrayList<tileData> unitArray = new ArrayList<>();
        //System.out.println("CPU: Getting all my units . . .");
        for (tileData[] tileData : map.getMap()) {
            for (tileData tile : tileData) {
                if(tile.getUnite()==null || tile.getUnite().getController()!=this) continue;
                unitArray.add(tile);
                //System.out.println("CPU: Getting " + tile.getUnite().toString());
            }
        }
        //System.out.println("CPU: Final Unit Array : " + unitArray.toString());
        return unitArray;
    }

    public ArrayList<tileData> getAllEnemy() {
        ArrayList<tileData> unitArray = new ArrayList<>();
        for (tileData[] tileData : map.getMap()) {
            for (tileData tile : tileData) {
                //System.out.print("CPU: Scanning - "+tile.x+":"+tile.y);
                if(tile.getUnite()==null || tile.getUnite().getController()==this) {
                    //System.out.println(" = NULL");
                    continue;
                }
                System.out.println(" = "+tile.getUnite().toString());
                unitArray.add(tile);
            }
        }
        printAllUnitArray(allEnemy);
        return unitArray;
    }

    public tileData getNearestEnnemy(tileData ally) {
        tileData nearest = null;
        for (tileData tempenemy : allEnemy) {
            //System.out.print("CPU: Scanning - "+tempenemy.x+":"+tempenemy.y);
            if(nearest == null) nearest = tempenemy;
            Vector2 allyVector = new Vector2(ally.x, ally.y);
            Vector2 nearestVector = new Vector2(nearest.x, nearest.y);
            Vector2 enemyVector = new Vector2(tempenemy.x, tempenemy.y);
            if(map.distance(allyVector, enemyVector)<map.distance(allyVector, nearestVector)) nearest = tempenemy;
            if(tempenemy.getUnite()!=null) System.out.println(" = "+tempenemy.getUnite().toString());
            //else System.out.println(" = NULL");
        }
        //System.out.println("CPU: Nearest ennemy at "+nearest.x+":"+nearest.y+" = "+nearest.getUnite().toString());
        //if(nearest.getUnite() != null) System.out.println("CPU: Nearest ennemy :" + nearest.getUnite().toString());
        return nearest;
    }

    public tileData getBestTile(tileData ally, tileData enemy) {
        tileData bestTile = null;
        HashMap<tileData, double[]> possibleTiles = new HashMap<>();
        Vector2 enemyVector = new Vector2(enemy.x, enemy.y);
        Vector2 allyVector = new Vector2(ally.x, ally.y);
        for (int i = ally.x-1; i <= ally.x+1; i++) {
            for (int j = ally.y-1; j <= ally.y+1; j++) {
                //System.out.println("CPU: Scanning - "+i+":"+j);
                if(i<0 || j<0 || (i==ally.x && j==ally.y) || i>19 || j>14) continue;
                if(map.getTile(i, j).getUnite() != null && map.getTile(i, j).getUnite().getController()==this) continue;
                Vector2 tempVector = new Vector2(i, j);
                double G = map.distance(allyVector, tempVector)*3; // Distance to start node (We keep it optimistic to simplify for now)
                double H = map.distance(tempVector, enemyVector)*3; // Distance to end node (Optimistic)
                double F = G+H; // F = G + H
                double[] distances = {F, H, G};
                possibleTiles.put(map.getTile(i, j), distances);
            }
        }
        double[] bestTileValues = {999,999,999};
        for (HashMap.Entry<tileData, double[]> test : possibleTiles.entrySet()) {
            /*if(bestTile == null) {
                System.out.println("CPU: best distance : " + test.getValue()[0]);
                bestTile = test.getKey();
                bestTileValues = test.getValue();
            }*/
            double[] testValues = test.getValue();
            if(test.getKey().getTypeString()!="Grass") continue;
            if(testValues[0]<bestTileValues[0] ) {  //If new F < old F -> New
                bestTile = test.getKey();
                bestTileValues = test.getValue();
            }else if(testValues[0]==bestTileValues[0] && testValues[1]<bestTileValues[1]){  //If New F == Old F and New H < Old H
                bestTile = test.getKey();
                bestTileValues = test.getValue();
            }
            if(test.getKey().getUnite()!=null && test.getKey().getUnite().getController()!=this) {
                //System.out.println("CPU: directly near the ennemy" + test.getValue()[0]);
                bestTile = test.getKey();
                bestTileValues = test.getValue();
            }
        }
        //System.out.println("CPU: best move : " + bestTile.x+":"+bestTile.y);
        return bestTile;
    }

    public void printAllUnitArray(ArrayList<tileData> unitArray) {
        int temp = 0;
        //System.out.println("=========================");
        for (tileData unit : unitArray) {
            if(unit.getUnite()!=null) {
                //System.out.println("CPU: Unit"+ temp++ +"=" + unit.getUnite().toString()+" | "+unit.x+":"+unit.y);
            }else{
                //System.out.println("CPU: Unit"+ temp++ +"= NULL");
            }
        }
        //System.out.println("=========================");
    }
}