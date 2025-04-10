package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            Intent intentToContextActivity = new Intent(this, ContextActivity.class);
            intentToContextActivity.putExtra("healthJoueur", joueur.getHealth());
            intentToContextActivity.putExtra("expJoueur", joueur.getExperience());
            intentToContextActivity.putExtra("goldJoueur", joueur.getGold());
            intentToContextActivity.putExtra("classJoueur", joueur.getCategory());
            intentToContextActivity.putExtra("strengthJoueur", joueur.getStrength());
            intentToContextActivity.putExtra("defenseJoueur", joueur.getDefense());
            intentToContextActivity.putExtra("agilityJoueur", joueur.getAgility());
            startActivity(intentToContextActivity);
        });

        choiceBtn2.setOnClickListener(view -> {
            Character joueur = new Character(100, 0, 0, "Archer");
            Intent intentToContextActivity = new Intent(this, ContextActivity.class);
            intentToContextActivity.putExtra("healthJoueur", joueur.getHealth());
            intentToContextActivity.putExtra("expJoueur", joueur.getExperience());
            intentToContextActivity.putExtra("goldJoueur", joueur.getGold());
            intentToContextActivity.putExtra("classJoueur", joueur.getCategory());
            intentToContextActivity.putExtra("strengthJoueur", joueur.getStrength());
            intentToContextActivity.putExtra("defenseJoueur", joueur.getDefense());
            intentToContextActivity.putExtra("agilityJoueur", joueur.getAgility());
            startActivity(intentToContextActivity);
        });

        choiceBtn3.setOnClickListener(view -> {
            Character joueur = new Character(100, 0, 0, "Paladin");
            Intent intentToContextActivity = new Intent(this, ContextActivity.class);
            intentToContextActivity.putExtra("healthJoueur", joueur.getHealth());
            intentToContextActivity.putExtra("expJoueur", joueur.getExperience());
            intentToContextActivity.putExtra("goldJoueur", joueur.getGold());
            intentToContextActivity.putExtra("classJoueur", joueur.getCategory());
            intentToContextActivity.putExtra("strengthJoueur", joueur.getStrength());
            intentToContextActivity.putExtra("defenseJoueur", joueur.getDefense());
            intentToContextActivity.putExtra("agilityJoueur", joueur.getAgility());
            startActivity(intentToContextActivity);
        });
    }
}