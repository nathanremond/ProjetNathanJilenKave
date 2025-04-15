package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CharacterChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choice);

        Button choiceBtn1 = findViewById(R.id.class_button1);
        Button choiceBtn2 = findViewById(R.id.class_button2);
        Button choiceBtn3 = findViewById(R.id.class_button3);


        choiceBtn1.setOnClickListener(view -> {
            Character joueur = new Character(100, 0, 0, "Chevalier");
            Weapon joueurArme = new Weapon("épée en bois", "commun", 5, 0);
            Armor joueurArmure = new Armor("armure en bois", "commun", 5, 0);
            Intent intentToContextActivity = new Intent(this, SceneActivity.class);
            intentToContextActivity.putExtra("healthJoueur", joueur.getHealth());
            intentToContextActivity.putExtra("expJoueur", joueur.getExperience());
            intentToContextActivity.putExtra("goldJoueur", joueur.getGold());
            intentToContextActivity.putExtra("classJoueur", joueur.getCategory());
            intentToContextActivity.putExtra("strengthJoueur", joueur.getStrength());
            intentToContextActivity.putExtra("defenseJoueur", joueur.getDefense());
            intentToContextActivity.putExtra("agilityJoueur", joueur.getAgility());
            intentToContextActivity.putExtra("nomArmeJoueur", joueurArme.getName());
            intentToContextActivity.putExtra("raretéArmeJoueur", joueurArme.getRarity());
            intentToContextActivity.putExtra("bonusStrengthArmeJoueur", joueurArme.getBonusStrength());
            intentToContextActivity.putExtra("priceArmeJoueur", joueurArme.getPrice());
            intentToContextActivity.putExtra("nomArmureJoueur", joueurArmure.getName());
            intentToContextActivity.putExtra("raretéArmureJoueur", joueurArmure.getRarity());
            intentToContextActivity.putExtra("bonusDefenseArmureJoueur", joueurArmure.getBonusDefense());
            intentToContextActivity.putExtra("priceArmureJoueur", joueurArmure.getPrice());
            intentToContextActivity.putExtra("previousPage", "CharaChoice");
            startActivity(intentToContextActivity);
        });

        choiceBtn2.setOnClickListener(view -> {
            Character joueur = new Character(100, 0, 0, "Archer");
            Weapon joueurArme = new Weapon("arc en bois", "commun", 5, 0);
            Armor joueurArmure = new Armor("armure en bois", "commun", 5, 0);
            Intent intentToContextActivity = new Intent(this, SceneActivity.class);
            intentToContextActivity.putExtra("healthJoueur", joueur.getHealth());
            intentToContextActivity.putExtra("expJoueur", joueur.getExperience());
            intentToContextActivity.putExtra("goldJoueur", joueur.getGold());
            intentToContextActivity.putExtra("classJoueur", joueur.getCategory());
            intentToContextActivity.putExtra("strengthJoueur", joueur.getStrength());
            intentToContextActivity.putExtra("defenseJoueur", joueur.getDefense());
            intentToContextActivity.putExtra("agilityJoueur", joueur.getAgility());
            intentToContextActivity.putExtra("nomArmeJoueur", joueurArme.getName());
            intentToContextActivity.putExtra("raretéArmeJoueur", joueurArme.getRarity());
            intentToContextActivity.putExtra("bonusStrengthArmeJoueur", joueurArme.getBonusStrength());
            intentToContextActivity.putExtra("priceArmeJoueur", joueurArme.getPrice());
            intentToContextActivity.putExtra("nomArmureJoueur", joueurArmure.getName());
            intentToContextActivity.putExtra("raretéArmureJoueur", joueurArmure.getRarity());
            intentToContextActivity.putExtra("bonusDefenseArmureJoueur", joueurArmure.getBonusDefense());
            intentToContextActivity.putExtra("priceArmureJoueur", joueurArmure.getPrice());
            intentToContextActivity.putExtra("previousPage", "CharaChoice");
            startActivity(intentToContextActivity);
        });

        choiceBtn3.setOnClickListener(view -> {
            Character joueur = new Character(100, 0, 0, "Paladin");
            Weapon joueurArme = new Weapon("épée en bois", "commun", 5, 0);
            Armor joueurArmure = new Armor("armure en bois", "commun", 5, 0);
            Intent intentToContextActivity = new Intent(this, SceneActivity.class);
            intentToContextActivity.putExtra("healthJoueur", joueur.getHealth());
            intentToContextActivity.putExtra("expJoueur", joueur.getExperience());
            intentToContextActivity.putExtra("goldJoueur", joueur.getGold());
            intentToContextActivity.putExtra("classJoueur", joueur.getCategory());
            intentToContextActivity.putExtra("strengthJoueur", joueur.getStrength());
            intentToContextActivity.putExtra("defenseJoueur", joueur.getDefense());
            intentToContextActivity.putExtra("agilityJoueur", joueur.getAgility());
            intentToContextActivity.putExtra("nomArmeJoueur", joueurArme.getName());
            intentToContextActivity.putExtra("raretéArmeJoueur", joueurArme.getRarity());
            intentToContextActivity.putExtra("bonusStrengthArmeJoueur", joueurArme.getBonusStrength());
            intentToContextActivity.putExtra("priceArmeJoueur", joueurArme.getPrice());
            intentToContextActivity.putExtra("nomArmureJoueur", joueurArmure.getName());
            intentToContextActivity.putExtra("raretéArmureJoueur", joueurArmure.getRarity());
            intentToContextActivity.putExtra("bonusDefenseArmureJoueur", joueurArmure.getBonusDefense());
            intentToContextActivity.putExtra("priceArmureJoueur", joueurArmure.getPrice());
            intentToContextActivity.putExtra("previousPage", "CharaChoice");
            startActivity(intentToContextActivity);
        });
    }
}