package com.example.projetnathanjilenkave;

public class Equipment {

    String name;
    String rarity;

    public Equipment(String name, String rarity){
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}
