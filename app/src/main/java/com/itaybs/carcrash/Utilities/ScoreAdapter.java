package com.itaybs.carcrash.Utilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itaybs.carcrash.R;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<ScoreEntry> scoreList;

    // Constructor to initialize with a list of scores
    public ScoreAdapter(List<ScoreEntry> scoreList) {
        this.scoreList = scoreList;
    }

    // ViewHolder class to hold references to views
    public static class ScoreViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView scoreTextView;

        public ScoreViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create ViewHolder instance
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        // Bind data to views based on position
        ScoreEntry score = scoreList.get(position);
        holder.dateTextView.setText(score.getDate().toString());
        holder.scoreTextView.setText(String.valueOf(score.getScore()));
    }

    @Override
    public int getItemCount() {
        // Return the size of the dataset (number of items to display)
        return scoreList.size();
    }
}
