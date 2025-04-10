package com.example.projetnathanjilenkave;

public class Armor extends Equipment{

    int bonusDefense;

    public Armor(String name, String rarity, int bonusDefense) {
        super(name, rarity);
        this.bonusDefense = bonusDefense;
    }

    public int getBonusDefense() {
        return bonusDefense;
    }

    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }
}
