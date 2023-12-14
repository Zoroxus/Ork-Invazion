package com.orkin.game.Arme;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.orkin.game.Dice;
import com.orkin.game.Map.tileData;


public class Arme {
    protected int portee;       // Range
    protected String attack;    // A
    protected int ct;           // CT
    protected int strength;     // S
    protected int pa;           // AP
    protected String dmg;       // D
    protected boolean ismelee;  // Melee or Range ?
    protected Sprite sprite;

    /**
     * @param portee
     * @param attack
     * @param ct
     * @param strength
     * @param pa
     * @param dmg
     * @param ismelee
     */
    protected Arme(int portee,String attack,int ct,int strength,int pa,String dmg,boolean ismelee){
        this.portee = portee;
        this.attack = attack;
        this.ct = ct;
        this.strength = strength;
        this.pa = pa;
        this.dmg = dmg;
        this.ismelee = ismelee;
        this.sprite = new Sprite(new Texture("bolter.png"));
    }

    public boolean getismelee(){
        return this.ismelee;
    }
    public Sprite getSprite() {
        return this.sprite;
    }


    // The different tests before attack
    protected boolean touche(){
        Dice des6 = new Dice();
        int resultatdes = des6.rollD6(1);
        System.out.print(" [hit roll: CT "+this.ct+"+ | "+resultatdes);
        if(resultatdes>=this.ct){
            System.out.print(" = hit] ");
            return true;
        }else{
            System.out.print(" = miss] ");
            return false;
        }
    }

    /**
     * @param savecible
     * @return
     */
    protected boolean save(int savecible){
        Dice des6 = new Dice();
        int resultatdes = des6.rollD6(1);
        resultatdes = resultatdes - this.pa;
        System.out.print(" [save : Sv="+savecible+"+ | pa=-"+this.pa+" | "+resultatdes+" | "+(resultatdes-this.pa));
        if(resultatdes-this.pa>=savecible){
            System.out.print(" = saved] ");
            return true;
        }else{
            System.out.print(" = not saved] ");
            return false;
        }
    }

    /**
     * @param endurancecible
     * @return
     */
    protected boolean blessure(int endurancecible){
        Dice des6 = new Dice();
        int resultatdes = des6.rollD6(1);
        // Si la force est le double(ou plus) de l'endurance
        System.out.print(" [wound : "+this.strength+" | "+endurancecible+" | ");
        if(this.strength/endurancecible >=2){
            System.out.print("2+ | "+resultatdes);
            if(resultatdes>=2){
                System.out.print(" = wounded] ");
                return true;
            }else{
                System.out.print(" = not wounded] ");
                return false;
            }
        }
        // Si la force est supérieure à l'endurance
        else if(this.strength>endurancecible){
            System.out.print("3+ | "+resultatdes);
            if(resultatdes>=3){
                System.out.print(" = wounded] ");
                return true;
            }else{
                System.out.print(" = not wounded] ");
                return false;
            }
        }
        // Si la force est égal à l'endurance
        else if(this.strength==endurancecible){
            System.out.print("4+ | "+resultatdes);
            if(resultatdes>=4){
                System.out.print(" = wounded] ");
                return true;
            }else{
                System.out.print(" = not wounded] ");
                return false;
            }
        }
        // Si la force est la moitié(ou moins) de l'endurance
        else if(endurancecible/this.strength>2){
            System.out.print("6+ | "+resultatdes);
            if(resultatdes>=6){
                System.out.print(" = wounded] ");
                return true;
            }else{
                System.out.print(" = not wounded] ");
                return false;
            }
        }
        // Si la force est inférieur à l'endurance
        else{
            System.out.print("5+ | "+resultatdes);
            if(resultatdes>=5){
                System.out.print(" = wounded] ");
                return true;
            }else{
                System.out.print(" = not wounded] ");
                return false;
            }
        }
    }

    // Attack  
    /**
     * @param attaquant
     * @param defenseur
     */
    public boolean attack(tileData attaquant,tileData defenseur) {
        Vector2 start = new Vector2(attaquant.getX(),attaquant.getY());
        Vector2 end = new Vector2(defenseur.getX(), defenseur.getY());

        int NumberAttack = Integer.parseInt(this.attack);
        System.out.println("Attacks per model : " + this.attack);
        System.out.println("Attacks per model : " + NumberAttack);
        int damage = Integer.parseInt(this.dmg);

        if(distance(start,end)*3>this.portee && this.getismelee()==false){
            System.out.println("Out of range");
            return false;
        } else if(defenseur.getUnite()==null) {
            System.out.println("Nobody here !");
            return false;
        }else{
            System.out.println("Attacking with " + this.toString());
            System.out.println(attaquant.getUnite().getNumber()*NumberAttack + " attacks.");
            for(int nombredetour = 0 ;nombredetour<attaquant.getUnite().getNumber()*NumberAttack ;nombredetour++){
                System.out.print("Attack "+(nombredetour+1) + ": ");
                if(defenseur.getUnite()==null) {
                    System.out.println("Nobody left !");
                    break;
                }
                if(this.touche() && this.blessure(defenseur.getUnite().getT()) && !this.save(defenseur.getUnite().getSv())) {
                    System.out.print("Wounded ! ");
                    //If pv of Unite is below or equals to zero
                    if(defenseur.getUnite().getW()-damage<=0) {
                        if(defenseur.getUnite().getNumber()>2) {
                            int new_number = defenseur.getUnite().getNumber() -1;
                            defenseur.getUnite().setNumber(new_number);
                            System.out.println("One less ! ");
                        } else {
                            attaquant.getUnite().setXP(defenseur.getUnite().getXP()+attaquant.getUnite().getXP());
                            defenseur.setArme(defenseur.getUnite().getRangeWeapon());
                            System.out.println("Weapon on ground : "+defenseur.getArme());
                            defenseur.setUnite(null);
                            System.out.println("Unit killed ! ");
                            return true;
                        }
                    //Otherwise we come and change the unit's HP and doing some reaction
                    } else {
                        defenseur.getUnite().setW(defenseur.getUnite().getW()-damage);
                        System.out.println("- "+damage+" W.");
                        if(defenseur.getUnite().getClass().getSimpleName().equals("Boyz")||defenseur.getUnite().getClass().getSimpleName().equals("Nobz")||defenseur.getUnite().getClass().getSimpleName().equals("Warboss")){
                            System.out.print(defenseur.getUnite().toString()+": WAAAAAAGH!!!!");
                            
                        } else {
                            System.out.print("Miss");
                        }
                    }
                } else {
                    System.out.print("Miss");
                }
                //System.out.println();
            }
        }
        //System.out.println("No hits !!!");
        return false;
    }


    protected double distance(Vector2 object1, Vector2 object2){
        return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
    }

    public int getRange() {
        return this.portee;
    }
    public String getA() {
        return this.attack;
    }
    public int getCT() {
        return this.ct;
    }
    public int getS() {
        return this.strength;
    }
    public int getAP() {
        return this.pa;
    }

    @Override
    public String toString(){
        return "Unnamed Weapon";
    }
}