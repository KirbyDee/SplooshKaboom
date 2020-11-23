package com.kirbydee.splooshkaboom.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class RestartDialog {

    private final Context context;

    private Listener listener;

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
                .show();
    }
}
