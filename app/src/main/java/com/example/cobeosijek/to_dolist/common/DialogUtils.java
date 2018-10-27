package com.example.cobeosijek.to_dolist.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.cobeosijek.to_dolist.R;
import com.example.cobeosijek.to_dolist.ui.listeners.OnDeleteToDoListener;

/**
 * Created by cobeosijek on 08/08/2017.
 */

public class DialogUtils {

    public static void showDeleteDialog(Context context, final int id, final OnDeleteToDoListener onDeleteToDoListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(context.getString(R.string.delete_to_do_title));
        alertDialog.setMessage(context.getString(R.string.delete_to_do_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, context.getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onDeleteToDoListener.onDeleteToDo(id);
                    }
                });
        alertDialog.show();
    }
}
