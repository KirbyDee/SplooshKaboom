package com.kirbydee.splooshkaboom.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.kirbydee.splooshkaboom.R;

public class RestartDialog {

    private Listener listener;

    private final Context context;

    public RestartDialog(Context context) {
        this.context = context;

        if (context instanceof Listener) {
            this.listener = (Listener) context;
        }
    }

    public interface Listener {

        void onPositive(DialogInterface dialog);

        void onNegative(DialogInterface dialog);
    }

    public void show() {
        new AlertDialog.Builder(this.context)
                .setTitle("RestARRRRt")
                .setMessage("Aye?")
                .setPositiveButton("Kaboom", (dialog, which) -> {
                    if (this.listener != null) {
                        this.listener.onPositive(dialog);
                    }
                })
                .setNegativeButton("Sploosh", (dialog, which) -> {
                    if (this.listener != null) {
                        this.listener.onNegative(dialog);
                    }
                })
                .setIcon(R.drawable.alert_icon)
                .show();
    }
}
