package com.itaybs.carcrash;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itaybs.carcrash.Fragments.LeaderboardFragment;
import com.itaybs.carcrash.Fragments.MapFragment;
import com.itaybs.carcrash.Utilities.ScoreAdapter;
import com.itaybs.carcrash.Utilities.ScoreEntry;

import java.util.List;

public class LeaderboardActivity extends AppCompatActivity implements ScoreAdapter.OnScoreClickListener {
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Load LeaderboardFragment
        LeaderboardFragment leaderboardFragment = new LeaderboardFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.leaderboardContainer, leaderboardFragment)
                .commit();

        // Load MapFragment
        mapFragment = new MapFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mapContainer, mapFragment)
                .commit();

        // Set the adapter for the RecyclerView in LeaderboardFragment
        // Ensure that you pass `this` to the adapter as the click listener
        leaderboardFragment.setScoreAdapter(new ScoreAdapter(getScoreList(), this));
    }

    @Override
    public void onScoreClick(double latitude, double longitude) {
        if (mapFragment != null) {
            mapFragment.moveToLocation(latitude, longitude);
        }
    }

    private List<ScoreEntry> getScoreList() {
        // Return your list of scores here
        return null;
    }
}
