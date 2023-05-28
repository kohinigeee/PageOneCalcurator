package com.example.pageoneculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class ReserveChart implements Serializable {

    private static final long serialVersionUID = 3L;

    private int rowCnt; //生成した行数
    private int maxRow; //そのverでの最大行数
    private int calRow; //固定してある列数
    private int playerNo; //プレイヤー数
    private int[] colorTheme; //カラーテーマ
    private String[] names; //プレイヤーたちの名前
    private ChartCalculator cal; //プレイヤーたちのスコア
    private LocalDateTime date; //チャート生成時の日付
    private String gameName;
    private int ver;
    private long Id;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReserveChart( int maxRow, int calRow, int[] colorTheme,
                        String[] names, ChartCalculator cal, LocalDateTime date, String gameName ) {
        this.rowCnt = cal.getRowCnt();
        this.maxRow = maxRow;
        this.calRow = calRow;
        this.playerNo = cal.getPlayerNo();
        this.colorTheme = colorTheme;
        this.names = names;
        this.cal = cal;
        this.date = date;
        this.gameName = gameName;
        this.Id = 10L;
        this.ver = 1;

        while ( cal.getRowCnt() > calRow ) {
            cal.minRow();
        }
    }

    public long getId() { return Id; }

    public int getMaxRow() {
        return maxRow;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public int getCalRow() {
        return calRow;
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public int[] getColorTheme() {
        return colorTheme;
    }

    public String getGameName() { return gameName; }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String[] getNames() {
        return names;
    }

    public ChartCalculator getCal() {
        return cal;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getVer() {
        return ver;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Intent makeIntent(Activity activity ) {
        Intent intent = new Intent(activity, com.example.pageoneculator.ContinueChartActivity.class);
        intent.putExtra("colorTheme", colorTheme);
        intent.putExtra("playerNo", playerNo);
        intent.putExtra("maxRow", maxRow);
        intent.putExtra("names", names);
        intent.putExtra("calRow", calRow);
        intent.putExtra("cal", cal);
        intent.putExtra("date", date);
        intent.putExtra("gameName", gameName);

        return intent;
    }


    @Override
    public String toString() {
        return "ReserveChart{" +
                "rowCnt=" + rowCnt +
                ", maxRow=" + maxRow +
                ", calRow=" + calRow +
                ", playerNo=" + playerNo +
                ", colorTheme=" + Arrays.toString(colorTheme) +
                ", names=" + Arrays.toString(names) +
                ", cal=" + cal +
                ", date=" + date +
                ", gameName=" + gameName +
                ", ver=" + ver +
                ", Id=" + Id +
                '}';
    }
}
