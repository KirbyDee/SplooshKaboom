package com.kirbydee.splooshkaboom.presenter;

import android.util.Log;

import com.kirbydee.splooshkaboom.contract.MenuContract;
import com.kirbydee.splooshkaboom.model.MenuState;

import static com.kirbydee.splooshkaboom.model.MenuState.INIT;
import static com.kirbydee.splooshkaboom.model.MenuState.INTRO;
import static com.kirbydee.splooshkaboom.model.MenuState.MENU;
import static com.kirbydee.splooshkaboom.model.MenuState.START;
import static com.kirbydee.splooshkaboom.model.MenuState.TEXT_START_1;
import static com.kirbydee.splooshkaboom.model.MenuState.TEXT_START_2;
import static com.kirbydee.splooshkaboom.model.MenuState.TEXT_START_3;
import static com.kirbydee.splooshkaboom.model.MenuState.TEXT_START_4;
import static com.kirbydee.splooshkaboom.model.MenuState.TEXT_START_5;
import static com.kirbydee.splooshkaboom.model.MenuState.TEXT_START_6;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_IDLE;
import static com.kirbydee.splooshkaboom.model.media.Video.MENU_TALK;

public class MenuPresenter implements MenuContract.Presenter {

    private static final String TAG = MenuPresenter.class.getName();

    // View
    private final MenuContract.View view;

    // current menu state
    private MenuState menuState;

    public MenuPresenter(MenuContract.View view) {
        this.menuState = INIT;
        this.view = view;
    }

    @Override
    public void onIntro() {
        Log.i(TAG, "onIntro");
        this.menuState = INTRO;
        this.view.showMenuText(true);
        this.view.showMenu(false);
        this.view.enableScreenClick();
        this.view.showNextText(this.menuState.textId);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        this.menuState = TEXT_START_1;
        this.view.showMenuText(true);
        this.view.showMenu(false);
        this.view.enableScreenClick();
        this.view.showNextText(this.menuState.textId);
        this.view.playVideo(MENU_TALK);
    }

    @Override
    public void onClickScreen() {
        Log.i(TAG, "onClickScreen");
        switch (menuState) {
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
        if (this.view.isTextBoxFinished()) {
            this.menuState = MENU;
            this.view.disableScreenClick();
            this.view.showMenuText(false);
            this.view.removeNextText();
            this.view.showMenu(true);
            this.view.playVideo(MENU_IDLE);
        }
        else {
            this.view.forceFinishText();
        }
    }

    private void onTextClick(MenuState menuState) {
        Log.i(TAG, "onTextClick");
        if (this.view.isTextBoxFinished()) {
            this.view.removeNextText();
            this.menuState = menuState;
            if (this.menuState == START) {
                this.view.startGame();
            } else {
                this.view.showNextText(this.menuState.textId);
            }
        }
        else {
            this.view.forceFinishText();
        }
    }
}
