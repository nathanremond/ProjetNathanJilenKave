package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SceneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);


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
            destinationScene(1);
        }


        //Redirection Inventaire
        Button invButton = findViewById(R.id.invBtn);
        invButton.setOnClickListener(view -> {
            openInventory(healthJoueur, expJoueur, goldJoueur, classJoueur, strengthJoueur, defenseJoueur, agilityJoueur);
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


    private void destinationScene(int currentId){
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
}