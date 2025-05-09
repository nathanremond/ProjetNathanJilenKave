package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        setupButton(R.id.btnIle, "Île de l’oubli");
        setupButton(R.id.btnDonjon, "Donjon des collines");
        setupButton(R.id.btnVillage, "Village Salty Springs");
        setupButton(R.id.btnChateau, "Château de l'Aincrad");
        setupButton(R.id.btnShop, "Shop");

        Button btnInventory = findViewById(R.id.btnInventory);
        btnInventory.setOnClickListener(view -> {
            Intent intentToInventoryActivity = new Intent(this, InventoryActivity.class);
            startActivity(intentToInventoryActivity);
        });
    }

    private void setupButton(int buttonId, String destinationName) {
        Button button = findViewById(buttonId);
        if (button != null) {
            button.setOnClickListener(v -> showConfirmationDialog(destinationName));
        }
    }

    private void showConfirmationDialog(String destination) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Souhaitez-vous aller à : " + destination + " ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    Intent intentToSceneActivity = new Intent(this, SceneActivity.class);
                    intentToSceneActivity.putExtra("destination", destination);
                    intentToSceneActivity.putExtra("previousPage", "Map");
                    startActivity(intentToSceneActivity);
                })
                .setNegativeButton("Non", null)
                .show();
    }
}