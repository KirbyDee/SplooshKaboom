package com.kirbydee.splooshkaboom.contract;

public class MenuContract {

    public interface Presenter extends TextContract.Presenter {

        void onIntro();

        void onStart();
    }

    public interface View extends TextContract.View {

        void startGame();

        default void showMenu() {
            showMenu(true);
        }

        void showMenu(boolean show);
    }
}
