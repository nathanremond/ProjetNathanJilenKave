package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SceneActivity extends AppCompatActivity {

    private int healthJoueur, expJoueur, goldJoueur, strengthJoueur, defenseJoueur, agilityJoueur;
    private String classJoueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);


        //Récupération des stats du joueur depuis SharedPreferences
        loadStats();


        //Sauvegarde des stats du joueur dans SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("playerStats", MODE_PRIVATE);
        if (getIntent().hasExtra("healthJoueur")){
            healthJoueur = getIntent().getIntExtra("healthJoueur", 100);
            expJoueur = getIntent().getIntExtra("expJoueur", 0);
            goldJoueur = getIntent().getIntExtra("goldJoueur", 0);
            classJoueur = getIntent().getStringExtra("classJoueur");
            strengthJoueur = getIntent().getIntExtra("strengthJoueur", 0);
            defenseJoueur = getIntent().getIntExtra("defenseJoueur", 0);
            agilityJoueur = getIntent().getIntExtra("agilityJoueur", 0);

            saveStats();
        }


        String previousPage = getIntent().getStringExtra("previousPage");

        //Affichage contexte histoire
        if (Objects.equals(previousPage, "CharaChoice")){
            ContextScene();
        } else {
            destinationScene(1);
        }


        //Redirection Inventaire
        Button invButton = findViewById(R.id.invBtn);
        invButton.setOnClickListener(view -> {
            openInventory();
        });


        //Affichage de la carte
        Button mapButton = findViewById(R.id.mapBtn);
        mapButton.setOnClickListener(view -> {
            Intent intentToMapActivity = new Intent(this, MapActivity.class);
            startActivity(intentToMapActivity);
        });
    }

    //Affiche le texte d'introduction de l'histoire
    private void ContextScene() {
        String json = HelperJson.loadJSONFromRaw(this, R.raw.context);
        TextView textScene = findViewById(R.id.TextScene);
        Button btnChoice1 = findViewById(R.id.btnChoice1);
        Button btnChoice2 = findViewById(R.id.btnChoice2);

        if (json != null) {
            try {
                JSONObject scene = new JSONArray(json).getJSONObject(0);
                String texte = scene.getString("text");

                textScene.setText(texte);
                btnChoice1.setVisibility(View.GONE);
                btnChoice2.setVisibility(View.GONE);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR", "Erreur lors du chargement du fichier");
        }
    }

    private void openInventory(){
        saveStats();
        Intent intentToInventoryActivity = new Intent(this, InventoryActivity.class);
        startActivity(intentToInventoryActivity);
    }


    private void destinationScene(int currentId){
        Log.d("STATS", "Health: " + healthJoueur + ", EXP: " + expJoueur);
        String destination =  getIntent().getStringExtra("destination");
        int choice = destinationChoice(destination);

        String json = HelperJson.loadJSONFromRaw(this, choice);
        TextView textScene = findViewById(R.id.TextScene);
        Button btnChoice1 = findViewById(R.id.btnChoice1);
        Button btnChoice2 = findViewById(R.id.btnChoice2);

        if (json != null) {
            try {
                JSONArray scenes = new JSONArray(json);
                JSONObject currentScene = null;

                //Récupération de la scène actuelle
                for (int i = 0; i < scenes.length(); i++) {
                    JSONObject scene = scenes.getJSONObject(i);
                    if (scene.getInt("id") == currentId) {
                        currentScene = scene;
                        isPlayerDead(currentScene);
                        break;
                    }
                }
                if (currentScene != null) {
                    textScene.setText(currentScene.getString("text"));

                    //Récupération choix des enfants pour afficher dans la page parent
                    List<JSONObject> children = new ArrayList<>();
                    for (int i = 0; i < scenes.length(); i++) {
                        JSONObject scene = scenes.getJSONObject(i);
                        if (scene.getString("parentId").equals(String.valueOf(currentId))) {
                            children.add(scene);
                        }
                    }

                    // Afficher les boutons selon les choix
                    if (!children.isEmpty()) {
                        JSONObject child1 = children.get(0);
                        btnChoice1.setText(child1.getString("choice"));
                        btnChoice1.setOnClickListener(v -> {
                            saveStats();
                            try {
                                destinationScene(child1.getInt("id"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        btnChoice1.setVisibility(View.VISIBLE);
                    } else {
                        btnChoice1.setVisibility(View.GONE);
                    }

                    if (children.size() >= 2) {
                        JSONObject child2 = children.get(1);
                        btnChoice2.setText(child2.getString("choice"));
                        btnChoice2.setOnClickListener(v -> {
                            saveStats();
                            try {
                                destinationScene(child2.getInt("id"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        btnChoice2.setVisibility(View.VISIBLE);
                    } else {
                        btnChoice2.setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("ERROR", "Erreur lors du chargement du fichier");
        }
    }

    //Récupère le fichier json qui contient les textes propres à un lieu
    private int destinationChoice(String destination){
        int jsonFileName = 0;

        if (destination != null) {
            switch (destination) {
                case "Île de l’oubli":
                    jsonFileName = R.raw.island;
                    break;
                case "Donjon des collines":
                    jsonFileName = R.raw.dungeon;
                    break;
                case "Village Salty Springs":
                    jsonFileName = R.raw.village;
                    break;
                case "Château de l'Aincrad":
                    jsonFileName = R.raw.castle;
                    break;
                case "Shop":
                    jsonFileName = R.raw.shop;
                    break;
                default:
                    Toast.makeText(this, "Destination inconnue", Toast.LENGTH_SHORT).show();
            }
        }
        return jsonFileName;
    }

    private void isPlayerDead(JSONObject scene) {
        try {
            String status = scene.getString("status");
            String text = scene.getString("text");
            if ("dead".equals(status)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Fin de la partie")
                        .setMessage(text)
                        .setCancelable(false)
                        .setPositiveButton("Retour à l’accueil", (dialog, id) -> {
                            resetPlayerStats();
                            Intent intentToMainActivity = new Intent(this, MainActivity.class);
                            startActivity(intentToMainActivity);
                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Sauvegarde les stats pour les passer à la prochaine scène
    private void saveStats() {
        SharedPreferences sharedPreferences = getSharedPreferences("PlayerStats", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("healthJoueur", healthJoueur);
        editor.putInt("expJoueur", expJoueur);
        editor.putInt("goldJoueur", goldJoueur);
        editor.putString("classJoueur", classJoueur);
        editor.putInt("strengthJoueur", strengthJoueur);
        editor.putInt("defenseJoueur", defenseJoueur);
        editor.putInt("agilityJoueur", agilityJoueur);
        editor.apply();
    }

    //Récupère les stats pour les afficher dans n'importe quelle scène
    private void loadStats() {
        SharedPreferences sharedPreferences = getSharedPreferences("PlayerStats", MODE_PRIVATE);
        healthJoueur = sharedPreferences.getInt("healthJoueur", 100); // 100 est la valeur par défaut
        expJoueur = sharedPreferences.getInt("expJoueur", 0);
        goldJoueur = sharedPreferences.getInt("goldJoueur", 0);
        classJoueur = sharedPreferences.getString("classJoueur", "DefaultClass");
        strengthJoueur = sharedPreferences.getInt("strengthJoueur", 0);
        defenseJoueur = sharedPreferences.getInt("defenseJoueur", 0);
        agilityJoueur = sharedPreferences.getInt("agilityJoueur", 0);
    }

    private void resetPlayerStats() {
        SharedPreferences sharedPreferences = getSharedPreferences("PlayerStats", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
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