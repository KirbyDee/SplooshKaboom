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

public class MenuPresenter extends TextPresenter<MenuContract.View> implements MenuContract.Presenter {

    private static final String TAG = MenuPresenter.class.getName();

    // current menu state
    private MenuState menuState;

    public MenuPresenter(MenuContract.View view) {
        super(view);
        this.menuState = INIT;
    }

    @Override
    public void onIntro() {
        Log.i(TAG, "onIntro");
        this.menuState = INTRO;
        this.view.showTextBox(true);
        this.view.showMenu(false);
        this.view.enableScreenClick(true);
        this.view.showNextText(this.menuState.textId);
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        this.menuState = TEXT_START_1;
        this.view.showTextBox(true);
        this.view.showMenu(false);
        this.view.enableScreenClick(true);
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
        onClickForceTextToFinish(() -> {
            this.menuState = MENU;
            this.view.enableScreenClick(false);
            this.view.showTextBox(false);
            this.view.showMenu(true);
            this.view.playVideo(MENU_IDLE);
        });
    }

    private void onTextClick(MenuState menuState) {
        Log.i(TAG, "onTextClick");
        onClickForceTextToFinish(() -> {
            this.menuState = menuState;
            if (this.menuState == START) {
                this.view.startGame();
            } else {
                this.view.showNextText(this.menuState.textId);
            }
        });
    }
}
