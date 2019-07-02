package com.dimaoprog.crosstimer;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dimaoprog.crosstimer.databinding.ActivityTimerBinding;

import static com.dimaoprog.crosstimer.Constants.*;

public class Timer extends AppCompatActivity {

    private TimerViewModel tViewModel;

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tViewModel.onActivityDestroy();
    }
}
