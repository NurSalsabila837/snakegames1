package com.example.snakegame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "SnakeGamePrefs";
    private static final String PLAYER_NAME_KEY = "playerName";
    private static final String DEFAULT_NAME = "Bob";

    private EditText nameInput;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        // Initialize SharedPreferences
        prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize views
        nameInput = findViewById(R.id.nameInput);

        // Load saved name
        String savedName = prefs.getString(PLAYER_NAME_KEY, DEFAULT_NAME);
        nameInput.setText(savedName);

        // Setup save button
        findViewById(R.id.saveButton).setOnClickListener(v -> {
            String newName = nameInput.getText().toString().trim();
            
            if (newName.isEmpty()) {
                nameInput.setError("Name cannot be empty");
                return;
            }

            // Save the name
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PLAYER_NAME_KEY, newName);
            editor.apply();

            Toast.makeText(this, "Name saved!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Helper method to get player name from anywhere in the app
    public static String getPlayerName(SharedPreferences prefs) {
        return prefs.getString(PLAYER_NAME_KEY, DEFAULT_NAME);
    }
} 