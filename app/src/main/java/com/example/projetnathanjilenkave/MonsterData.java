package com.example.projetnathanjilenkave;

import android.content.Context;
import android.content.SharedPreferences;

public class MonsterData {
    private final SharedPreferences statsPrefs;
    private final SharedPreferences.Editor statsEditor;

    public MonsterData(Context context) {
        statsPrefs = context.getSharedPreferences("monsterStats", Context.MODE_PRIVATE);
        statsEditor = statsPrefs.edit();
    }


    //Stats
    public void setName(String value) {
        statsEditor.putString("monsterName", value).apply();
    }

    public String getName() {
        return statsPrefs.getString("monsterName", "defaultMonsterName");
    }

    public void setHealth(int value) {
        statsEditor.putInt("monsterHealth", value).apply();
    }

    public int getHealth() {
        return statsPrefs.getInt("monsterHealth", 100);
    }

    public void setStrength(int value) {
        statsEditor.putInt("monsterStrength", value).apply();
    }

    public int getStrength() {
        return statsPrefs.getInt("monsterStrength", 0);
    }

    public void setDefense(int value) {
        statsEditor.putInt("monsterDefense", value).apply();
    }

    public int getDefense() {
        return statsPrefs.getInt("monsterDefense", 0);
    }

    public void setGold(int value) {
        statsEditor.putInt("monsterGold", value).apply();
    }

    public int getGold() {
        return statsPrefs.getInt("monsterGold", 0);
    }

    public void setMonster(String name, int health, int strength, int defense, int gold) {
        statsEditor.putString("monsterName", name);
        statsEditor.putInt("monsterHealth", health);
        statsEditor.putInt("monsterStrength", strength);
        statsEditor.putInt("monsterDefense", defense);
        statsEditor.putInt("monsterGold", gold);
        statsEditor.apply();
    }
}
