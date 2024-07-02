package com.itaybs.carcrash.Managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itaybs.carcrash.Models.ScoreEntry;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ScoreManager {
    private static final String PREF_NAME = "score_prefs";
    private static final String KEY_SCORES = "scores";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public ScoreManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Save a new score entry
    public void saveScoreEntry(ScoreEntry scoreEntry) {
        List<ScoreEntry> scoreList = getScoreEntries();
        scoreList.add(scoreEntry);

        String scoresJson = gson.toJson(scoreList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SCORES, scoresJson);
        editor.apply();
    }

    // Retrieve all score entries
    public List<ScoreEntry> getScoreEntries() {
        String scoresJson = sharedPreferences.getString(KEY_SCORES, null);
        Type type = new TypeToken<List<ScoreEntry>>() {}.getType();

        if (scoresJson == null) {
            return new ArrayList<>();
        }

        return gson.fromJson(scoresJson, type);
    }
}
