package com.dimaoprog.crosstimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.dimaoprog.crosstimer.databinding.ActivityTimerBinding;

import static com.dimaoprog.crosstimer.utils.Constants.*;

public class TimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimerViewModel tViewModel = ViewModelProviders.of(this).get(TimerViewModel.class);
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
}
