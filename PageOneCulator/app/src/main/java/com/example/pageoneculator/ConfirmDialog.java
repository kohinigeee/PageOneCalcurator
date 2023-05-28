package com.example.pageoneculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ConfirmDialog extends DialogFragment {

    public final static int MODE_CLEAR_HISTORY = 10;
    public final static int MODE_TRASH_FAVORITE = 20;

    String msg;
    int mode;

    public ConfirmDialog(String msg, int mode) {
        super();
        this.msg = msg;
        this.mode = mode;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("確認")
                .setMessage(msg);

        switch (mode) {
            case ConfirmDialog.MODE_CLEAR_HISTORY:
                builder.setPositiveButton("YES", (view, witch) -> {
                    ((HistoryActivity) requireActivity()).clear();
                })
                        .setNegativeButton("NO", (view, witch) -> {

                        });
                break;
            case ConfirmDialog.MODE_TRASH_FAVORITE:
                Bundle bundle = getArguments();
                builder.setPositiveButton("YES", (view, witch) -> {
                    int id = bundle.getInt(KeyStringsManager.keyGameID.getKey(), -1);
                    HistoryManager.removeFavorite(id, getActivity() );
                    ((FavoriteActivity) getActivity()).update();
                })
                        .setNegativeButton("NO", (view, witch) -> {
                        });
                break;
            default:
                break;
        }

        return builder.create();

    }
}
