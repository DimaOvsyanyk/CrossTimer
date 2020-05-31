package com.dimaoprog.crosstimer.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.io.IOException;

public class SoundPlayer {

    private static final String SOUND_NAME_PATH = "sounds/sound.mp3";
    private static final String SOUND_START_FINISH_PATH = "sounds/sound_start_finish.mp3";


    private int soundId;
    private int soundStartFinishId;
    private AssetManager assetManager;
    private SoundPool soundPool;

    public SoundPlayer(Context context) {
        assetManager = context.getAssets();
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    private void loadSounds() {
        AssetFileDescriptor assetFD;
        try {
            assetFD = assetManager.openFd(SOUND_NAME_PATH);
            soundId = soundPool.load(assetFD, 1);

            assetFD = assetManager.openFd(SOUND_START_FINISH_PATH);
            soundStartFinishId = soundPool.load(assetFD, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playSound() {
        soundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playSoundStartFinish() {
        soundPool.play(soundStartFinishId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void release() {
        soundPool.release();
    }
}