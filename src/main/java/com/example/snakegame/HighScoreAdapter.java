package com.example.snakegame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    private ArrayList<ScoreEntry> scores;

    public HighScoreAdapter(ArrayList<ScoreEntry> scores) {
        this.scores = scores;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_high_score, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScoreEntry score = scores.get(position);
        holder.playerNameText.setText(score.name);
        holder.scoreText.setText(String.valueOf(score.score));
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerNameText;
        TextView scoreText;

        ViewHolder(View view) {
            super(view);
            playerNameText = view.findViewById(R.id.playerNameText);
            scoreText = view.findViewById(R.id.scoreText);
        }
    }
} 