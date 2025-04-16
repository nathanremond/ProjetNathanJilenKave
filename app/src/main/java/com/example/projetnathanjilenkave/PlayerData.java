package com.example.projetnathanjilenkave;

import android.content.Context;
import android.content.SharedPreferences;

public class PlayerData {
    private final SharedPreferences statsPrefs;
    private final SharedPreferences.Editor statsEditor;

    private final SharedPreferences weaponPrefs;
    private final SharedPreferences.Editor weaponEditor;

    private final SharedPreferences armorPrefs;
    private final SharedPreferences.Editor armorEditor;


    public PlayerData(Context context) {
        statsPrefs = context.getSharedPreferences("playerStats", Context.MODE_PRIVATE);
        statsEditor = statsPrefs.edit();

        weaponPrefs = context.getSharedPreferences("playerWeapon", Context.MODE_PRIVATE);
        weaponEditor = weaponPrefs.edit();

        armorPrefs = context.getSharedPreferences("playerArmor", Context.MODE_PRIVATE);
        armorEditor = armorPrefs.edit();
    }

    //Stats
    public void setHealth(int value) {
        statsEditor.putInt("healthPlayer", value).apply();
    }

    public int getHealth() {
        return statsPrefs.getInt("healthPlayer", 100);
    }

    public void setExp(int value) {
        statsEditor.putInt("expPlayer", value).apply();
    }

    public int getExp() {
        return statsPrefs.getInt("expPlayer", 0);
    }

    public void setGold(int value) {
        statsEditor.putInt("goldPlayer", value).apply();
    }

    public int getGold() {
        return statsPrefs.getInt("goldPlayer", 0);
    }

    public void setClassPlayer(String value) {
        statsEditor.putString("classPlayer", value).apply();
    }

    public String getClassPlayer() {
        return statsPrefs.getString("classPlayer", "DefaultClass");
    }

    public void setStrength(int value) {
        statsEditor.putInt("strengthPlayer", value).apply();
    }

    public int getStrength() {
        return statsPrefs.getInt("strengthPlayer", 0);
    }

    public void setDefense(int value) {
        statsEditor.putInt("defensePlayer", value).apply();
    }

    public int getDefense() {
        return statsPrefs.getInt("defensePlayer", 0);
    }

    public void setAgility(int value) {
        statsEditor.putInt("agilityPlayer", value).apply();
    }

    public int getAgility() {
        return statsPrefs.getInt("agilityPlayer", 0);
    }

    //Weapon
    public void setWeapon(String name, String rarity, int damage, int price) {
        weaponEditor.putString("namePlayerWeapon", name);
        weaponEditor.putString("rarityPlayerWeapon", rarity);
        weaponEditor.putInt("damagesPlayerWeapon", damage);
        weaponEditor.putInt("pricePlayerWeapon", price);
        weaponEditor.apply();
    }

    public String getWeaponName() {
        return weaponPrefs.getString("namePlayerWeapon", "DefaultWeapon");
    }

    public String getWeaponRarity() {
        return weaponPrefs.getString("rarityPlayerWeapon", "Common");
    }

    public int getWeaponDamage() {
        return weaponPrefs.getInt("damagesPlayerWeapon", 0);
    }

    public int getWeaponPrice() {
        return weaponPrefs.getInt("pricePlayerWeapon", 0);
    }

    //Armor
    public void setArmor(String name, String rarity, int defense, int price) {
        armorEditor.putString("namePlayerArmor", name);
        armorEditor.putString("rarityPlayerArmor", rarity);
        armorEditor.putInt("defensePlayerArmor", defense);
        armorEditor.putInt("pricePlayerArmor", price);
        armorEditor.apply();
    }

    public String getArmorName() {
        return armorPrefs.getString("namePlayerArmor", "DefaultArmor");
    }

    public String getArmorRarity() {
        return armorPrefs.getString("rarityPlayerArmor", "Common");
    }

    public int getArmorDefense() {
        return armorPrefs.getInt("defensePlayerArmor", 0);
    }

    public int getArmorPrice() {
        return armorPrefs.getInt("pricePlayerArmor", 0);
    }

    //Réinitialiser les infos du joueur
    public void resetPlayerData() {
        statsEditor.clear().apply();
        weaponEditor.clear().apply();
        armorEditor.clear().apply();
    }
}