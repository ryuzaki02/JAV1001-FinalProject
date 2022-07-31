package com.cambrian.jav1001_finalproject;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class Dialog {
    public static Dialog instance = new Dialog();

    void showDialog(Context context, String message, boolean showSecondButton, String secondButtonText, DialogResultHandler dialogResultHandler) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (dialogResultHandler != null) {
                            dialogResultHandler.firstButtonClicked();
                        }
                        dialog.cancel();
                    }
                });
        if (showSecondButton) {
            builder1.setNegativeButton(
                    "Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (dialogResultHandler != null) {
                                dialogResultHandler.secondButtonClicked();
                            }
                            dialog.cancel();
                        }
                    });
        }

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
