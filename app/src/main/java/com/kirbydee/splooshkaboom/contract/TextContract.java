package com.kirbydee.splooshkaboom.contract;

import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.media.Sound;
import com.kirbydee.splooshkaboom.model.media.Video;

public class TextContract {

    public interface Presenter {

        void onClickScreen();

        void onTextFinished();

        void onBackPressed();
    }

    public interface View {

        boolean isTextBoxFinished();

        void playSound(Sound sound);

        void playVideo(Video video);

        void removeTextBoxNext();

        void enableScreenClick(boolean enable);

        void forceFinishText();

        void showNextText(int resId);

        default void showTextBox() {
            showTextBox(true);
        }

        void showTextBox(boolean show);

        default void showTextBoxNext() {
            showTextBoxNext(true);
        }

        void showTextBoxNext(boolean show);

        void updateRupees(Rupees rupees);

        void finish();
    }
}
