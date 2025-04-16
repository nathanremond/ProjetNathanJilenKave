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
    private PlayerData player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        //Pour récupérer les données du joueur
        player = new PlayerData(this);

        TextView messageFightView = findViewById(R.id.messageFight);
        messageFightView.setVisibility(View.GONE);

        //Affichage contexte histoire
        String previousPage = getIntent().getStringExtra("previousPage");
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
        Intent intentToInventoryActivity = new Intent(this, InventoryActivity.class);
        startActivity(intentToInventoryActivity);
    }


    private void destinationScene(int currentId){
        TextView messageFightView = findViewById(R.id.messageFight);
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

                    //Récupération choix des enfants pour afficher dans la scène parent
                    List<JSONObject> children = new ArrayList<>();
                    for (int i = 0; i < scenes.length(); i++) {
                        JSONObject scene = scenes.getJSONObject(i);
                        if (scene.getString("parentId").equals(String.valueOf(currentId))) {
                            children.add(scene);
                        }
                    }

                    //on vérifie s'il y a un combat et on l'exécute si oui
                    if (currentScene.getString("fight").equals("yes")) {
                        if (currentScene.getString("event").equals("zombie") || currentScene.getString("event").equals("ogre") || currentScene.getString("event").equals("dragon") || currentScene.getString("event").equals("chevalier obscur")) {
                            String monster = currentScene.getString("event");
                            initMonster(monster);
                        }
                        String result = fight(currentScene, messageFightView);

                        if (result.equals("victoire")) {
                            JSONObject child1 = children.get(0);
                            destinationScene(child1.getInt("id"));
                        } else {
                            JSONObject child2 = children.get(1);
                            destinationScene(child2.getInt("id"));
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
                            player.resetPlayerData();
                            Intent intentToMainActivity = new Intent(this, MainActivity.class);
                            startActivity(intentToMainActivity);
                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Combat entre le joueur et un monstre
    private String fight(JSONObject currentScene, TextView messageFight) throws JSONException {
        messageFight.setVisibility(View.VISIBLE);
        MonsterData monster = new MonsterData(this);

        int strengthMonster = monster.getStrength();
        int defenseMonster = monster.getDefense();
        int healthMonster = monster.getHealth();

        int strengthPlayer = player.getStrength();
        int defensePlayer = player.getDefense();
        int healthPlayer = player.getHealth();

        int playerAttack = player.getWeaponDamage() * (strengthPlayer / 10);
        int playerDefense = player.getArmorDefense() * (defensePlayer / 10);

        int tour = 1;
        String res = null;

        while (healthPlayer > 0 && healthMonster > 0) {
            messageInFight(messageFight, "Tour " + tour);

            int playerDamages = playerAttack - defenseMonster;
            playerDamages = Math.max(playerDamages, 0);
            int newMonsterHealth = healthMonster - playerDamages;
            monster.setHealth(Math.max(newMonsterHealth, 0));
            messageInFight(messageFight, "Le joueur inflige " + playerDamages + " dégâts. PV Monstre : " + healthMonster);

            if (healthMonster <= 0) {
                messageInFight(messageFight, "Victoire ! Le monstre est vaincu !");
                res = "victoire";
                break;
            }

            int monsterDamages = strengthMonster - playerDefense;
            monsterDamages = Math.max(monsterDamages, 0);
            int newPlayerHealth = healthPlayer - monsterDamages;
            player.setHealth(Math.max(newPlayerHealth, 0));
            messageInFight(messageFight, "Le monstre inflige " + monsterDamages + " dégâts. PV Joueur : " + healthPlayer);


            if (healthPlayer <= 0) {
                messageInFight(messageFight, "Défaite ! Vous avez été vaincu.");
                res = "défaite";
                break;
            }

            tour++;
        }
        if (res.equals("victoire")) {
            int gainedGold = monster.getGold();
            player.setGold(player.getGold() + gainedGold);
            player.setExp(player.getExp() + 3);
            messageInFight(messageFight, "Vous gagnez " + gainedGold + " pièces d'or et 3 points d'expérience !");
        }
        messageFight.setVisibility(View.GONE);
        return res;
    }

    private void initMonster(String monsterName) {
        MonsterData monster = new MonsterData(this);

        switch (monsterName) {
            case "zombie":
                monster.setMonster("Zombie", 30, 5, 2, 10);
                break;
            case "ogre":
                monster.setMonster("Ogre", 50, 8, 5, 20);
                break;
            case "dragon":
                monster.setMonster("Dragon", 150, 20, 10, 100);
                break;
            case "chevalier obscur":
                monster.setMonster("Chevalier obscur", 150, 20, 10, 100);
                break;
            default:
                monster.setMonster("Inconnu", 0, 0, 0, 0);
                break;
        }
    }

    private void messageInFight(TextView messageFight, String message) {
        String currentText = messageFight.getText().toString();
        messageFight.setText(currentText + "\n" + message);
    }
}