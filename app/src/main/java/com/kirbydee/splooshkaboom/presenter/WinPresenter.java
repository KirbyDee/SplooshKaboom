package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.WinContract;
import com.kirbydee.splooshkaboom.model.textstate.MenuState;
import com.kirbydee.splooshkaboom.utils.Storage;

public class WinPresenter extends TextPresenter<WinContract.View, MenuState> implements WinContract.Presenter {

    private static final String TAG = WinPresenter.class.getName();

    public WinPresenter(WinContract.View view, Storage storage) {
        super(view, storage);
    }

    @Override
    public void onClickScreen() {
        Log.i(TAG, "onClickScreen");
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        this.view.backToMenu();
    }
}
