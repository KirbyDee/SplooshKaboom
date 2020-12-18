package com.kirbydee.splooshkaboom.contract;

public class MenuContract {

    public interface Presenter extends TextContract.Presenter {

        void onIntro();

        void onStart();
    }

    public interface View extends TextContract.View {

        void startGame();

        void showMenu(boolean show);
    }
}
