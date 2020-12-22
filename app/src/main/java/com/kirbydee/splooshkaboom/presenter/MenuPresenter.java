package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.MenuContract;
import com.kirbydee.splooshkaboom.model.textstate.MenuState;
import com.kirbydee.splooshkaboom.utils.Storage;

import static com.kirbydee.splooshkaboom.model.media.Video.MENU_IDLE;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_TALK;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.INTRO;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.MENU;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.START;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.TEXT_START_1;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.TEXT_START_2;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.TEXT_START_3;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.TEXT_START_4;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.TEXT_START_5;
import static com.kirbydee.splooshkaboom.model.textstate.MenuState.TEXT_START_6;

public class MenuPresenter extends TextPresenter<MenuContract.View, MenuState> implements MenuContract.Presenter {

    private static final String TAG = MenuPresenter.class.getName();

    public MenuPresenter(MenuContract.View view, Storage storage) {
        super(view, storage);
    }

    @Override
    protected void showText() {
        Log.i(TAG, "showText");
        this.view.showMenu(false);
        super.showText();
    }

    private void showMenu() {
        Log.i(TAG, "showMenu");
        this.view.enableScreenClick(false);
        this.view.showTextBox(false);
        this.view.showMenu();
    }

    @Override
    public void onIntro() {
        Log.i(TAG, "onIntro");

        // set INTRO state
        setState(INTRO);

        // show INTRO text
        showText();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");

        // set START state
        setState(TEXT_START_1);

        // show START text
        showText();

        // play TALK video
        this.view.playVideo(MENU_TALK);
    }

    @Override
    public void onClickScreen() {
        Log.i(TAG, "onClickScreen");
        if (getState() == null) {
            return;
        }
        switch (getState()) {
            case INTRO:
                onIntroClick();
                break;
            case TEXT_START_1:
                onTextClick(TEXT_START_2);
                break;
            case TEXT_START_2:
                onTextClick(TEXT_START_3);
                break;
            case TEXT_START_3:
                onTextClick(TEXT_START_4);
                break;
            case TEXT_START_4:
                onTextClick(TEXT_START_5);
                break;
            case TEXT_START_5:
                onTextClick(TEXT_START_6);
                break;
            case TEXT_START_6:
                onTextClick(START);
                break;
            default:
                // do nothing
                break;
        }
    }

    private void onIntroClick() {
        Log.i(TAG, "onIntroClick");
        onClickForceTextToFinish(() -> {
            setState(MENU);
            showMenu();
            this.view.playVideo(MENU_IDLE);
        });
    }

    private void onTextClick(MenuState menuState) {
        Log.i(TAG, "onTextClick");
        onClickForceTextToFinish(() -> {
            setState(menuState);
            if (getState() == START) {
                this.view.startGame();
            } else {
                showText();
            }
        });
    }
}
