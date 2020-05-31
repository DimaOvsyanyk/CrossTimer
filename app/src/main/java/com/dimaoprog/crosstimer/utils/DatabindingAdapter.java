package com.dimaoprog.crosstimer.utils;

import androidx.databinding.BindingAdapter;

import com.shawnlin.numberpicker.NumberPicker;

public class DatabindingAdapter {

    @BindingAdapter("pickerValue")
    public static void setPickerValue(NumberPicker numberPicker, int quantity) {
        numberPicker.setValue(quantity);
    }
}
