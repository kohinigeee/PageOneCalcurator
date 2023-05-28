package com.example.pageoneculator;

import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class ContinueChartActivity extends ChartActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

        ChartCalculator cal = super.getCal();

        Log.d(RowBuilder.TAG, "getCalRow = " + super.getCalRow());

        for ( int i = 0; i < super.getPlayerNo(); ++i ) {
            TextView total = findViewById(ChartCellsManager.getTotalIdAt(i));
            PlayerScores player = cal.getPlayersList().get(i);
            total.setText( String.format("Total　%d\nあがり　%d\nドボン　%d",
                    player.getTotal(), player.getWinCnt(), player.getDobonCnt()));
        }

        for ( int i = 0; i < super.getCalRow(); ++i ) {
            TableRow row = findViewById(ChartCellsManager.getTableRowIdAt(i));
            for ( int j = 0; j < super.getPlayerNo(); ++j ) {
                LinearLayout linear = row.findViewById(ChartCellsManager.getLinearIdAt(j));
                EditText edit = linear.findViewById(ChartCellsManager.getEditScoreId());
                TextView text = linear.findViewById(ChartCellsManager.getScoreTextId());
                CheckBox win = linear.findViewById(ChartCellsManager.getWinId());
                CheckBox dobon = linear.findViewById(ChartCellsManager.getDobonId());

                edit.setText( String.valueOf(cal.getPlayersList().get(j).getTurnScore(i)));
                text.setText("/" + cal.getPlayersList().get(j).getTurnTotal(i));
                win.setChecked(cal.getPlayersList().get(j).getTurnWin(i));
                dobon.setChecked(cal.getPlayersList().get(j).getTurnDobon(i));
            }
            ((CheckBox)row.findViewById(ChartCellsManager.getFocusableCheck())).setChecked(true);
        }

        Log.d(RowBuilder.TAG, "calRow() = " + super.getCalRow() );
        Log.d(RowBuilder.TAG, "list.size() = " + cal.getPlayersList().get(0).getListSize() );
    }

}
