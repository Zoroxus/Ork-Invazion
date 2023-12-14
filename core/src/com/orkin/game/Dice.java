package com.orkin.game;

import java.util.Random;

public class Dice {
    private Random random;

    public Dice(){
        this.random = new Random();
    }

    // Lance 'numDice' dés à 3 faces et retourne la somme des résultats
    public int rollD3(int numDice){
        int sum = 0;
        for (int i = 0; i < numDice; i++) {
            sum += random.nextInt(3) + 1;
        }
        return sum;
    }

    // Lance 'numDice' dés à 6 faces et retourne la somme des résultats
    public int rollD6(int numDice){
        int sum = 0;
        for (int i = 0; i < numDice; i++) {
            sum += random.nextInt(6) + 1;
        }
        return sum;
    }
}