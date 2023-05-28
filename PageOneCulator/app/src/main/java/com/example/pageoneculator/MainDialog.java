package com.example.pageoneculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

public final class MainDialog extends DialogFragment {

    private Intent intent;
    private LinearLayout layout;
    private final int maxNo = 8;
    private AlertDialog dialog  = null;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState ) {

        Log.d(RowBuilder.TAG, "MainDialog0");

        intent = new Intent(getActivity(), com.example.pageoneculator.ChartActivity.class);
        layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_main_dialog, null);
        Button button = layout.findViewById(R.id.createButton);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        button.setOnClickListener(view -> {
            EditText edit = layout.findViewById(R.id.editPlayerNo);
            Bundle bundle = getArguments();
            int playerNo;

            intent.putExtra("colorTheme", bundle.getIntArray("colorTheme"));

            try {
                playerNo = Integer.parseInt(edit.getText().toString());
                if (playerNo > maxNo || playerNo < 0 ) {
                    Toast.makeText(getActivity(), R.string.numberErrorMsg, Toast.LENGTH_SHORT).show();
                } else {
                    bundle.putInt(KeyStringsManager.keyPlayerNo.getKey(), playerNo);
                    DialogFragment namesDialog = new PlayerNamesFragment();
                    namesDialog.setArguments(bundle);
                    namesDialog.show(getActivity().getSupportFragmentManager(), "namesDialog");

                    dialog.dismiss();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getActivity(), R.string.numberErrorMsg, Toast.LENGTH_SHORT).show();
            }

        });

        dialog = builder.setTitle("表情報の入力")
                .setView(layout)
                .create();

        return dialog;

    }
}
