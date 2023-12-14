package com.orkin.game;

import java.util.List;
import java.util.LinkedList;

public class Turn_based_system {
    private List<Player> players;
    private String[] phases = {"Pre-Game","Command", "Movement", "Shooting", "Charge", "Fight"};
    private int currentPhase;
    private int currentPlayerIndex;
    private int otherPlayerIndex;
    private int turnCounter;

    public Turn_based_system() {
        players = new LinkedList<>();
        currentPlayerIndex = 0;
        currentPlayerIndex = 1;
        turnCounter = 50; // Initialize with the total number of turns
        currentPhase = 1;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void nextTurn(){
        System.out.println("Next turn !");
        this.turnCounter--;
        if (turnCounter == 0) {
            System.out.println("Game over! No more turns left.");
        }
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if(currentPlayerIndex==0) otherPlayerIndex=1;
        else otherPlayerIndex=0;
    }

    public void nextPhase(){
        System.out.println("Next phase !");
        if (currentPhase == 0||currentPhase==5){
            this.currentPhase = 1;
            nextTurn();
        }
        if(currentPhase<5) this.currentPhase++;
        if (currentPhase == 1) this.currentPhase = 2; /* TODO : implement the command phase */
    }

    public void startGame() {
        Player currentPlayer = players.get(currentPlayerIndex);
        this.currentPlayerIndex = 0;
        currentPlayer.takeTurn();
    }

    public Player getTurnPlayer() {
        return this.players.get(currentPlayerIndex);
    }
    public Player getOtherPlayer() {
        return this.players.get(otherPlayerIndex);
    }
    public int getTurnCounter() {
        return this.turnCounter;
    }
    public String getCurrentPhase() {
        return this.phases[currentPhase-1];
    }

    /*public static void main(String[] args) {
        Turn_based_system system = new Turn_based_system();
        system.addPlayer(new Player("Player 1"));
        system.addPlayer(new Player("Player 2"));
        system.startGame();
    }*/
}