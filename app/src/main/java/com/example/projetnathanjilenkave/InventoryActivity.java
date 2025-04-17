package com.example.projetnathanjilenkave;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //Affichage des stats
        displayplayerStats();

        SharedPreferences sharedPreferencesWeapon = getSharedPreferences("playerWeapon", MODE_PRIVATE);
        String nomArmeJoueur = sharedPreferencesWeapon.getString("nomArmeJoueur", "DefaultWeapon");
        String raretéArmeJoueur = sharedPreferencesWeapon.getString("raretéArmeJoueur", "DefaultRarityWeapon");
        int bonusStrengthArmeJoueur = sharedPreferencesWeapon.getInt("bonusStrengthArmeJoueur", 0);

        displayCharaWeapon(nomArmeJoueur, raretéArmeJoueur , bonusStrengthArmeJoueur);

        SharedPreferences sharedPreferencesArmor = getSharedPreferences("playerArmor", MODE_PRIVATE);
        String nomArmureJoueur = sharedPreferencesArmor.getString("nomArmureJoueur", "DefaultArmor");
        String raretéArmureJoueur = sharedPreferencesArmor.getString("raretéArmureJoueur", "DefaultRarityArmor");
        int bonusDefenseArmureJoueur = sharedPreferencesArmor.getInt("bonusDefenseArmureJoueur", 0);

        displayCharaArmor(nomArmureJoueur, raretéArmureJoueur , bonusDefenseArmureJoueur);


        buttonNull();
        //Fermer l'inventaire
        Button closeBtn = findViewById(R.id.ButtonClose);
        closeBtn.setOnClickListener(view -> {
            finish();
        });

    }

    private void buttonNull() {
        TextView nameObject1 = findViewById(R.id.NameObject1);
        TextView nameObject2 = findViewById(R.id.NameObject2);
        TextView nameObject3 = findViewById(R.id.nameObject3);

        Button objectBtn1 = findViewById(R.id.buttonObject1);
        Button objectBtn2 = findViewById(R.id.ButtonObject2);
        Button objectBtn3 = findViewById(R.id.buttonObject3);

        for (int objectNumber = 1; objectNumber <= 3; objectNumber++) {
            switch (objectNumber) {
                case 1:
                    if (isEmpty(nameObject1)) {
                        objectBtn1.setVisibility(View.GONE);
                    }
                    break;
                case 2:
                    if (isEmpty(nameObject2)) {
                        objectBtn2.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    if (isEmpty(nameObject3)) {
                        objectBtn3.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    private boolean isEmpty(TextView textView) {
        return textView.getText() == null || textView.getText().toString().trim().isEmpty();
    }

    private void displayplayerStats(){
        PlayerData player = new PlayerData(this);

        // Stats
        ((TextView) findViewById(R.id.health)).setText("Vie : " + player.getHealth());
        ((TextView) findViewById(R.id.experience)).setText("EXP : " + player.getExp());
        ((TextView) findViewById(R.id.gold)).setText("Or : " + player.getGold());
        ((TextView) findViewById(R.id.category)).setText("Classe : " + player.getClassPlayer());
        ((TextView) findViewById(R.id.strength)).setText("Force : " + player.getStrength());
        ((TextView) findViewById(R.id.defense)).setText("Défense : " + player.getDefense());
        ((TextView) findViewById(R.id.agility)).setText("Agilité : " + player.getAgility());

        // Arme
        ((TextView) findViewById(R.id.weaponName)).setText("Arme : " + player.getWeaponName());
        ((TextView) findViewById(R.id.weaponDamage)).setText("Dégâts : " + player.getWeaponDamage());
        ((TextView) findViewById(R.id.weaponRarity)).setText("Rareté : " + player.getWeaponRarity());

        // Armure
        ((TextView) findViewById(R.id.armorName)).setText("Armure : " + player.getArmorName());
        ((TextView) findViewById(R.id.armorDefense)).setText("Défense : " + player.getArmorDefense());
        ((TextView) findViewById(R.id.armorRarity)).setText("Rareté : " + player.getArmorRarity());
    }

    private void displayCharaWeapon(String nomArmeJoueur, String raretéArmeJoueur , int bonusStrengthArmeJoueur){
        TextView weaponNamePlayer = findViewById(R.id.WeaponName);
        TextView rarityWeaponPlayer = findViewById(R.id.WeaponRarity);
        TextView bonusStrengthWeapon = findViewById(R.id.WeaponBonusStat);

        weaponNamePlayer.setText(nomArmeJoueur);
        rarityWeaponPlayer.setText(raretéArmeJoueur);
        bonusStrengthWeapon.setText(String.valueOf(bonusStrengthArmeJoueur));
    }

    private void displayCharaArmor(String nomArmureJoueur, String raretéArmureJoueur , int bonusDefenseArmureJoueur){
        TextView armorNamePlayer = findViewById(R.id.ArmorName);
        TextView rarityArmorPlayer = findViewById(R.id.ArmorRarity);
        TextView bonusDefenseArmor = findViewById(R.id.ArmorBonusStat);

        armorNamePlayer.setText(nomArmureJoueur);
        rarityArmorPlayer.setText(raretéArmureJoueur);
        bonusDefenseArmor.setText(String.valueOf(bonusDefenseArmureJoueur));
    }
}