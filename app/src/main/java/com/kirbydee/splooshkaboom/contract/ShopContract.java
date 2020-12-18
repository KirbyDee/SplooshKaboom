package com.kirbydee.splooshkaboom.contract;

public class ShopContract {

    public interface Presenter extends TextContract.Presenter {

        void onStart();
    }

    public interface View extends TextContract.View {}
}
