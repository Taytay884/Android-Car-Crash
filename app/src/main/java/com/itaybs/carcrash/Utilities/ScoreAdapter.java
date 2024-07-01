package com.itaybs.carcrash.Utilities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itaybs.carcrash.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<ScoreEntry> scoreList;
    private OnScoreClickListener onScoreClickListener;
    private int selectedPosition = -1; // To keep track of the selected item

    // Constructor to initialize with a list of scores and a click listener
    public ScoreAdapter(List<ScoreEntry> scoreList, OnScoreClickListener onScoreClickListener) {
        this.scoreList = scoreList;
        this.onScoreClickListener = onScoreClickListener;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        // Bind data to views based on position
        ScoreEntry score = scoreList.get(position);
        holder.dateTextView.setText(dateFormat.format(score.getDate()));
        holder.scoreTextView.setText(String.valueOf(score.getScore()));

        // Highlight the selected item
        holder.itemView.setBackgroundColor(position == selectedPosition ?
                holder.itemView.getResources().getColor(R.color.selected_item_color) :
                holder.itemView.getResources().getColor(R.color.default_item_color));

        // Set click listener for item
        holder.itemView.setOnClickListener(v -> {
            if (onScoreClickListener != null) {
                onScoreClickListener.onScoreClick(score.getLatitude(), score.getLongitude());
                notifyItemChanged(selectedPosition); // Unselect previous item
                selectedPosition = holder.getAdapterPosition(); // Select new item
                notifyItemChanged(selectedPosition); // Highlight new item
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the size of the dataset (number of items to display)
        return scoreList.size();
    }

    // Define an interface for handling score clicks
    public interface OnScoreClickListener {
        void onScoreClick(double latitude, double longitude);
    }
}
