package com.example.pageoneculator;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class RowBuilder2 {

    private static int maxCol = 8;

    public static void setMaxCol(int x) {
        if ( x > 0 && x <= AppValuesManager.getMaxPlayer() )
            maxCol = x;
        else
            maxCol = AppValuesManager.getMaxPlayer();
    }

    public static String TAG = "LOG_VER1";

    public static TableRow Build(ChartActivity context, int rowNo, String[] names, int[] theme, Resources res, String packName, ChartCalculator cal) {
        TableRow tablerow = new TableRow(context);
        int textId = R.id.scoreTotalText;
        int editId = R.id.editScore;
        int rowId = res.getIdentifier("tableRow" + (rowNo + 1), "id", packName);

        LinearLayout turnLayout = new LinearLayout(context);
        turnLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams turnLayoutParam = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        turnLayout.setLayoutParams(turnLayoutParam);
        turnLayout.setBackgroundResource(R.color.white);

        TextView turnView = new TextView(context);
        turnView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1
        ));
        turnView.setText(String.format("turn %d", rowNo + 1));
        turnView.setTextSize(22);
        turnView.setGravity(Gravity.CENTER);
        turnView.setBackgroundResource(R.color.white);
        tablerow.setId(rowId);
        Log.d("LOG_VER1", "rowSetId = " + rowId);

        CheckBox focusCheck = new CheckBox(context);
        LinearLayout.LayoutParams focusCheckLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        focusCheckLayout.gravity = Gravity.RIGHT;
        focusCheck.setLayoutParams(focusCheckLayout);
        focusCheck.setText("入力終了");
        focusCheck.setTag(rowNo);
        focusCheck.setId(ChartCellsManager.getFocusableCheck());
        focusCheck.setOnCheckedChangeListener( ( view, isCheckd)-> {
            int tag = (Integer)view.getTag();
            context.changeRowFocusable(!isCheckd, tag+1);
        });

        turnLayout.addView(turnView);
        turnLayout.addView(focusCheck);
        tablerow.addView(turnLayout);

        for (int i = 0; i < maxCol; ++i) {

            int tmp = i;

            LinearLayout layout = new LinearLayout(context);
            LinearLayout inLayout = new LinearLayout(context);
            TextView nameTag = new TextView(context);
            TextView text = new TextView(context);
            EditText edit = new EditText(context);
            CheckBox dobon = new CheckBox(context);
            CheckBox win = new CheckBox(context);
            int layoutId = res.getIdentifier("cellLinear" + (i + 1), "id", packName);

            //LinearLayoutの設定
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT
            );
            layout.setBackgroundResource(theme[i]);
            layout.setOrientation(LinearLayout.VERTICAL);
            layoutParams.setMargins(3, 0, 0, 0);
            layout.setLayoutParams(layoutParams);
            layout.setId(layoutId);

            LinearLayout.LayoutParams inLayoutPrams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            );
            inLayout.setOrientation(LinearLayout.HORIZONTAL);
            inLayout.setLayoutParams(inLayoutPrams);

            //NameTagの設定
            LinearLayout.LayoutParams nameTagLayoutPrams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            nameTag.setLayoutParams(nameTagLayoutPrams);
            nameTag.setText(names[i]);

            //TextViewの設定
            LinearLayout.LayoutParams textLayoutPrams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0
            );
            text.setLayoutParams(textLayoutPrams);
            text.setPadding(0, 0, 20, 0);
            text.setText(R.string.default_totalScore);
            text.setTextSize(22);
            text.setId(textId);

            //EditTextの設定
            LinearLayout.LayoutParams editLayoutPrams = new LinearLayout.LayoutParams(
                    160, LinearLayout.LayoutParams.WRAP_CONTENT);
            edit.setLayoutParams(editLayoutPrams);
            edit.setHint("score");
            edit.setInputType(2);
            edit.setId(editId);
            edit.setEms(5);
            edit.setOnClickListener((view) -> {

                if (((ChartActivity) context).getCardScoreFlag()) {
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

            //checkBoxの設定
            LinearLayout.LayoutParams checkLayout1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            );
            LinearLayout.LayoutParams checkLayout2 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            );
            dobon.setLayoutParams(checkLayout1);
            dobon.setText("ドボン");
            dobon.setId(ChartCellsManager.getDobonId());
            dobon.setOnCheckedChangeListener( ( view, flag)->{
                cal.getPlayersList().get(tmp).setTurnDobon(rowNo, dobon.isChecked());
            });
            win.setLayoutParams(checkLayout2);
            win.setText("あがり");
            win.setId(ChartCellsManager.getWinId());
            win.setOnCheckedChangeListener( ( view, isCheck )-> {
                cal.getPlayersList().get(tmp).setTurnWin(rowNo, win.isChecked());
            });

            inLayout.addView(edit);
            inLayout.addView(text);
            layout.addView(nameTag);
            layout.addView(inLayout);
            layout.addView(dobon);
            layout.addView(win);

            tablerow.addView(layout);

        }
        return tablerow;
    }
}
