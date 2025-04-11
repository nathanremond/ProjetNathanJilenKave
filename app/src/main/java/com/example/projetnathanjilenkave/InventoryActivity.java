package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

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

        Character j1 = new Character(100, 0, 0, "Chevalier");
        Weapon a1 = new Weapon("épée", "commune", 4, 30);
        Armor ar1 = new Armor("armure", "rare", 10, 50);
        Object o1 = new Object("torche", "Un outil permettant d'éclairer", 20);
        Button closeBtn = findViewById(R.id.ButtonClose);

        TextView health = findViewById(R.id.StatHealthValue);
        TextView exp = findViewById(R.id.StatExpValue);
        TextView gold = findViewById(R.id.GoldValue);
        TextView category = findViewById(R.id.StatClassValue);
        TextView defense = findViewById(R.id.StatDefenseValue);
        TextView strength = findViewById(R.id.StatStrengthValue);
        TextView agility = findViewById(R.id.StatAgilityValue);

        TextView nameWeapon = findViewById(R.id.WeaponName);
        TextView rarityWeapon = findViewById(R.id.WeaponRarity);
        TextView bonusWeapon = findViewById(R.id.WeaponBonusStat);

        TextView nameObject = findViewById(R.id.NameObject1);
        TextView descriptionObject = findViewById(R.id.DescriptionObject1);

        health.setText(String.valueOf(j1.getHealth()));
        exp.setText(String.valueOf(j1.getExperience()));
        gold.setText(String.valueOf(j1.getGold()));
        category.setText(j1.getCategory());
        defense.setText(String.valueOf(j1.getDefense()));
        strength.setText(String.valueOf(j1.getStrength()));
        agility.setText(String.valueOf(j1.getAgility()));

        nameWeapon.setText(String.valueOf(a1.getName()));
        rarityWeapon.setText(String.valueOf(a1.getRarity()));
        bonusWeapon.setText(String.valueOf(a1.getBonusStrength()));

        nameObject.setText(String.valueOf(o1.getName()));
        descriptionObject.setText(String.valueOf(o1.getDescription()));

        closeBtn.setOnClickListener(view -> {
            Intent intentToContextActivity = new Intent(this, ContextActivity.class);
            startActivity(intentToContextActivity);
        });


    }
}