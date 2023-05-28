package com.example.pageoneculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;

public class ChartActivity extends AppCompatActivity  {

    public static final int FROM_FAVORITE = 10;

    private Intent intent = null;

    private int rowCnt = 0;
    private int initRow = AppValuesManager.getInitRow(); //アクティビティ起動時のデフォルトの行の数
    private int maxRow = AppValuesManager.getMaxRow(); //テーブルの最大の行数
    private int calRow = 0;
    private ChartValues chartValues;
    private boolean cardScoreFlag = false; //カードスコア入力モードのon/off　フラグ
    private TableLayout tbLayout;

    public boolean getCardScoreFlag () { return cardScoreFlag; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chart);

        intent = getIntent();
        tbLayout = findViewById(R.id.tableLayout);
        chartValues = (ChartValues)intent.getSerializableExtra(KeyStringsManager.keyChartValues.getKey());
        calRow = chartValues.getRowCnt();

        chartValues.setChart(this);


        Log.d(RowBuilder.TAG, "StartChartActivity");

        for (int i = 0; i < chartValues.getPlayerNo(); ++i) {
            TextView name = tbLayout.findViewById(ChartCellsManager.getNameIdAt(i));
            TextView total = tbLayout.findViewById(ChartCellsManager.getTotalIdAt(i));
            name.setBackgroundResource(chartValues.getColorTheme()[i]);
            total.setBackgroundResource(chartValues.getColorTheme()[i]);
            name.setText(chartValues.getNames()[i]);
        }
        for (int i = chartValues.getPlayerNo(); i < AppValuesManager.getMaxPlayer() + 1; ++i) {
            tbLayout.setColumnCollapsed(i + 1, true);
        }

        RowBuilder2.setMaxCol(chartValues.getPlayerNo());
        while ( rowCnt < chartValues.getRowCnt() ) {
            createRow();
        }
        while (rowCnt < initRow) {
            createRow();
            chartValues.getCal().addRow();
        }

        Log.d(RowBuilder.TAG, "ChartActivity3");

        Switch chBox = findViewById(R.id.cardScoreCheckBox);
        chBox.setOnCheckedChangeListener((view, checked) -> {
            cardScoreFlag = checked;
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected  void onDestroy ( ) {
        super.onDestroy();

        Log.d(RowBuilder.TAG, "Start ChartActivity onStop");
        ChartCalculator temp = chartValues.getCal();
        while ( temp.getRowCnt() > calRow ) {
            temp.minRow();
        }


        if ( intent.getIntExtra("mode", 0) == FROM_FAVORITE ) {
            HistoryManager.addFavorite(chartValues, this);
        } else {
            HistoryManager.addHistory(chartValues, this);
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    public void changeBoxChangeFocusable (CheckBox box, boolean f ) {
        if ( !f ) {
            box.setOnTouchListener((view, mode)-> {
                return true;
            });
        } else {
            box.setOnTouchListener((view, mode)-> {
                if ( mode.getAction() == MotionEvent.ACTION_DOWN) {
                    ((CheckBox)view).setChecked(!((CheckBox)view).isChecked());
                }
                return true;
            });
        }
    }

    protected void changeRowFocusable ( boolean flag, int index ) { // trueで固定解除、falseで固定 indexは1 origin
        TableRow row = findViewById(ChartCellsManager.getTableRowIdAt(index-1));
        for ( int i = 0; i < chartValues.getPlayerNo() ; ++i ) {
            LinearLayout layout = row.findViewById(ChartCellsManager.getLinearIdAt(i));
            EditText edit = layout.findViewById(ChartCellsManager.getEditScoreId());
            CheckBox dobon = layout.findViewById(ChartCellsManager.getDobonId());
            CheckBox win = layout.findViewById(ChartCellsManager.getWinId());

            if ( flag ) {
                edit.setFocusableInTouchMode(true);
                changeBoxChangeFocusable(dobon, true);
                changeBoxChangeFocusable(win,true);
            } else {
                edit.setFocusable(false);
                changeBoxChangeFocusable(dobon, false);
                changeBoxChangeFocusable(win,false);
            }
        }
    }


    public void btn_onClick (View view ) {
        if ( rowCnt >= maxRow ) {
            Toast.makeText(this,R.string.maxRow_error, Toast.LENGTH_SHORT).show();
        } else {
            createRow();
            chartValues.getCal().addRow();
            Toast.makeText(this, "turn" + rowCnt + "を追加しました", Toast.LENGTH_SHORT).show();
        }
    }

    public void btn_onUpdate( View view ) {
        calRow = chartValues.getCal().calculate();
        for ( PlayerScores tmp: chartValues.getCal().getPlayersList()) {
            Log.d(RowBuilder.TAG,tmp.toString());
        }
    }

    public void createRow() {
        TableRow row = RowBuilder2.Build(this, rowCnt, chartValues.getNames(), chartValues.getColorTheme(), getResources(), getPackageName(), chartValues.getCal());
        TableLayout.LayoutParams rowLayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT);
        rowLayout.setMargins(0, 3, 0, 0);
        row.setLayoutParams(rowLayout);
        tbLayout.addView(row);
        ++rowCnt;
    }

    protected int getCalRow() {
        return calRow;
    }

    protected ChartCalculator getCal() {
        return chartValues.getCal();
    }

    protected int getPlayerNo() {
        return chartValues.getPlayerNo();
    }
}