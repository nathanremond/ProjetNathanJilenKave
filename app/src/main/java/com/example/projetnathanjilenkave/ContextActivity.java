package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class ContextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context);

        String previousPage = getIntent().getStringExtra("previousPage");

        if (Objects.equals(previousPage, "CharaChoice")){
            ContextScene();
        } else {
            return;
        }
    }

    private void ContextScene() {
        String json = HelperJson.loadJSONFromRaw(this, R.raw.context);
        TextView textScene = findViewById(R.id.TextScene);

        if (json != null) {
            try {
                JSONObject scene = new JSONArray(json).getJSONObject(0);
                String texte = scene.getString("text");

                textScene.setText(texte);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR", "Erreur lors du chargement du fichier");
        }


        Button mapButton = findViewById(R.id.mapBtn);

        mapButton.setOnClickListener(view -> {
            Intent intentToMapActivity = new Intent(this, MapActivity.class);
            startActivity(intentToMapActivity);
        });
    }

    private void otherPlace(){
        String json = HelperJson.loadJSONFromRaw(this, R.raw.context);
        TextView textScene = findViewById(R.id.TextScene);

        String texte = "";

        if (json != null) {
            try {
                JSONArray scenes = new JSONArray(json);
                for (int i = 0; i < scenes.length(); i++) {
                    JSONObject scene = scenes.getJSONObject(i);
                    texte = scene.getString("text");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR", "Erreur lors du chargement du fichier");
        }
    }

    private void Fight() {
        int strengthJoueur = getIntent().getIntExtra("strengthJoueur", 0);
        int defenseJoueur = getIntent().getIntExtra("defenseJoueur", 0);
        int healthJoueur = getIntent().getIntExtra("healthJoueur", 0);
        int bonusStrength = getIntent().getIntExtra("bonusStrength", 0);
        int bonusDefense = getIntent().getIntExtra("bonusDefense", 0);

        int strengthMonstre = getIntent().getIntExtra("strengthMonstre", 0);
        int defenseMonstre = getIntent().getIntExtra("defenseMonstre", 0);
        int healthMonstre = getIntent().getIntExtra("healthMonstre", 0);

        int attack = bonusStrength * (strengthJoueur / 10);
        int defense = bonusDefense * (defenseJoueur / 10);


        int tour = 1;

        while (healthJoueur > 0 && healthMonstre > 0) {
            Log.d("Combat", "Tour " + tour);

            int degatsJoueur = attack - defenseMonstre;
            degatsJoueur = Math.max(degatsJoueur, 0);
            healthMonstre -= degatsJoueur;
            Log.d("Combat", "Le joueur inflige " + degatsJoueur + " de dégâts. PV monstre: " + healthMonstre);

            if (healthMonstre <= 0) {
                Log.d("Combat", "Victoire ! Le monstre est vaincu !");
                break;
            }
            
            int degatsMonstre = strengthMonstre - defense;
            degatsMonstre = Math.max(degatsMonstre, 0);
            healthJoueur -= degatsMonstre;
            Log.d("Combat", "Le monstre inflige " + degatsMonstre + " de dégâts. PV joueur: " + healthJoueur);

            if (healthJoueur <= 0) {
                Log.d("Combat", "Défaite! Le joueur a été vaincu.");
                break;
            }

            tour++;
        }
    }
}