package com.example.projetnathanjilenkave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playBtn = findViewById(R.id.play_button);

        playBtn.setOnClickListener(view -> {
            Intent intentToCharacterChoiceActivity = new Intent(this, CharacterChoiceActivity.class);
            startActivity(intentToCharacterChoiceActivity);
        });
    }
}