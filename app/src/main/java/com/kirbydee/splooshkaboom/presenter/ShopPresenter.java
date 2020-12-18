package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.ShopContract;

public class ShopPresenter extends TextPresenter<ShopContract.View> implements ShopContract.Presenter {

    private static final String TAG = ShopPresenter.class.getName();

    public ShopPresenter(ShopContract.View view) {
        super(view);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
    }

    @Override
    public void onClickScreen() {
        Log.i(TAG, "onClickScreen");
    }
}
