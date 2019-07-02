package com.dimaoprog.crosstimer;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TimerViewModel extends AndroidViewModel {

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

    private CompositeDisposable timerDisp = new CompositeDisposable();
    private SoundPlayer player;

    private boolean firstSecond;
    private boolean firstRound;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        player = new SoundPlayer(application);
    }

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

    private void setNewTime(long time) {
        setTenMin((time / 60) / 10);
        setMin((time / 60) % 10);
        setTenSec((time % 60) / 10);
        setSec((time % 60) % 10);
    }

    private void resetAction() {
        rounds.set(baseRounds);
        workTicking.set(false);
        restTicking.set(false);
        setNewTime(0L);
    }

    private void playSound() {
        if (needSound) {
            player.playSound();
        }
    }

    private void playSoundStartFinish() {
        if (needSound) {
            player.playSoundStartFinish();
        }
    }

    public void resetTimer() {
        timerDisp.clear();
        resetAction();
    }

    public void startTimer() {
        firstRound = true;
        if (getRounds().get() > 0) {
            if (needTenSec) {
                timerTenSec();
            } else {
                playSoundStartFinish();
                timerWork();
            }
        }
    }

    private void timerWork() {
        if (getRounds().get() > 0) {
            countingWork();
        } else {
            playSoundStartFinish();
        }
    }

    private void timerTenSec() {
        timerDisp.add(Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(10)
                .map(time -> 10 - time)
                .subscribe(time -> {
                            if (time < 4) {
                                playSound();
                            }
                            setNewTime(time);
                        },
                        onError -> Log.d("timerCounting", onError.getMessage()),
                        () -> {
                            playSoundStartFinish();
                            timerWork();
                        }));
    }

    private void countingWork() {
        firstSecond = true;
        timerDisp.add(Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(workTime)
                .map(time -> {
                    if (countDown) {
                        return workTime - time - 1;
                    } else {
                        return time;
                    }
                })
                .subscribe(time -> {
                            if (firstSecond) {
                                if (!firstRound) {
                                    playSound();
                                    setRounds(getRounds().get() - 1);
                                }
                                setWorkTicking(true);
                                firstSecond = false;
                            }
                            setNewTime(time);
                        },
                        onError -> Log.d("timerCounting", onError.getMessage()),
                        () -> {
                            firstRound = false;
                            if (restTime == 0 | getRounds().get() == 1) {
                                setRounds(getRounds().get() - 1);
                                timerWork();
                            } else {
                                countingRest();
                            }
                        }));
    }

    private void countingRest() {
        firstSecond = true;
        timerDisp.add(Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(restTime)
                .map(time -> {
                    if (countDown) {
                        return restTime - time - 1;
                    } else {
                        return time;
                    }
                })
                .subscribe(time -> {
                            if (firstSecond) {
                                playSound();
                                setRestTicking(true);
                                firstSecond = false;
                            }
                            setNewTime(time);
                        },
                        onError -> Log.d("timerCounting", onError.getMessage()),
                        this::timerWork));
    }

    public void onActivityDestroy() {
        timerDisp.clear();
        player.release();
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

    //   Default Setters and Getters
    public ObservableInt getRounds() {
        return rounds;
    }

    private void setRounds(int rounds) {
        this.rounds.set(rounds);
    }

    public ObservableLong getTenMin() {
        return tenMin;
    }

    private void setTenMin(long tenMin) {
        this.tenMin.set(tenMin);
    }

    public ObservableLong getMin() {
        return min;
    }

    private void setMin(long min) {
        this.min.set(min);
    }

    public ObservableLong getTenSec() {
        return tenSec;
    }

    private void setTenSec(long tenSec) {
        this.tenSec.set(tenSec);
    }

    public ObservableLong getSec() {
        return sec;
    }

    private void setSec(long sec) {
        this.sec.set(sec);
    }
}
