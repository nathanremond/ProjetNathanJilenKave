package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene);

        //Pour récupérer les données du joueur
        player = new PlayerData(this);


        TextView messageFightView = findViewById(R.id.messageFight);
        messageFightView.setVisibility(View.GONE);
        Button btnContinue = findViewById(R.id.btnContinueFight);
        btnContinue.setVisibility(View.GONE);
        Button btnStartFight = findViewById(R.id.btnStartFight);
        btnStartFight.setVisibility(View.GONE);
        ProgressBar playerHealthBar = findViewById(R.id.playerHealthBar);
        playerHealthBar.setVisibility(View.GONE);
        ProgressBar monsterHealthBar = findViewById(R.id.monsterHealthBar);
        monsterHealthBar.setVisibility(View.GONE);
        Button btnEnd = findViewById(R.id.btnEnd);
        btnEnd.setVisibility(View.GONE);

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
        Button btnContinue = findViewById(R.id.btnContinueFight);
        Button btnEnd = findViewById(R.id.btnEnd);
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
                        isPlayerVictorious(currentScene);
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
                        btnChoice1.setVisibility(View.GONE);
                        btnChoice2.setVisibility(View.GONE);

                        Button btnStartFight = findViewById(R.id.btnStartFight);
                        btnStartFight.setVisibility(View.VISIBLE);

                        if (currentScene.getString("event").equals("zombie") || currentScene.getString("event").equals("ogre") || currentScene.getString("event").equals("dragon") || currentScene.getString("event").equals("chevalier obscur")) {
                            String monster = currentScene.getString("event");
                            initMonster(monster);
                        }

                        List<JSONObject> finalChildren = new ArrayList<>(children);

                        btnStartFight.setOnClickListener(v -> {
                            btnStartFight.setVisibility(View.GONE);
                            try {
                                fight(messageFightView,
                                    () -> {
                                        stopMusic();
                                        JSONObject child1 = finalChildren.get(0);
                                        btnContinue.setVisibility(View.VISIBLE);
                                        btnContinue.setOnClickListener(view -> {
                                            btnContinue.setVisibility(View.GONE);
                                            try {
                                                destinationScene(child1.getInt("id"));
                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                                    },
                                    () -> {
                                        stopMusic();
                                        JSONObject child2 = finalChildren.get(1);
                                        btnContinue.setVisibility(View.VISIBLE);
                                        btnContinue.setOnClickListener(view -> {
                                            btnContinue.setVisibility(View.GONE);
                                            try {
                                                destinationScene(child2.getInt("id"));
                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });
                                    }
                                );
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        return;
                    }

                    if (currentScene.getString("status").equals("end")) {
                        btnEnd.setVisibility(View.VISIBLE);
                        btnEnd.setOnClickListener(view -> {
                            btnEnd.setVisibility(View.GONE);
                            Intent intentToMapActivity = new Intent(SceneActivity.this, MapActivity.class);
                            startActivity(intentToMapActivity);
                            finish();
                        });

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
                    Intent intentToShopActivity = new Intent(this, ShopActivity.class);
                    startActivity(intentToShopActivity);
                    finish();
                    return 0;
            }
        }
        return jsonFileName;
    }

    private void isPlayerDead(JSONObject scene) {
        try {
            String status = scene.getString("status");
            if ("dead".equals(status)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Fin de la partie")
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

    private void isPlayerVictorious(JSONObject scene) {
        try {
            String status = scene.getString("status");
            if ("victory".equals(status)) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Fin de la partie")
                        .setCancelable(false)
                        .setPositiveButton("Vous avez gagné la partie !", (dialog, id) -> {
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
    private void fight(TextView messageFight, Runnable onVictory, Runnable onDefeat) throws JSONException {
        ProgressBar playerHealthBar = findViewById(R.id.playerHealthBar);
        ProgressBar monsterHealthBar = findViewById(R.id.monsterHealthBar);

        playerHealthBar.setVisibility(View.VISIBLE);
        monsterHealthBar.setVisibility(View.VISIBLE);

        messageFight.setVisibility(View.VISIBLE);

        mediaPlayer = MediaPlayer.create(this, R.raw.fight);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(1.0f, 1.0f);
        mediaPlayer.start();

        MonsterData monster = new MonsterData(this);

        final int[] strengthMonster = { monster.getStrength() };
        final int[] defenseMonster = { monster.getDefense() };
        final int[] healthMonster = { monster.getHealth() };

        final int[] strengthPlayer = { player.getStrength() };
        final int[] defensePlayer = { player.getDefense() };
        final int[] healthPlayer = { player.getHealth() };

        final int playerAttack = player.getWeaponDamage() * strengthPlayer[0];
        final int playerDefense = player.getArmorDefense() * defensePlayer[0];

        playerHealthBar.setMax(healthPlayer[0]);
        playerHealthBar.setProgress(healthPlayer[0]);

        monsterHealthBar.setMax(healthMonster[0]);
        monsterHealthBar.setProgress(healthMonster[0]);

        final Handler handler = new Handler();
        final int[] tour = { 1 };

        Runnable combatRound = new Runnable() {
            @Override
            public void run() {
                //On vérifie si le combat est terminé
                if (healthPlayer[0] <= 0 || healthMonster[0] <= 0) {
                    messageFight.setVisibility(View.GONE);
                    playerHealthBar.setVisibility(View.GONE);
                    monsterHealthBar.setVisibility(View.GONE);
                    if (healthMonster[0] <= 0) {
                        int gainedGold = monster.getGold();
                        player.setGold(player.getGold() + gainedGold);
                        player.setExp(player.getExp() + 3);
                        onVictory.run();
                    } else {
                        messageInFight(messageFight, "Défaite ! Vous avez été vaincu.");
                        onDefeat.run();
                    }
                    return;
                }
              
                messageInFight(messageFight, "Tour " + tour[0]);

                handler.postDelayed(() -> {
                    // Attaque du joueur
                    int playerDamage = Math.max(playerAttack - defenseMonster[0], 0);
                    healthMonster[0] = Math.max(healthMonster[0] - playerDamage, 0);
                    monsterHealthBar.setProgress(healthMonster[0]);
                    messageInFight(messageFight, "Le joueur inflige " + playerDamage + " dégâts. PV Monstre : " + healthMonster[0]);

                    if (healthMonster[0] <= 0) {
                        handler.postDelayed(this, 1500);
                        return;
                    }

                    handler.postDelayed(() -> {
                        // Attaque du monstre
                        int monsterDamage = Math.max(strengthMonster[0] - playerDefense, 0);
                        healthPlayer[0] = Math.max(healthPlayer[0] - monsterDamage, 0);
                        playerHealthBar.setProgress(healthPlayer[0]);
                        messageInFight(messageFight, "Le monstre inflige " + monsterDamage + " dégâts. PV Joueur : " + healthPlayer[0]);

                        tour[0]++;
                        handler.postDelayed(this, 1500);
                    }, 1500);
                }, 1500);
            }
        };

        handler.post(combatRound);
    }

    private void initMonster(String monsterName) {
        MonsterData monster = new MonsterData(this);

        switch (monsterName) {
            case "zombie":
                monster.setMonster("Zombie", 30, 10, 5, 10);
                break;
            case "ogre":
                monster.setMonster("Ogre", 50, 20, 10, 20);
                break;
            case "dragon":
                monster.setMonster("Dragon", 100, 30, 15, 40);
                break;
            case "chevalier obscur":
                monster.setMonster("Chevalier obscur", 200, 50, 20, 100);
                break;
            default:
                monster.setMonster("Inconnu", 0, 0, 0, 0);
                break;
        }
    }

    private void messageInFight(TextView messageFight, String message) {
        messageFight.setText(message);
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}