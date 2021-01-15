package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.WinContract;
import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.counter.Rupee;
import com.kirbydee.splooshkaboom.model.textstate.WinState;
import com.kirbydee.splooshkaboom.utils.Storage;

import static com.kirbydee.splooshkaboom.model.counter.Rupee.BLUE;
import static com.kirbydee.splooshkaboom.model.counter.Rupee.GREEN;
import static com.kirbydee.splooshkaboom.model.counter.Rupee.RED;
import static com.kirbydee.splooshkaboom.model.counter.Rupee.YELLOW;
import static com.kirbydee.splooshkaboom.model.textstate.WinState.INTRO;
import static com.kirbydee.splooshkaboom.model.textstate.WinState.RUPEE_COUNT;
import static com.kirbydee.splooshkaboom.model.textstate.WinState.RUPEE_SHOW;
import static com.kirbydee.splooshkaboom.model.textstate.WinState.RUPEE_TEXT;
import static com.kirbydee.splooshkaboom.utils.VoidFunction.nothing;

public class WinPresenter extends TextPresenter<WinContract.View, WinState> implements WinContract.Presenter {

    private static final String TAG = WinPresenter.class.getName();

    private final Rupee wonRupee;

    public WinPresenter(WinContract.View view, Storage storage, Counter counter) {
        super(view, storage);

        if (counter.get() <= 9) {
            this.wonRupee = YELLOW;
        }
        else if (counter.get() <= 15) {
            this.wonRupee = RED;
        }
        else if (counter.get() <= 20) {
            this.wonRupee = BLUE;
        }
        else {
            this.wonRupee = GREEN;
        }
    }

    @Override
    public void onIntro() {
        Log.i(TAG, "onIntro");
        setState(INTRO);
        this.view.showTextBoxText(false);
        this.view.showTextBox(false);
        this.view.enableScreenClick(false);
    }

    @Override
    public void onClickScreen() {
        Log.i(TAG, "onClickScreen");
        switch(getState()){
            case RUPEE_TEXT:
                onRupeeTextClick();
                break;
            case RUPEE_COUNT:
                this.view.backToMenu();
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onTextFinished() {
        super.onTextFinished();
        Log.i(TAG, "onTextFinished");
        setState(RUPEE_COUNT);
        increaseRupees(this.wonRupee.amount);
    }

    private void onRupeeTextClick() {
        Log.i(TAG, "onRupeeClick");
        onClickForceTextToFinish(nothing());
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed");
        if (getState() == RUPEE_COUNT) {
            this.view.backToMenu();
        }
    }

    @Override
    public void onChestOpenVideoFinished() {
        Log.i(TAG, "onChestOpenVideoFinished");
        setState(RUPEE_SHOW);
        this.view.showRupee(this.wonRupee);
    }

    @Override
    public void onChestOpenSoundFinished() {
        Log.i(TAG, "onChestOpenSoundFinished");
        setState(RUPEE_TEXT);
        showText();
    }

    @Override
    public Rupee getWonRupee() {
        Log.i(TAG, "getWonRupee");
        return this.wonRupee;
    }
}
