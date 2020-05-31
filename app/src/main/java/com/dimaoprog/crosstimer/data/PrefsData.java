package com.dimaoprog.crosstimer.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.dimaoprog.crosstimer.models.Timer;

public class PrefsData {

    private final static String APP_PREFS = "timer_prefs";

    private final static String INTERVAL_MODE = "intervalMode";
    private final static String SW_MODE = "swMode";
    private final static String FGB_MODE = "fgbMode";
    private final static String TABATA_MODE = "tabataMode";
    private final static String ROUNDS = "rounds";
    private final static String WORK = "work";
    private final static String REST = "rest";
    private final static String COUNTDOWN = "countdown";
    private final static String TEN_SEC_START = "tenSecStart";
    private final static String SOUND = "sound";

    private SharedPreferences sharedPrefs;

    public PrefsData(Application application) {
        sharedPrefs = application.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public void cleanTimerSettings() {
        sharedPrefs.edit().clear().apply();
    }

    public void setTimerSettings(Timer timer) {
        setIntervalMode(timer.isInterval());
        setSwMode(timer.isSW());
        setFgbMode(timer.isFGB());
        setTabataMode(timer.isTabata());
        setROUNDS(timer.getRounds());
        setWORK(timer.getWork());
        setREST(timer.getRest());
        setCOUNTDOWN(timer.isCountdown());
        setTenSecStart(timer.isNeedTenSecStart());
        setSOUND(timer.isNeedSound());
    }

    public Timer getTimerSettings() {
        return new Timer(
                isIntervalMode(),
                isSwMode(),
                isFgbMode(),
                isTabataMode(),
                getROUNDS(),
                getWORK(),
                getREST(),
                isCOUNTDOWN(),
                needTenSecStart(),
                needSOUND());
    }

    public boolean isIntervalMode() {
        return sharedPrefs.getBoolean(INTERVAL_MODE, true);
    }

    public boolean isSwMode() {
        return sharedPrefs.getBoolean(SW_MODE, false);
    }

    public boolean isFgbMode() {
        return sharedPrefs.getBoolean(FGB_MODE, false);
    }

    public boolean isTabataMode() {
        return sharedPrefs.getBoolean(TABATA_MODE, false);
    }

    public int getROUNDS() {
        return sharedPrefs.getInt(ROUNDS, 1);
    }

    public long getWORK() {
        return sharedPrefs.getLong(WORK, 0);
    }

    public long getREST() {
        return sharedPrefs.getLong(REST, 0);
    }

    public boolean isCOUNTDOWN() {
        return sharedPrefs.getBoolean(COUNTDOWN, false);
    }

    public boolean needTenSecStart() {
        return sharedPrefs.getBoolean(TEN_SEC_START, true);
    }

    public boolean needSOUND() {
        return sharedPrefs.getBoolean(SOUND, true);
    }

    public void setIntervalMode(boolean isIntervalMode) {
        sharedPrefs.edit().putBoolean(INTERVAL_MODE, isIntervalMode).apply();
    }

    public void setSwMode(boolean isSWMode) {
        sharedPrefs.edit().putBoolean(SW_MODE, isSWMode).apply();
    }

    public void setFgbMode(boolean isFGBMode) {
        sharedPrefs.edit().putBoolean(FGB_MODE, isFGBMode).apply();
    }

    public void setTabataMode(boolean isTabataMode) {
        sharedPrefs.edit().putBoolean(TABATA_MODE, isTabataMode).apply();
    }

    public void setROUNDS(int rounds) {
        sharedPrefs.edit().putInt(ROUNDS, rounds).apply();
    }

    public void setWORK(long work) {
        sharedPrefs.edit().putLong(WORK, work).apply();
    }

    public void setREST(long rest) {
        sharedPrefs.edit().putLong(REST, rest).apply();
    }

    public void setCOUNTDOWN(boolean isCountdown) {
        sharedPrefs.edit().putBoolean(COUNTDOWN, isCountdown).apply();
    }

    public void setTenSecStart(boolean needTenSecStart) {
        sharedPrefs.edit().putBoolean(TEN_SEC_START, needTenSecStart).apply();
    }

    public void setSOUND(boolean needSound) {
        sharedPrefs.edit().putBoolean(SOUND, needSound).apply();
    }
}
