package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.TextContract;
import com.kirbydee.splooshkaboom.utils.VoidFunction;

public abstract class TextPresenter<V extends TextContract.View> implements TextContract.Presenter {

    private static final String TAG = TextPresenter.class.getName();

    // View
    protected final V view;

    public TextPresenter(V view) {
        this.view = view;
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
}
