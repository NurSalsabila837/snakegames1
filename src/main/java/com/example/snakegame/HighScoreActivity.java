package com.example.snakegame;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        // Set up action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("High Score");
        }

        // Get high scores from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("SnakeGamePrefs", MODE_PRIVATE);
        String scoresStr = prefs.getString("highScores", "");
        ArrayList<ScoreEntry> scores = ScoreEntry.fromString(scoresStr);

        // If no scores exist, add placeholder scores
        if (scores.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                scores.add(new ScoreEntry("player " + (i + 1), 0));
            }
        }

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.scoresRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HighScoreAdapter(scores));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
} 