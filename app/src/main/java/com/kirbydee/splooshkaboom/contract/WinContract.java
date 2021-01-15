package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.model.counter.Rupee;

public class WinContract {

    public interface Presenter extends TextContract.Presenter {

        void onIntro();

        void onChestOpenVideoFinished();

        void onChestOpenSoundFinished();

        Rupee getWonRupee();
    }

    public interface View extends TextContract.View {

        void showRupee(Rupee rupee);

        void backToMenu();
    }
}
