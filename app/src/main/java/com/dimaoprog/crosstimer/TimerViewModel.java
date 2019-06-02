package com.dimaoprog.crosstimer;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;

public class TimerViewModel extends ViewModel {

    private int baseRounds;
    private ObservableInt rounds = new ObservableInt();
    private long workTime;
    private long restTime;
    private boolean intMode;
    private boolean swMode;
    private boolean fgbMode;
    private boolean tbtMode;
    private boolean countDown;
    private boolean needTenSec;
    private boolean needSound;
    private ObservableLong tenMin = new ObservableLong();
    private ObservableLong min = new ObservableLong();
    private ObservableLong tenSec = new ObservableLong();
    private ObservableLong sec = new ObservableLong();

    private ObservableBoolean workTicking = new ObservableBoolean();
    private ObservableBoolean restTicking = new ObservableBoolean();

    public void setInput(int baseRounds, long workTime, long restTime, boolean intMode, boolean swMode, boolean fgbMode,
                         boolean tbtMode, boolean countDown, boolean needTenSec, boolean needSound) {
        this.baseRounds = baseRounds;
        this.rounds.set(baseRounds);
        this.workTime = workTime;
        this.restTime = restTime;
        this.intMode = intMode;
        this.swMode = swMode;
        this.fgbMode = fgbMode;
        this.tbtMode = tbtMode;
        this.countDown = countDown;
        this.needTenSec = needTenSec;
        this.needSound = needSound;
    }

    public void setNewTime(long time) {
        setTenMin((time / 60) / 10);
        setMin((time / 60) % 10);
        setTenSec((time % 60) / 10);
        setSec((time % 60) % 10);
    }

    public void resetAction() {
        rounds.set(baseRounds);
        workTicking.set(false);
        restTicking.set(false);
        setNewTime(0L);
    }

    public ObservableBoolean getWorkTicking() {
        return workTicking;
    }

    public void setWorkTicking(boolean workTicking) {
        this.workTicking.set(workTicking);
        restTicking.set(!workTicking);
    }

    public ObservableBoolean getRestTicking() {
        return restTicking;
    }

    public void setRestTicking(boolean restTicking) {
        this.restTicking.set(restTicking);
        workTicking.set(!restTicking);
    }

//    Setters and Getters
    public ObservableInt getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds.set(rounds);
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    public long getRestTime() {
        return restTime;
    }

    public void setRestTime(long restTime) {
        this.restTime = restTime;
    }

    public boolean isIntMode() {
        return intMode;
    }

    public void setIntMode(boolean intMode) {
        this.intMode = intMode;
    }

    public boolean isSwMode() {
        return swMode;
    }

    public void setSwMode(boolean swMode) {
        this.swMode = swMode;
    }

    public boolean isFgbMode() {
        return fgbMode;
    }

    public void setFgbMode(boolean fgbMode) {
        this.fgbMode = fgbMode;
    }

    public boolean isTbtMode() {
        return tbtMode;
    }

    public void setTbtMode(boolean tbtMode) {
        this.tbtMode = tbtMode;
    }

    public boolean isCountDown() {
        return countDown;
    }

    public void setCountDown(boolean countDown) {
        this.countDown = countDown;
    }

    public boolean isNeedTenSec() {
        return needTenSec;
    }

    public void setNeedTenSec(boolean needTenSec) {
        this.needTenSec = needTenSec;
    }

    public boolean isNeedSound() {
        return needSound;
    }

    public void setNeedSound(boolean needSound) {
        this.needSound = needSound;
    }

    public ObservableLong getTenMin() {
        return tenMin;
    }

    public void setTenMin(long tenMin) {
        this.tenMin.set(tenMin);
    }

    public ObservableLong getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min.set(min);
    }

    public ObservableLong getTenSec() {
        return tenSec;
    }

    public void setTenSec(long tenSec) {
        this.tenSec.set(tenSec);
    }

    public ObservableLong getSec() {
        return sec;
    }

    public void setSec(long sec) {
        this.sec.set(sec);
    }
}
