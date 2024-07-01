package com.itaybs.carcrash.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itaybs.carcrash.Managers.ScoreManager;
import com.itaybs.carcrash.R;
import com.itaybs.carcrash.Utilities.ScoreAdapter;
import com.itaybs.carcrash.Utilities.ScoreEntry;

import java.util.Date;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private ScoreAdapter adapter;
    private List<ScoreEntry> scoreList;

    private ScoreManager scoreManager;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.leaderboardContainer);

        // Initialize ScoreManager
        scoreManager = new ScoreManager(requireContext());

        // Load score data from ScoreManager
        scoreList = scoreManager.getScoreEntries();

        // If no scores are stored, populate with sample data
        if (scoreList.isEmpty()) {
            ScoreEntry score1 = new ScoreEntry(new Date(), 500);
            score1.setLatitude(31.4117257);
            score1.setLongitude(35.0818155);
            scoreList.add(score1);
        }

        // Create and set adapter
        adapter = new ScoreAdapter(scoreList, (latitude, longitude) -> {
            // Notify the activity about the score click
            if (getActivity() instanceof ScoreAdapter.OnScoreClickListener) {
                ((ScoreAdapter.OnScoreClickListener) getActivity()).onScoreClick(latitude, longitude);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    // Method to set the adapter for RecyclerView
    public void setScoreAdapter(ScoreAdapter adapter) {
        this.adapter = adapter;
        if (recyclerView != null) {
            recyclerView.setAdapter(adapter);
        }
    }
}
