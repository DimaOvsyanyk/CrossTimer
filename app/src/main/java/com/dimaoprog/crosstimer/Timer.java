package com.dimaoprog.crosstimer;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dimaoprog.crosstimer.databinding.ActivityTimerBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.dimaoprog.crosstimer.Constants.*;

public class Timer extends AppCompatActivity {

    private TimerViewModel tViewModel;
    private Disposable timerDisp;
    private SoundPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tViewModel = ViewModelProviders.of(this).get(TimerViewModel.class);
        ActivityTimerBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_timer);
        binding.setTimerModel(tViewModel);

        tViewModel.setInput(getIntent().getIntExtra(ROUNDS_EXTRA, 0),
                getIntent().getLongExtra(WORK_TIME_EXTRA, 0),
                getIntent().getLongExtra(REST_TIME_EXTRA, 0),
                getIntent().getBooleanExtra(INT_MODE_EXTRA, false),
                getIntent().getBooleanExtra(SW_MODE_EXTRA, false),
                getIntent().getBooleanExtra(FGB_MODE_EXTRA, false),
                getIntent().getBooleanExtra(TBT_MODE_EXTRA, false),
                getIntent().getBooleanExtra(COUNT_DOWN_EXTRA, false),
                getIntent().getBooleanExtra(TEN_SEC_EXTRA, false),
                getIntent().getBooleanExtra(SOUND_EXTRA, false));
        binding.btnStart.setOnClickListener(__ -> startTimer());
        binding.btnReset.setOnClickListener(__ -> resetTimer());
        player = new SoundPlayer(getApplicationContext());
    }

    private void playSound() {
        if (tViewModel.isNeedTenSec()) {
            player.playSound();
        }
    }

    private void playSoundStartFinish() {
        if (tViewModel.isNeedTenSec()) {
            player.playSoundStartFinish();
        }
    }

    private void resetTimer() {
        timerDisp.dispose();
        tViewModel.resetAction();
    }

    private void startTimer() {
        if (tViewModel.getRounds().get() > 0) {
            if (tViewModel.isNeedTenSec()) {
                timerTenSec();
            } else {
                playSoundStartFinish();
                timerWork();
            }
        }
    }

    private void timerWork() {
        Log.d("timerCounting", "round - " + tViewModel.getRounds().get());
        if (tViewModel.getRounds().get() > 0) {
            playSound();
            countingWork();
        } else {
            playSoundStartFinish();
        }
    }

    private void timerTenSec() {
        timerDisp = Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(10)
                .map(l -> 10 - l - 1)
                .subscribe(onNext -> {
                            if (onNext < 4) {
                                playSound();
                            }
                            tViewModel.setNewTime(onNext);
                        },
                        onError -> Log.d("timerCounting", onError.getMessage()),
                        () -> {
                            playSoundStartFinish();
                            timerWork();
                        });
    }

    private void countingWork() {
        tViewModel.setWorkTicking(true);
        timerDisp = Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(tViewModel.getWorkTime())
                .map(l -> {
                    if (tViewModel.isCountDown()) {
                        return tViewModel.getWorkTime() - l - 1;
                    } else {
                        return l + 1;
                    }
                })
                .subscribe(onNext -> tViewModel.setNewTime(onNext),
                        onError -> Log.d("timerCounting", onError.getMessage()),
                        () -> {
                            if (tViewModel.getRestTime() == 0) {
                                tViewModel.setRounds(tViewModel.getRounds().get() - 1);
                                timerWork();
                            } else {
                                playSound();
                                countingRest();
                            }
                        });
    }

    private void countingRest() {
        tViewModel.setRestTicking(true);
        timerDisp = Observable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .take(tViewModel.getRestTime())
                .map(l -> {
                    if (tViewModel.isCountDown()) {
                        return tViewModel.getRestTime() - l - 1;
                    } else {
                        return l +1;
                    }
                })
                .subscribe(onNext -> tViewModel.setNewTime(onNext),
                        onError -> Log.d("timerCounting", onError.getMessage()),
                        () -> {
                            tViewModel.setRounds(tViewModel.getRounds().get() - 1);
                            timerWork();
                        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerDisp.dispose();
        player.release();
    }
}
