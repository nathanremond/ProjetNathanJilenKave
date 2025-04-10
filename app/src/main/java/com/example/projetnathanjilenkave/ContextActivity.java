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

        //Stats personnage
        int healthJoueur = getIntent().getIntExtra("healthJoueur", 100);
        int expJoueur = getIntent().getIntExtra("expJoueur", 0);
        int goldJoueur = getIntent().getIntExtra("goldJoueur", 0);
        String classJoueur = getIntent().getStringExtra("classJoueur");
        int strengthJoueur = getIntent().getIntExtra("strengthJoueur", 0);
        int defenseJoueur = getIntent().getIntExtra("defenseJoueur", 0);
        int agilityJoueur = getIntent().getIntExtra("agilityJoueur", 0);
        String previousPage = getIntent().getStringExtra("previousPage");


        //Affichage contexte histoire
        if (Objects.equals(previousPage, "CharaChoice")){
            ContextScene();
        } else {
            return;
        }


        //Redirection Inventaire
        Button invButton = findViewById(R.id.invBtn);
        invButton.setOnClickListener(view -> {
            openInventory(healthJoueur, expJoueur, goldJoueur, classJoueur, strengthJoueur, defenseJoueur, agilityJoueur);
        });
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

    private void openInventory(int healthJoueur, int expJoueur, int goldJoueur, String classJoueur, int strengthJoueur, int defenseJoueur, int agilityJoueur){
        Intent intentToInventoryActivity = new Intent(this, InventoryActivity.class);
        intentToInventoryActivity.putExtra("healthJoueur", healthJoueur);
        intentToInventoryActivity.putExtra("expJoueur", expJoueur);
        intentToInventoryActivity.putExtra("goldJoueur", goldJoueur);
        intentToInventoryActivity.putExtra("classJoueur", classJoueur);
        intentToInventoryActivity.putExtra("strengthJoueur", strengthJoueur);
        intentToInventoryActivity.putExtra("defenseJoueur", defenseJoueur);
        intentToInventoryActivity.putExtra("agilityJoueur", agilityJoueur);
        startActivity(intentToInventoryActivity);
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
}