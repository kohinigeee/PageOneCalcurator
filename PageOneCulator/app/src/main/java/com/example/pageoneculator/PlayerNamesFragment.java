package com.example.pageoneculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.InputEvent;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public  final class PlayerNamesFragment extends DialogFragment {

    private Dialog dialog;
    private Bundle arg;
    private int playerNo = AppValuesManager.getMaxPlayer();
    private String[] names;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState ) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        TableLayout layout = (TableLayout)LayoutInflater.from(getActivity()).inflate(R.layout.layout_names_dialog, null);
        TableRow lastRow = new TableRow(getActivity());
        Button btn = new Button(getActivity());
        Intent intent = new Intent(getActivity(), com.example.pageoneculator.ChartActivity.class);
        arg = getArguments();
        playerNo = arg.getInt(KeyStringsManager.keyPlayerNo.getKey());
        names = new String[playerNo];

        for ( int i = 0; i < playerNo; ++i ) {
            TableRow row = new TableRow(getActivity());
            TextView text = new TextView(getActivity());
            EditText edit = new EditText(getActivity());
            int editId = getResources().getIdentifier("nameEdit"+(i+1), "id", getActivity().getPackageName());

            TableLayout.LayoutParams rowLayout = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT
            );
            row.setLayoutParams(rowLayout);

            TableRow.LayoutParams otherLayout = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT
            );
            text.setLayoutParams(otherLayout);
            text.setPadding(10, 0,10,0);
            text.setTextSize(22);
            text.setText("Player"+(i+1));

            edit.setLayoutParams(otherLayout);
            edit.setHint(R.string.namesDialogHint);
            edit.setId(editId);
            edit.setEms(8);
            edit.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

            row.addView(text);
            row.addView(edit);

            layout.addView(row);
        }

        TableLayout.LayoutParams rowLayout = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT
        );
        lastRow.setLayoutParams(rowLayout);

        TableRow.LayoutParams btnLayout = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT
        );
        btn.setLayoutParams(btnLayout);
        btn.setText("作成");
        btn.setOnClickListener(view-> {

            Log.d(RowBuilder.TAG, "NamesFragment1");

            for ( int i = 0; i < playerNo; ++i ) {
                int editId = getResources().getIdentifier("nameEdit"+(i+1), "id", getActivity().getPackageName());
                EditText edit = layout.findViewById(editId);
                names[i] = edit.getText().toString();
                if ( names[i].equals("") ) {
                    names[i] = "Player"+(i+1);
                }
            }

            Log.d(RowBuilder.TAG, "NamesFragment2");

            ChartValues chartValues = HistoryManager.makeNewChart(ColorManager.getTheme(getActivity()), names, AppValuesManager.getDefaultGameName(), getActivity());

            intent.putExtra(KeyStringsManager.keyChartValues.getKey(), chartValues);
            Log.d(RowBuilder.TAG, "NamesFragment3");

            startActivity(intent);
            dialog.dismiss();
        });

        lastRow.addView(btn);
        layout.addView(lastRow);

        dialog = builder.setTitle(R.string.nameDialogTitle)
                    .setView(layout)
                    .create();

        return dialog;
    }
}
