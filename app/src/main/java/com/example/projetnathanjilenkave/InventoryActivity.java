package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.content.SharedPreferences;
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

        SharedPreferences sharedPreferences = getSharedPreferences("PlayerStats", MODE_PRIVATE);
        int healthJoueur = sharedPreferences.getInt("healthJoueur", 100); // 100 est la valeur par défaut
        int expJoueur = sharedPreferences.getInt("expJoueur", 0);
        int goldJoueur = sharedPreferences.getInt("goldJoueur", 0);
        String classJoueur = sharedPreferences.getString("classJoueur", "DefaultClass");
        int strengthJoueur = sharedPreferences.getInt("strengthJoueur", 0);
        int defenseJoueur = sharedPreferences.getInt("defenseJoueur", 0);
        int agilityJoueur = sharedPreferences.getInt("agilityJoueur", 0);

        displayCharaStats(healthJoueur, expJoueur, goldJoueur, classJoueur, strengthJoueur, defenseJoueur, agilityJoueur);

        //Fermer l'inventaire
        Button closeBtn = findViewById(R.id.ButtonClose);
        closeBtn.setOnClickListener(view -> {
            finish();
        });
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
        strengthStat.setText(String.valueOf(defenseJoueur));
        defenseStat.setText(String.valueOf(strengthJoueur));
        agilityStat.setText(String.valueOf(agilityJoueur));
    }
}