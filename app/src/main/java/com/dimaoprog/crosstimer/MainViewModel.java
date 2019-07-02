package com.dimaoprog.crosstimer;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableLong;

public class MainViewModel extends ViewModel {

    private ObservableLong workTime = new ObservableLong();
    private ObservableLong restTime = new ObservableLong();
    private int rounds = 1;
    private ObservableBoolean intMode = new ObservableBoolean();
    private ObservableBoolean swMode = new ObservableBoolean();
    private ObservableBoolean fgbMode = new ObservableBoolean();
    private ObservableBoolean tbtMode = new ObservableBoolean();

    public ObservableLong getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime.set(workTime);
    }

    public ObservableLong getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime.set(restTime);
    }

    public ObservableBoolean getIntMode() {
        return intMode;
    }

    public void setIntMode(boolean intMode) {
        this.intMode.set(intMode);
        if (intMode) {
            setSwMode(false);
            setTbtMode(false);
            setFgbMode(false);
        }
    }

    public ObservableBoolean getSwMode() {
        return swMode;
    }

    public void setSwMode(boolean swMode) {
        this.swMode.set(swMode);
        if (swMode) {
            setIntMode(false);
            setFgbMode(false);
            setTbtMode(false);
        }
    }

    public ObservableBoolean getFgbMode() {
        return fgbMode;
    }

    public void setFgbMode(boolean fgbMode) {
        this.fgbMode.set(fgbMode);
        if (fgbMode) {
            setIntMode(false);
            setTbtMode(false);
            setSwMode(false);
        }
    }

    public ObservableBoolean getTbtMode() {
        return tbtMode;
    }

    public void setTbtMode(boolean tbtMode) {
        this.tbtMode.set(tbtMode);
        if (tbtMode) {
            setIntMode(false);
            setFgbMode(false);
            setSwMode(false);
        }
    }

    public void clearWorkTime() {
        setWorkTime(0L);
    }

    public void clearRestTime() {
        setRestTime(0L);
    }


    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }


}
