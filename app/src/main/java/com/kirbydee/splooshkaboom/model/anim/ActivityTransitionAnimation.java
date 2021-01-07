package com.kirbydee.splooshkaboom.model.anim;

import com.kirbydee.splooshkaboom.R;

public enum ActivityTransitionAnimation {

    NORMAL_FADE(android.R.anim.fade_in, android.R.anim.fade_out),
    LONG_FADE(R.anim.fade_in, R.anim.fade_out),
    NO_SPECIAL(-1, -1);

    public int enterAnim;
    public int exitAnim;

    ActivityTransitionAnimation(int enterAnim, int exitAnim) {
        this.enterAnim = enterAnim;
        this.exitAnim = exitAnim;
    }
}
