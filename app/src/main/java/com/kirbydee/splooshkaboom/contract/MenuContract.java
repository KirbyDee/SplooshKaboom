package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.model.media.Video;

public class MenuContract {

    public interface Presenter {

        void onIntro();

        void onClickScreen();

        void onStart();
    }

    public interface View {

        boolean isTextBoxFinished();

        void playVideo(Video video);

        void removeNextText();

        void disableScreenClick();

        void enableScreenClick();

        void startGame();

        void forceFinishText();

        void showNextText(int resId);

        void showMenu(boolean show);

        void showMenuText(boolean show);
    }
}
