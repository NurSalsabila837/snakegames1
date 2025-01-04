package com.example.snakegame;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        int score = getIntent().getIntExtra("SCORE", 0);
        
        // Set score text
        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Your score: " + score);

        // Set up try again button
        findViewById(R.id.tryAgainButton).setOnClickListener(v -> {
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });
    }
} 