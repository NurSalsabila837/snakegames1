package com.example.snakegame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity implements GameView.GameOverListener {
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Create and add GameView to container
        gameView = new GameView(this);
        gameView.setGameOverListener(this);
        FrameLayout container = findViewById(R.id.gameContainer);
        container.addView(gameView);

        // Set up control buttons
        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);

        upButton.setOnClickListener(v -> gameView.setDirection(GameView.Direction.UP));
        downButton.setOnClickListener(v -> gameView.setDirection(GameView.Direction.DOWN));
        leftButton.setOnClickListener(v -> gameView.setDirection(GameView.Direction.LEFT));
        rightButton.setOnClickListener(v -> gameView.setDirection(GameView.Direction.RIGHT));

        // Set up action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Your score: 0");
        }
    }

    @Override
    public void onGameOver(int score) {
        // Save score to SharedPreferences
        SharedPreferences prefs = getSharedPreferences("SnakeGamePrefs", MODE_PRIVATE);
        String playerName = SettingsActivity.getPlayerName(prefs);
        saveHighScore(playerName, score);

        // Start GameOverActivity
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
        finish();
    }

    private void saveHighScore(String playerName, int score) {
        SharedPreferences prefs = getSharedPreferences("SnakeGamePrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        
        // Get existing scores
        String scoresStr = prefs.getString("highScores", "");
        ArrayList<ScoreEntry> scores = ScoreEntry.fromString(scoresStr);
        
        // Add new score
        scores.add(new ScoreEntry(playerName, score));
        
        // Sort and limit to top 10
        Collections.sort(scores);
        if (scores.size() > 10) {
            scores = new ArrayList<>(scores.subList(0, 10));
        }
        
        // Save back to preferences
        editor.putString("highScores", ScoreEntry.toString(scores));
        editor.apply();
    }
} 