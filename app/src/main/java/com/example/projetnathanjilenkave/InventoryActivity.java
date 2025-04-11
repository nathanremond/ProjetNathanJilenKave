package com.example.projetnathanjilenkave;

import android.content.Intent;
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

        //Stats personnage
        int healthJoueur = getIntent().getIntExtra("healthJoueur", 100);
        int expJoueur = getIntent().getIntExtra("expJoueur", 0);
        int goldJoueur = getIntent().getIntExtra("goldJoueur", 0);
        String classJoueur = getIntent().getStringExtra("classJoueur");
        int strengthJoueur = getIntent().getIntExtra("strengthJoueur", 0);
        int defenseJoueur = getIntent().getIntExtra("defenseJoueur", 0);
        int agilityJoueur = getIntent().getIntExtra("agilityJoueur", 0);
        String previousPage = getIntent().getStringExtra("previousPage");

        displayCharaStats(healthJoueur, expJoueur, goldJoueur, classJoueur, strengthJoueur, defenseJoueur, agilityJoueur);

        Weapon a1 = new Weapon("épée", "commune", 4, 30);
        Armor ar1 = new Armor("armure", "rare", 10, 50);
        Object o1 = new Object("torche", "Un outil permettant d'éclairer", 20);

        //Fermer l'inventaire
        Button closeBtn = findViewById(R.id.ButtonClose);
        closeBtn.setOnClickListener(view -> {
            finish();
        });



        TextView nameWeapon = findViewById(R.id.WeaponName);
        TextView rarityWeapon = findViewById(R.id.WeaponRarity);
        TextView bonusWeapon = findViewById(R.id.WeaponBonusStat);

        TextView nameObject = findViewById(R.id.NameObject1);
        TextView descriptionObject = findViewById(R.id.DescriptionObject1);



        nameWeapon.setText(String.valueOf(a1.getName()));
        rarityWeapon.setText(String.valueOf(a1.getRarity()));
        bonusWeapon.setText(String.valueOf(a1.getBonusStrength()));

        nameObject.setText(String.valueOf(o1.getName()));
        descriptionObject.setText(String.valueOf(o1.getDescription()));


    }

    private void displayCharaStats(int healthJoueur, int expJoueur, int goldJoueur, String classJoueur, int strengthJoueur, int defenseJoueur, int agilityJoueur){
        TextView healthStat = findViewById(R.id.StatHealthValue);
        TextView expStat = findViewById(R.id.StatExpValue);
        TextView goldStat = findViewById(R.id.GoldValue);
        TextView classStat = findViewById(R.id.StatClassValue);
        TextView defenseStat = findViewById(R.id.StatDefenseValue);
        TextView strengthStat = findViewById(R.id.StatStrengthValue);
        TextView agilityStat = findViewById(R.id.StatAgilityValue);

        healthStat.setText(String.valueOf(healthJoueur));
        expStat.setText(String.valueOf(expJoueur));
        goldStat.setText(String.valueOf(goldJoueur));
        classStat.setText(classJoueur);
        defenseStat.setText(String.valueOf(strengthJoueur));
        strengthStat.setText(String.valueOf(defenseJoueur));
        agilityStat.setText(String.valueOf(agilityJoueur));
    }
}