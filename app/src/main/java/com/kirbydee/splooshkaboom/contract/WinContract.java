package com.kirbydee.splooshkaboom.contract;

public class WinContract {

    public interface Presenter extends TextContract.Presenter {
    }

    public interface View extends TextContract.View {

        void backToMenu();
    }
}
