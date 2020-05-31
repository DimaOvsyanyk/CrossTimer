package com.dimaoprog.crosstimer;

import android.app.AlertDialog;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dimaoprog.crosstimer.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.shawnlin.numberpicker.NumberPicker;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel mViewModel;
    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    public final static int FROM_TIMER = 27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainModel(mViewModel);
        MobileAds.initialize(this, getString(R.string.ad_app_id));
        setupAds();
        setUpPickersListeners();
    }

    private void setUpPickersListeners() {
        binding.pickerRounds.setOnValueChangedListener((picker, oldVal, newVal) -> {mViewModel.setRounds(newVal);});
        binding.pickerTenMin.setOnValueChangedListener((picker, oldVal, newVal) -> {mViewModel.setTenMin(newVal);});
        binding.pickerMin.setOnValueChangedListener((picker, oldVal, newVal) -> {mViewModel.setMin(newVal);});
        binding.pickerTenSec.setOnValueChangedListener((picker, oldVal, newVal) -> {mViewModel.setTenSec(newVal);});
        binding.pickerSec.setOnValueChangedListener((picker, oldVal, newVal) -> {mViewModel.setSec(newVal);});
    }

    private void setupAds() {
        adRequest = new AdRequest.Builder()
                .addTestDevice("B1515A74A9515CC704AC807B68B96082")
                .build();
        binding.adView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.timer_ad_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }
        });
    }

//    public void onStartTimerClick(View view) {
//        if (mViewModel.getWorkTime().get() < 1) {
//            Toast.makeText(this, "Bad settings", Toast.LENGTH_SHORT).show();
//        } else {
//            Intent startTimer = new Intent(MainActivity.this, TimerActivity.class);

//            startActivityForResult(startTimer, FROM_TIMER);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_TIMER) {
            interstitialAd.loadAd(adRequest);
        }
    }

    public void onInfoDialogClick(View view) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(infoTitle)
                .setMessage(infoText)
                .setCancelable(false)
                .setNegativeButton(getText(R.string.ok), (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}