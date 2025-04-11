package com.example.projetnathanjilenkave;

public class Weapon extends Equipment{

    int bonusStrength;
    int price;


    public Weapon(String name, String rarity, int bonusStrength, int price) {
        super(name, rarity);
        this.bonusStrength = bonusStrength;
        this.price = price;
    }

    public int getBonusStrength() {
        return bonusStrength;
    }

    public void setBonusStrength(int bonusStrength) {
        this.bonusStrength = bonusStrength;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
