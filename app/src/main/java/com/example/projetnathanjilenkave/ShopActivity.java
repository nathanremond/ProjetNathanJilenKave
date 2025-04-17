package com.example.projetnathanjilenkave;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Button buyBtn1 = findViewById(R.id.buyBtn1);
        buyBtn1.setOnClickListener(v -> {
            handlePurchase(R.id.price1, R.id.nameProduct1, R.id.descriptionProduct1, R.id.textStat1, R.id.textrarity1, R.id.rarity1, R.id.buyBtn1);
        });

        Button buyBtn2 = findViewById(R.id.buyBtn2);
        buyBtn2.setOnClickListener(v -> {
            handlePurchase(R.id.price2, R.id.nameProduct2, R.id.descriptionProduct2, R.id.textStat2, R.id.textrarity2, R.id.rarity2, R.id.buyBtn2);
        });

        Button buyBtn3 = findViewById(R.id.buyBtn3);
        buyBtn3.setOnClickListener(v -> {
            handlePurchase(R.id.price3, R.id.nameProduct3, R.id.descriptionProduct3, R.id.textStat3, R.id.textrarity3, R.id.rarity3, R.id.buyBtn3);
        });

        Button buyBtn4 = findViewById(R.id.buyBtn4);
        buyBtn4.setOnClickListener(v -> {
            handlePurchase(R.id.price4, R.id.nameProduct4, R.id.descriptionProduct4, R.id.textStat4, R.id.textrarity4, R.id.rarity5, R.id.buyBtn4);
        });

        Button buyBtn5 = findViewById(R.id.buyBtn5);
        buyBtn5.setOnClickListener(v -> {
            handlePurchase(R.id.price5, R.id.nameProduct5, R.id.descriptionProduct5, R.id.textStat5, R.id.textrarity5, R.id.rarity5, R.id.buyBtn5);
        });


        Button returnButton = findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void handlePurchase(int priceId, int nameProductId, int descriptionId, int textStatID, int textRarityID, int rarityID, int buttonId) {
        TextView valeur = findViewById(R.id.value);
        TextView prix = findViewById(R.id.price1);

        int playerGold = Integer.parseInt(valeur.getText().toString());
        int productPrice = Integer.parseInt(prix.getText().toString());

        if (playerGold >= productPrice) {
            playerGold -= productPrice;
            valeur.setText(String.valueOf(playerGold));
            Toast.makeText(this, "Achat effectué !", Toast.LENGTH_SHORT).show();

            TextView price = findViewById(priceId);
            TextView nameProduct = findViewById(nameProductId);
            TextView decriptionProduct = findViewById(descriptionId);
            Button buttonProduct = findViewById(buttonId);

            findViewById(priceId).setVisibility(View.GONE);
            findViewById(nameProductId).setVisibility(View.GONE);
            findViewById(descriptionId).setVisibility(View.GONE);
            findViewById(textStatID).setVisibility(View.GONE);
            findViewById(textRarityID).setVisibility(View.GONE);
            findViewById(rarityID).setVisibility(View.GONE);
            findViewById(buttonId).setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Pas assez d'or !", Toast.LENGTH_SHORT).show();
        }
    }


}