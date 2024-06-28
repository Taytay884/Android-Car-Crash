package com.itaybs.carcrash.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {

    private Context context;
    private Executor executor;
    private MediaPlayer mediaPlayer;

    public SoundPlayer(Context context) {
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playSound(int resID, boolean looping) {
        if (mediaPlayer == null) {
            executor.execute(() -> {
                mediaPlayer = MediaPlayer.create(context, resID);
                mediaPlayer.setLooping(looping);
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.start();
            });
        } else {
            executor.execute(() -> {
                mediaPlayer.start();
            });
        }
    }

    public void stopSound() {
        if (mediaPlayer != null) {
            executor.execute(() -> {
                mediaPlayer.pause();
                mediaPlayer.release();
                mediaPlayer = null;
            });
        }
    }

    public void pauseSound() {
        if (mediaPlayer != null) {
            executor.execute(() -> {
                mediaPlayer.pause();
            });
        }
    }
}
