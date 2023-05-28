package com.example.pageoneculator;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class RowBuilder {

    private static int maxCol = 8;

    public static void setMaxCol(int x ) {
        maxCol = x;
    }
    public static String TAG = "LOG_VER1";

    public static TableRow Build(Context context, int rowNo, int[] theme,  Resources res, String packName ) {
        TableRow tablerow = new TableRow(context);
        int textId = R.id.scoreTotalText;
        int editId = R.id.editScore;
        int rowId = res.getIdentifier("tableRow"+(rowNo+1), "id", packName);


        TextView turnView = new TextView(context);
        turnView.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, 130
        ) );
        turnView.setText(String.format("turn %d", rowNo+1));
        turnView.setTextSize(22);
        turnView.setGravity(Gravity.CENTER);
        turnView.setBackgroundResource(R.color.white);
        tablerow.setId(rowId);
        Log.d("LOG_VER1", "rowSetId = " + rowId);


        tablerow.addView(turnView);

        for (int i = 0; i < maxCol; ++i) {
            //LinearLayoutの設定
            LinearLayout layout = new LinearLayout(context);
            TextView text = new TextView(context);
            EditText edit = new EditText(context);
            int layoutId = res.getIdentifier("cellLinear"+(i+1), "id", packName);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT
            );
            layout.setBackgroundResource(theme[i]);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            layoutParams.setMargins(3, 0, 0, 0);
            layout.setLayoutParams(layoutParams);
            layout.setId(layoutId);

            //TextViewの設定
            LinearLayout.LayoutParams textLayoutPrams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0
            );
            text.setLayoutParams(textLayoutPrams);
            text.setText(R.string.default_totalScore);
            text.setTextSize(22);
            text.setId(textId);

            //EditTextの設定
            LinearLayout.LayoutParams editLayoutPrams = new LinearLayout.LayoutParams(
                    210, LinearLayout.LayoutParams.WRAP_CONTENT);
            edit.setLayoutParams(editLayoutPrams);
            edit.setHint("score");
            edit.setInputType(2);
            edit.setId(editId);
            edit.setEms(5);
            edit.setOnClickListener((view) -> {

                if ( ((ChartActivity)context).getCardScoreFlag() ) {
                    DialogFragment dialog = new ScoreCardFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("editId", view.getId());
                    bundle.putInt("tableRowId", tablerow.getId());
                    bundle.putInt("layoutId", layoutId);

                    Log.d("LOG_VER1", "LOG1_2");

                    dialog.setArguments(bundle);
                    dialog.show(((ChartActivity) context).getSupportFragmentManager(), "dialog_score");
                }

            });


            layout.addView(edit);
            layout.addView(text);

            tablerow.addView(layout);

        }
        return tablerow;
    }
}
