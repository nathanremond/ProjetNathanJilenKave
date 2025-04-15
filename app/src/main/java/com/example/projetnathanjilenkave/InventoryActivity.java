package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        SharedPreferences sharedPreferences = getSharedPreferences("PlayerStats", MODE_PRIVATE);
        int healthJoueur = sharedPreferences.getInt("healthJoueur", 100); // 100 est la valeur par défaut
        int expJoueur = sharedPreferences.getInt("expJoueur", 0);
        int goldJoueur = sharedPreferences.getInt("goldJoueur", 0);
        String classJoueur = sharedPreferences.getString("classJoueur", "DefaultClass");
        int strengthJoueur = sharedPreferences.getInt("strengthJoueur", 0);
        int defenseJoueur = sharedPreferences.getInt("defenseJoueur", 0);
        int agilityJoueur = sharedPreferences.getInt("agilityJoueur", 0);

        displayCharaStats(healthJoueur, expJoueur, goldJoueur, classJoueur, strengthJoueur, defenseJoueur, agilityJoueur);

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

    private void displayCharaStats(int healthJoueur, int expJoueur, int goldJoueur, String classJoueur, int strengthJoueur, int defenseJoueur, int agilityJoueur){
        TextView healthStat = findViewById(R.id.StatHealthValue);
        TextView expStat = findViewById(R.id.StatExpValue);
        TextView goldStat = findViewById(R.id.GoldValue);
        TextView classStat = findViewById(R.id.StatClassValue);
        TextView strengthStat = findViewById(R.id.StatStrengthValue);
        TextView defenseStat = findViewById(R.id.StatDefenseValue);
        TextView agilityStat = findViewById(R.id.StatAgilityValue);

        healthStat.setText(String.valueOf(healthJoueur));
        expStat.setText(String.valueOf(expJoueur));
        goldStat.setText(String.valueOf(goldJoueur));
        classStat.setText(classJoueur);
        strengthStat.setText(String.valueOf(strengthJoueur));
        defenseStat.setText(String.valueOf(defenseJoueur));
        agilityStat.setText(String.valueOf(agilityJoueur));
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