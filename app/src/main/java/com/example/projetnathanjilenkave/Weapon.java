package com.example.projetnathanjilenkave;

public class Weapon extends Equipment{

    int bonusStrength;

    public Weapon(String name, String rarity, int bonusStrength) {
        super(name, rarity);
        this.bonusStrength = bonusStrength;
    }

    public int getBonusStrength() {
        return bonusStrength;
    }

    public void setBonusStrength(int bonusStrength) {
        this.bonusStrength = bonusStrength;
    }
}
