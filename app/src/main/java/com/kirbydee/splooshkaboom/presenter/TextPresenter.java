package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.TextContract;
import com.kirbydee.splooshkaboom.model.counter.Rupees;
import com.kirbydee.splooshkaboom.model.textstate.TextState;
import com.kirbydee.splooshkaboom.utils.Storage;
import com.kirbydee.splooshkaboom.utils.VoidFunction;

import java.util.function.Consumer;

import static com.kirbydee.splooshkaboom.model.media.Sound.RUPEE;

public abstract class TextPresenter<V extends TextContract.View, S extends TextState> implements TextContract.Presenter {

    private static final String TAG = TextPresenter.class.getName();

    // View
    protected final V view;

    // Storage
    protected Storage storage;

    // Text State
    private S state;

    public TextPresenter(V view, Storage storage) {
        this.view = view;
        this.storage = storage;
    }

    protected void setState(S state) {
        this.state = state;
    }

    protected S getState() {
        return this.state;
    }

    protected void showText() {
        Log.i(TAG, "showText");
        this.view.showTextBox(true);
        this.view.enableScreenClick(true);
        this.view.showNextText(state.getTextId());
    }

    @Override
    public void onTextFinished() {
        Log.i(TAG, "onTextFinished");
        this.view.showTextBoxNext();
    }

    protected void onClickForceTextToFinish(VoidFunction function) {
        Log.i(TAG, "onClickForceTextToFinish");
        if (this.view.isTextBoxFinished()) {
            this.view.removeTextBoxNext();
            function.run();
        }
        else {
            this.view.forceFinishText();
        }
    }

    protected void increaseRupees(Rupees rupees) {
        Log.i(TAG, "increaseRupees (" + rupees + ")");
        updateRupees(r -> r.increase(rupees));
    }

    protected void decreaseRupees(Rupees rupees) {
        Log.i(TAG, "decreaseRupees (" + rupees + ")");
        updateRupees(r -> r.decrease(rupees));
    }

    private void updateRupees(Consumer<Rupees> rupeesConsumer) {
        Log.i(TAG, "updateRupees");
        // change rupee model
        Rupees currentRupees = this.storage.getRupees();
        rupeesConsumer.accept(currentRupees);
        this.storage.storeRupees(currentRupees);

        // fire to change rupee view
        this.view.updateRupees(currentRupees);
        this.view.playSound(RUPEE);
    }
}
