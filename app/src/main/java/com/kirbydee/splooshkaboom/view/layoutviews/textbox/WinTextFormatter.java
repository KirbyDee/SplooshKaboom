package com.kirbydee.splooshkaboom.view.layoutviews.textbox;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.WinContract;

public class WinTextFormatter implements TextFormatter {

    private static final String TAG = WinTextFormatter.class.getName();

    private final WinContract.Presenter presenter;

    public WinTextFormatter(WinContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public String format(String text) {
        Log.i(TAG, "format (" + text + ")");
        return text.replace("[rupees]", "" + presenter.getWonRupee().amount.get());
    }
}
