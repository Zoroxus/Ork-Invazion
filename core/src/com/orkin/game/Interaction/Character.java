package com.orkin.game.Interaction;

public class Character {
    // ------------ Attributes ------------
    private int health;
    private int armor;

    // ------------ Constructors ------------
    public Character(int health, int armor) {
        this.health = health;
        this.armor = armor;
    }

    // ------------ Methods ------------
    /**
     * Method to manage the interaction between two characters.
     * @param target The character to interact with.
     * @param interactionType The type of interaction (e.g., "combat", "healing").
     */
    public void interact(Character target, String interactionType) {
        switch (interactionType) {
            case "combat":
                executeCombat(target);
                break;
            case "healing":
                executeHealing(target);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized interaction type: " + interactionType);
        }
    }

    private void executeCombat(Character target) {
        // Specific combat logic
        // For example, calculate damage and apply it to the target
    }

    private void executeHealing(Character target) {
        // Specific healing logic
        // For example, restore the health of the target
    }

    // ------------ Getters and Setters ------------
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}