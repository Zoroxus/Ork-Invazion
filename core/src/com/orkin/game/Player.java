package com.orkin.game;

class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public void takeTurn() {
        System.out.println(name + ": Select an action to do");
    }

    public String getName() {
        return this.name;
    }
}