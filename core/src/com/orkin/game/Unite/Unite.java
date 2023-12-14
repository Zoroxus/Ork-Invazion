package com.orkin.game.Unite;
import com.orkin.game.Arme.Arme;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.orkin.game.Map.*;

public abstract class Unite<Player> {

    //Attributs (Tu déclares des variables que tu vas utiliser)
    protected int movement;
    protected int endurance;
    protected int save;
    protected int pv;
    protected int fullpv;
    protected int commandment;
    protected int capture_capacity;
    protected int number;
    protected Arme melee;
    protected Arme distance;
    protected int exp;
    protected Player owner;
    protected Player controller;
    protected Sprite sprite;
    protected Sprite knuckles = new Sprite(new Texture("knuckles_left.png"));
    protected boolean moved;
    protected boolean shoot;
    protected boolean charged;
    protected boolean fought;
    protected boolean commanded;

    // Constructeurs (sert à appeler la classe "Unite")
    // (=protected) Car on va juste appeler les filles
    /**
     * Constructor for a Unit
     * @param movement
     * @param endurance
     * @param save
     * @param pv
     * @param commandment
     * @param capture_capacity
     * @param number
     * @param exp
     * @param melee Melee Weapon
     * @param distance Ranged Weapon
     * @param sprite Sprite/Texture of the unit
     */
    protected Unite(int movement, int endurance, int save, int pv, int commandment, int capture_capacity, int number,int exp,Arme melee,Arme distance, Sprite sprite){
        this.movement = movement;
        this.endurance = endurance;
        this.save = save;
        this.pv = pv;
        this.fullpv = pv;
        this.commandment = commandment;
        this.capture_capacity = capture_capacity;
        this.number = number;
        this.exp = exp;
        this.melee = melee;
        this.distance = distance;
        this.sprite = sprite;
    }

    // Getters
    public int getM(){
        return this.movement;
    }
    public int getT(){
        return this.endurance;
    }
    public int getSv(){
        return this.save;
    }
    public int getW(){
        return this.pv;
    }
    public int getFullW(){
        return this.fullpv;
    }
    public int getCm(){
        return this.commandment;
    }
    public int getOC(){
        return this.capture_capacity;
    }
    public int getNumber(){
        return this.number;
    }
    public Arme getMeleeWeapon(){
        return this.melee;
    }
    public Arme getRangeWeapon(){
        return this.distance;
    }
    public int getXP(){
        return this.exp;
    }
    public Player getOwner(){
        return this.owner;
    }
    public Player getController(){
        return this.controller;
    }
    public Sprite getSprite() {
        return this.sprite;
    }
    public Sprite getKnuckles() {
        return this.knuckles;
    }
    public boolean hasCommanded() {
        return this.commanded;
    }
    public boolean hasMoved() {
        return this.moved;
    }
    public boolean hasShoot() {
        return this.shoot;
    }
    public boolean hasCharged() {
        return this.charged;
    }
    public boolean hasFought() {
        return this.fought;
    }

    // Setters
    public void setOwner(Player owner){
        this.owner = owner;
    }
    public void setController(Player controller){
        this.controller = controller;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setW(int pv) {
        this.pv = pv;
    }
    public void setCommanded(boolean state) {
        this.commanded = state;
    }
    public void setMoved(boolean state) {
        this.moved = state;
    }
    public void setShoot(boolean state) {
        this.shoot = state;
    }
    public void setCharged(boolean state) {
        this.charged = state;
    }
    public void setFought(boolean state) {
        this.fought = state;
    }
    public void setEveryState(boolean state) {
        this.commanded = state;
        this.moved = state;
        this.shoot = state;
        this.charged = state;
        this.fought = state;
    }
    
    public void equipArme(tileData positionPlayer){
        //There's a weapon at ground
        if(positionPlayer.getArme() != null && positionPlayer.getArme().getismelee()){
            Arme temp = this.melee;
            this.melee = positionPlayer.getArme();
            positionPlayer.setArme(temp);
            System.out.println("Melee weapon equipped : " + this.melee.getClass());
        }
        else if (positionPlayer.getArme() != null && !positionPlayer.getArme().getismelee()) {
            Arme temp = this.distance;
            this.distance = positionPlayer.getArme();
            positionPlayer.setArme(temp);
            System.out.println("Equipped ranged weapon: " + this.distance.getClass());
        }
        else {
            System.out.println("No weapons on the ground or incompatible weapons.");
        }
    }

    public void setArme(Arme weapon){
        //There's a weapon at ground
        if(weapon.getismelee()){
            this.melee = weapon;
            System.out.println("Melee weapon equipped : " + this.melee.getClass());
        }
        else {
            this.distance = weapon;
            System.out.println("Equipped ranged weapon: " + this.distance.getClass());
        }
    }

    public void heal(Unite unite_allie) {
        int healingPower = 1; // La puissance de guérison pour toutes les unités

        // Boyz
        if (unite_allie instanceof Boyz) {
            // Puisque les Boyz ont seulement un pv, nous vérifions si l'unité peut ajouter un Boyz
            if (unite_allie.getNumber() < 10) { // Supposons que 10 est le nombre maximum de Boyz dans une unité
                unite_allie.setNumber(unite_allie.getNumber() + 1); // Ajouter un Boyz à l'unité
                System.out.println("Un Boyz a été ajouté à l'unité! Nombre actuel de Boyz dans l'unité: " + unite_allie.getNumber());
            } else {
                System.out.println("L'unité de Boyz est déjà au complet.");
            }
        }
        // Intercessor
        else if (unite_allie instanceof Intercessor) {
            if (unite_allie.getW() < 2) {
                unite_allie.setW(unite_allie.getW() + healingPower);
                System.out.println("Un Intercessor a été guéri! Points de vie actuels: " + unite_allie.getW());
            }
            else if (unite_allie.pv == 2 && unite_allie.number < 5){
                unite_allie.number += 1;
            }
            else {
                System.out.println("L'Intercessor a déjà tous ses points de vie.");
            }
        }
        // Nobz
        else if (unite_allie instanceof Nobz) {
            // Guérison des Nobz
            if (unite_allie.pv < 2) {
                unite_allie.pv += healingPower;
                System.out.println("Un Nobz a été guéri! Points de vie actuels: " + unite_allie.pv);
            } else if (unite_allie.pv == 2 && unite_allie.number < 5) {
                unite_allie.number += 1;
                System.out.println("Un nouveau Nobz a rejoint l'unité! Nombre actuel de Nobz: " + unite_allie.number);
            }
            else {
                System.out.println("Le Nobz a déjà tous ses points de vie.");
            }
        }
        // WarBoss
        else if (unite_allie instanceof Warboss) {
            // Guérison du WarBoss
            if (unite_allie.pv < 1) {
                unite_allie.pv += 1;
                System.out.println("Le WarBoss a été guéri! Points de vie actuels: " + unite_allie.pv);
            } else {
                System.out.println("Le WarBoss a déjà tous ses points de vie.");
            }
        }
        // Autre type d'unité
        else {
            System.out.println("Type d'unité non reconnu pour la guérison.");
        }
    }

    public void setXP(int exp){
        this.exp = exp;
    }

    @Override
    public String toString(){
        return "Unnamed Unit";
    }

}

