package com.dimaoprog.crosstimer.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class Timer extends BaseObservable {

    private boolean isInterval;
    private boolean isSW;
    private boolean isFGB;
    private boolean isTabata;

    private int rounds;
    private long work;
    private long rest;

    private boolean isCountdown;
    private boolean needTenSecStart;
    private boolean needSound;

    public Timer(boolean isInterval, boolean isSW, boolean isFGB, boolean isTabata,
                 int rounds, long work, long rest,
                 boolean isCountdown, boolean needTenSecStart, boolean needSound) {
        this.isInterval = isInterval;
        this.isSW = isSW;
        this.isFGB = isFGB;
        this.isTabata = isTabata;
        this.rounds = rounds;
        this.work = work;
        this.rest = rest;
        this.isCountdown = isCountdown;
        this.needTenSecStart = needTenSecStart;
        this.needSound = needSound;
    }

    @Bindable
    public boolean isInterval() {
        return isInterval;
    }

    public void setInterval(boolean interval) {
        isInterval = interval;
        if (interval) {
            setSW(false);
            setTabata(false);
            setFGB(false);
        }
        notifyPropertyChanged(BR.interval);
    }

    @Bindable
    public boolean isSW() {
        return isSW;
    }

    public void setSW(boolean SW) {
        isSW = SW;
        if (SW) {
            setInterval(false);
            setFGB(false);
            setTabata(false);
        }
        if (SW) {
            setRounds(1);
            setRest(0L);
        }
        notifyPropertyChanged(BR.sW);
    }

    @Bindable
    public boolean isFGB() {
        return isFGB;
    }

    public void setFGB(boolean FGB) {
        isFGB = FGB;
        if (FGB) {
            setInterval(false);
            setTabata(false);
            setSW(false);
        }
        if (FGB) {
            setRounds(3);
            setWork(300L);
            setRest(60L);
        }
        notifyPropertyChanged(BR.fGB);
    }

    @Bindable
    public boolean isTabata() {
        return isTabata;
    }

    public void setTabata(boolean tabata) {
        isTabata = tabata;
        if (tabata) {
            setInterval(false);
            setFGB(false);
            setSW(false);
        }
        if (tabata) {
            setRounds(8);
            setWork(20L);
            setRest(10L);
        }
        notifyPropertyChanged(BR.tabata);
    }

    @Bindable
    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
        notifyPropertyChanged(BR.rounds);
    }

    @Bindable
    public long getWork() {
        return work;
    }

    public void setWork(long work) {
        this.work = work;
        notifyPropertyChanged(BR.work);
    }

    @Bindable
    public long getRest() {
        return rest;
    }

    public void setRest(long rest) {
        this.rest = rest;
        notifyPropertyChanged(BR.rest);
    }

    @Bindable
    public boolean isCountdown() {
        return isCountdown;
    }

    public void setCountdown(boolean countdown) {
        isCountdown = countdown;
        notifyPropertyChanged(BR.countdown);
    }

    @Bindable
    public boolean isNeedTenSecStart() {
        return needTenSecStart;
    }

    public void setNeedTenSecStart(boolean needTenSecStart) {
        this.needTenSecStart = needTenSecStart;
        notifyPropertyChanged(BR.needTenSecStart);
    }

    @Bindable
    public boolean isNeedSound() {
        return needSound;
    }

    public void setNeedSound(boolean needSound) {
        this.needSound = needSound;
        notifyPropertyChanged(BR.needSound);
    }

    public boolean verifyTimerSettings() {
        return (isInterval || isSW || isFGB || isTabata) &&
                rounds > 0 && work > 2;
    }

    @Override
    public String toString() {
        return "Timer{" +
                "isInterval=" + isInterval +
                ", isSW=" + isSW +
                ", isFGB=" + isFGB +
                ", isTabata=" + isTabata +
                ", rounds=" + rounds +
                ", work=" + work +
                ", rest=" + rest +
                ", isCountdown=" + isCountdown +
                ", needTenSecStart=" + needTenSecStart +
                ", needSound=" + needSound +
                '}';
    }
}
