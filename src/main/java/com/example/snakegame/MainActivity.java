package com.example.snakegame;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar in main menu
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Setup button click listeners
        findViewById(R.id.newGameButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, GameActivity.class));
        });

        findViewById(R.id.highScoreButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HighScoreActivity.class));
        });

        findViewById(R.id.settingsButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        });

        findViewById(R.id.aboutButton).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });
    }
}