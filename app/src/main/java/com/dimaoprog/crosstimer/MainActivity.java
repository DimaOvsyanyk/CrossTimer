package com.dimaoprog.crosstimer;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dimaoprog.crosstimer.databinding.ActivityMainBinding;

import static com.dimaoprog.crosstimer.Constants.*;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainModel(mViewModel);
        mViewModel.setIntMode(true);

        binding.btnAddWorkTime.setOnClickListener(__ -> setWorkTime());
        binding.btnAddRestTime.setOnClickListener(__ -> setRestTime());

        binding.btnDeleteWorkTime.setOnClickListener(__ -> mViewModel.clearWorkTime());
        binding.btnDeleteRestTime.setOnClickListener(__ -> mViewModel.clearRestTime());

        binding.toggleInt.setOnCheckedChangeListener((__, isChecked) -> mViewModel.setIntMode(isChecked));
        binding.toggleSw.setOnCheckedChangeListener((__, isChecked) -> swModePicked(isChecked));
        binding.toggleTbt.setOnCheckedChangeListener((__, isChecked) -> tbtModePicked(isChecked));
        binding.toggleFgb.setOnCheckedChangeListener((__, isChecked) -> fgbModePicked(isChecked));

        binding.pickerRounds.setOnValueChangedListener((picker, oldVal, newVal) -> mViewModel.setRounds(newVal));
    }

    private void swModePicked(boolean isPicked) {
        if (isPicked) {
            mViewModel.setRounds(1);
            mViewModel.setRestTime(0L);
            binding.pickerRounds.setValue(mViewModel.getRounds());
        }
        mViewModel.setSwMode(isPicked);
    }

    private void tbtModePicked(boolean isPicked) {
        if (isPicked) {
            mViewModel.setRounds(8);
            mViewModel.setWorkTime(20L);
            mViewModel.setRestTime(10L);
            binding.pickerRounds.setValue(mViewModel.getRounds());
        }
        mViewModel.setTbtMode(isPicked);
    }

    private void fgbModePicked(boolean isPicked) {
        if (isPicked) {
            mViewModel.setRounds(3);
            mViewModel.setWorkTime(300L);
            mViewModel.setRestTime(60L);
            binding.pickerRounds.setValue(mViewModel.getRounds());
        }
        mViewModel.setFgbMode(isPicked);
    }

    private void setWorkTime() {
        if (getTimeFromPickers() > 0) {
            mViewModel.setWorkTime(getTimeFromPickers());
            setPickersToZero();
        }
    }

    private void setRestTime() {
        if (getTimeFromPickers() > 0) {
            mViewModel.setRestTime(getTimeFromPickers());
            setPickersToZero();
        }
    }

    private long getTimeFromPickers() {
        return binding.pickerTenMin.getValue() * 10 * 60 +
                binding.pickerMin.getValue() * 60 +
                binding.pickerTenSec.getValue() * 10 +
                binding.pickerSec.getValue();
    }

    private void setPickersToZero() {
        binding.pickerTenMin.setValue(0);
        binding.pickerMin.setValue(0);
        binding.pickerTenSec.setValue(0);
        binding.pickerSec.setValue(0);
    }

    public void onGoClick(View view) {
        if (mViewModel.getWorkTime().get() < 1) {
            Toast.makeText(this, "Bad settings", Toast.LENGTH_SHORT).show();
        } else {
            Intent startTimer = new Intent(MainActivity.this, Timer.class);
            startTimer.putExtra(INT_MODE_EXTRA, mViewModel.getIntMode().get());
            startTimer.putExtra(SW_MODE_EXTRA, mViewModel.getSwMode().get());
            startTimer.putExtra(FGB_MODE_EXTRA, mViewModel.getFgbMode().get());
            startTimer.putExtra(TBT_MODE_EXTRA, mViewModel.getTbtMode().get());
            startTimer.putExtra(COUNT_DOWN_EXTRA, binding.toggleUpDown.isChecked());
            startTimer.putExtra(TEN_SEC_EXTRA, !binding.toggleTenSec.isChecked());
            startTimer.putExtra(SOUND_EXTRA, !binding.toggleSound.isChecked());
            startTimer.putExtra(ROUNDS_EXTRA, mViewModel.getRounds());
            startTimer.putExtra(WORK_TIME_EXTRA, mViewModel.getWorkTime().get());
            startTimer.putExtra(REST_TIME_EXTRA, mViewModel.getRestTime().get());

            startActivity(startTimer);
        }
    }

    public void showInfoDialog(View view) {
        String infoTitle = "";
        String infoText = "";
        switch (view.getId()) {
            case R.id.btn_info_int:
                infoTitle = getString(R.string.intervals);
                infoText = getString(R.string.infoIntervals);
                break;
            case R.id.btn_info_sw:
                infoTitle = getString(R.string.stopwatch);
                infoText = getString(R.string.infoStopWatch);
                break;
            case R.id.btn_info_fgb:
                infoTitle = getString(R.string.fightgonebad);
                infoText = getString(R.string.infoFGB);
                break;
            case R.id.btn_info_tbt:
                infoTitle = getString(R.string.tabata);
                infoText = getString(R.string.infoTBT);
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(infoTitle)
                .setMessage(infoText)
                .setCancelable(false)
                .setNegativeButton("OK", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}