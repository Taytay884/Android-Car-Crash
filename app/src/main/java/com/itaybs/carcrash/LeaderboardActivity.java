package com.itaybs.carcrash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itaybs.carcrash.Fragments.LeaderboardFragment;

public class LeaderboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Load LeaderboardFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leaderboardContainer, new LeaderboardFragment())
                .commit();

        // Load MapFragment
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.mapContainer, new MapFragment())
//                .commit();
    }
}