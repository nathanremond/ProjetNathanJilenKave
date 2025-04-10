package com.example.projetnathanjilenkave;

import java.util.Random;

public class Character {

    int health = 100;
    int experience = 0;
    int gold = 0;
    int defense;
    int strength;
    int agility;
    String category;

    public Character(int health, int experience, int gold, String category){
        this.health = health;
        this.experience = experience;
        this.gold = gold;
        this.defense = initDefense();
        this.strength = initStrength();
        this.agility = initAgility();
        this.category = category;
    }

    private int initDefense(){
        Random rand = new Random();
        return rand.nextInt(10) + 1;
    }

    private int initStrength(){
        Random rand = new Random();
        return rand.nextInt(10) + 1;
    }


    private int initAgility(){
        Random rand = new Random();
        return rand.nextInt(10) + 1;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
