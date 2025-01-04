package com.example.snakegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ScoreEntry implements Comparable<ScoreEntry> {
    String name;
    int score;

    public ScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        return other.score - this.score; // For descending order
    }

    public static ArrayList<ScoreEntry> fromString(String str) {
        ArrayList<ScoreEntry> scores = new ArrayList<>();
        if (!str.isEmpty()) {
            String[] entries = str.split(",");
            for (String entry : entries) {
                String[] parts = entry.split(":");
                scores.add(new ScoreEntry(parts[0], Integer.parseInt(parts[1])));
            }
        }
        return scores;
    }

    public static String toString(ArrayList<ScoreEntry> scores) {
        return scores.stream()
                .map(s -> s.name + ":" + s.score)
                .collect(Collectors.joining(","));
    }
} 