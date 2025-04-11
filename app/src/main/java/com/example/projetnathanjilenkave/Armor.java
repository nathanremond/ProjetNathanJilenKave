package com.example.projetnathanjilenkave;

public class Armor extends Equipment{

    int bonusDefense;
    int price;


    public Armor(String name, String rarity, int bonusDefense, int price) {
        super(name, rarity);
        this.bonusDefense = bonusDefense;
        this.price = price;
    }

    public int getBonusDefense() {
        return bonusDefense;
    }

    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
