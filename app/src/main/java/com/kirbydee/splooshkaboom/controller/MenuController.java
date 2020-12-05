package com.kirbydee.splooshkaboom.controller;

import android.content.Context;
import android.util.Log;

import com.kirbydee.splooshkaboom.model.MenuState;
import com.kirbydee.splooshkaboom.model.media.Video;

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

public class MenuController {

    private static final String TAG = MenuController.class.getName();

    // Listener
    private Listener listener;

    // current menu state
    private MenuState menuState;

    public interface Listener {

        boolean isTextBoxFinished();

        void playVideo(Video video);

        void removeNextText();

        void disableScreenTouch();

        void enableScreenTouch();

        void startGame();

        void forceFinishText();

        void showNextText(int resId);

        void showMenu(boolean show);

        void showMenuText(boolean show);
    }

    public MenuController(Context context) {
        this.menuState = INIT;
        if (context instanceof MenuController.Listener) {
            this.listener = (MenuController.Listener) context;
        }
    }

    public void onIntro() {
        Log.i(TAG, "onIntro");
        this.menuState = INTRO;
        this.listener.showMenuText(true);
        this.listener.showMenu(false);
        this.listener.enableScreenTouch();
        this.listener.showNextText(this.menuState.textId);
    }

    public void onStart() {
        Log.i(TAG, "onStart");
        this.menuState = TEXT_START_1;
        this.listener.showMenuText(true);
        this.listener.showMenu(false);
        this.listener.enableScreenTouch();
        this.listener.showNextText(this.menuState.textId);
        this.listener.playVideo(Video.MENU_TALK);
    }

    public void onTouchScreen() {
        Log.i(TAG, "onTouchScreen");
        if (this.listener == null) {
            return;
        }

        switch (menuState) {
            case INTRO:
                onIntroTouch();
                break;
            case TEXT_START_1:
                onTextTouch(TEXT_START_2);
                break;
            case TEXT_START_2:
                onTextTouch(TEXT_START_3);
                break;
            case TEXT_START_3:
                onTextTouch(TEXT_START_4);
                break;
            case TEXT_START_4:
                onTextTouch(TEXT_START_5);
                break;
            case TEXT_START_5:
                onTextTouch(TEXT_START_6);
                break;
            case TEXT_START_6:
                onTextTouch(START);
                break;
            default:
                // do nothing
                break;
        }
    }

    private void onIntroTouch() {
        Log.i(TAG, "onIntroTouch");
        if (this.listener.isTextBoxFinished()) {
            this.menuState = MENU;
            this.listener.disableScreenTouch();
            this.listener.showMenuText(false);
            this.listener.removeNextText();
            this.listener.showMenu(true);
            this.listener.playVideo(Video.MENU_IDLE);
        }
        else {
            this.listener.forceFinishText();
        }
    }

    private void onTextTouch(MenuState menuState) {
        Log.i(TAG, "onTextTouch");
        if (this.listener.isTextBoxFinished()) {
            this.listener.removeNextText();
            this.menuState = menuState;
            if (this.menuState == START) {
                this.listener.startGame();
            } else {
                this.listener.showNextText(this.menuState.textId);
            }
        }
        else {
            this.listener.forceFinishText();
        }
    }
}
