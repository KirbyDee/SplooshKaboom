package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.model.media.Video;

public class TextContract {

    public interface Presenter {

        void onClickScreen();
    }

    public interface View {

        boolean isTextBoxFinished();

        void playVideo(Video video);

        void removeTextBoxNext();

        void enableScreenClick(boolean enable);

        void forceFinishText();

        void showNextText(int resId);

        void showTextBox(boolean show);
    }
}
