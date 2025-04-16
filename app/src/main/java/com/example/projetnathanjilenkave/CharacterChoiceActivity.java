package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class CharacterChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_choice);

        Button choiceBtn1 = findViewById(R.id.class_button1);
        Button choiceBtn2 = findViewById(R.id.class_button2);
        Button choiceBtn3 = findViewById(R.id.class_button3);

        choiceBtn1.setOnClickListener(view -> {

            initPlayer("chevalier");

            Intent intentToContextActivity = new Intent(this, SceneActivity.class);
            intentToContextActivity.putExtra("previousPage", "CharaChoice");
            startActivity(intentToContextActivity);
        });

        choiceBtn2.setOnClickListener(view -> {

            initPlayer("archer");

            Intent intentToContextActivity = new Intent(this, SceneActivity.class);
            intentToContextActivity.putExtra("previousPage", "CharaChoice");
            startActivity(intentToContextActivity);
        });

        choiceBtn3.setOnClickListener(view -> {

            initPlayer("paladin");

            Intent intentToContextActivity = new Intent(this, SceneActivity.class);
            intentToContextActivity.putExtra("previousPage", "CharaChoice");
            startActivity(intentToContextActivity);
        });
    }

    private void initPlayer(String classe){
        PlayerData playerData = new PlayerData(this);

        Random random = new Random();
        int strength = random.nextInt(10) + 1;
        int defense = random.nextInt(10) + 1;
        int agility = random.nextInt(10) + 1;

        playerData.setHealth(100);
        playerData.setExp(0);
        playerData.setGold(0);
        playerData.setClassPlayer(classe);
        playerData.setStrength(strength);
        playerData.setDefense(defense);
        playerData.setAgility(agility);

        if (classe.equals("archer")) {
            playerData.setWeapon("arc en bois", "commun", 5, 0);
            playerData.setArmor("armure en bois", "commun", 5, 0);
        } else {
            playerData.setWeapon("épée en bois", "commun", 5, 0);
            playerData.setArmor("armure en bois", "commun", 5, 0);
        }
    }
}