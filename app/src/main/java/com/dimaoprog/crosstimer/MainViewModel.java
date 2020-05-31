package com.dimaoprog.crosstimer;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;

import com.dimaoprog.crosstimer.data.PrefsData;
import com.dimaoprog.crosstimer.models.Timer;

public class MainViewModel extends AndroidViewModel {

    private Timer timer;
    private PrefsData prefsData;

    private ObservableInt tenMin = new ObservableInt();
    private ObservableInt min = new ObservableInt();
    private ObservableInt tenSec = new ObservableInt();
    private ObservableInt sec = new ObservableInt();

    public MainViewModel(@NonNull Application application) {
        super(application);
        prefsData = new PrefsData(application);
        timer = prefsData.getTimerSettings();
    }

    public void clearWorkTime() {
        timer.setWork(0L);
    }

    public void clearRestTime() {
        timer.setRest(0L);
    }

    public void setRounds(int rounds) {
        timer.setRounds(rounds);
    }

    public void setWorkTime() {
        timer.setWork(getTimeFromPickers());
        setPickersToZero();
    }

    public void setRestTime() {
        timer.setRest(getTimeFromPickers());
        setPickersToZero();
    }

    private long getTimeFromPickers() {
        return tenMin.get() * 10 * 60 +
                min.get() * 60 +
                tenSec.get() * 10 +
                sec.get();
    }

    private void setPickersToZero() {
        tenMin.set(0);
        min.set(0);
        tenSec.set(0);
        sec.set(0);
    }

    public Timer getTimer() {
        return timer;
    }

    public void goClick() {
        Log.e("dimon", "goClick: " + timer.toString());
    }

    public ObservableInt getTenMin() {
        return tenMin;
    }

    public void setTenMin(int tenMin) {
        this.tenMin.set(tenMin);
    }

    public ObservableInt getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public ObservableInt getTenSec() {
        return tenSec;
    }

    public void setTenSec(int tenSec) {
        this.tenSec.set(tenSec);
    }

    public ObservableInt getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec.set(sec);
    }
}
