package com.example.pageoneculator;

import android.content.Context;
import android.content.res.Resources;

public class ChartCellsManager {

    private static Context context;
    private static Resources res;
    private final static int[] namesId = { R.id.Name1, R.id.Name2, R.id.Name3, R.id.Name4, R.id.Name5, R.id.Name6, R.id.Name7, R.id.Name8};
    private final static int[] totalsId = { R.id.total1, R.id.total2, R.id.total3, R.id.total4, R.id.total5, R.id.total6, R.id.total7, R.id.total8};
    private final static int[] linearId = new int[AppValuesManager.getMaxPlayer()];
    private final static int[] tableRowsId = new int[AppValuesManager.getMaxRow()];
    private static int scoreTextId, editScoreId, dobonId, winId, focusableCheck;

    public static int getNameIdAt ( int x ) {
        return namesId[x];
    }

    public static int getTotalIdAt( int x ) {
        return totalsId[x];
    }

    public static int getLinearIdAt( int x ) { return linearId[x];}

    public static int getTableRowIdAt ( int x ) { return tableRowsId[x]; }

    public static int getScoreTextId() { return scoreTextId;}

    public static int getEditScoreId() { return  editScoreId; }

    public static int getDobonId() { return dobonId; }

    public static int getWinId() { return winId; }

    public static int getFocusableCheck() { return focusableCheck; }

    public static  void setContext( Context con ) {
        context = con;
        res = con.getResources();
        scoreTextId = res.getIdentifier("scoreTotalText", "id", con.getPackageName());
        editScoreId = res.getIdentifier("editScore", "id", con.getPackageName());
        dobonId = res.getIdentifier("checkDobon", "id", con.getPackageName());
        winId = res.getIdentifier("checkWin", "id", con.getPackageName());
        focusableCheck = res.getIdentifier("focusableCheck", "id", con.getPackageName());

        for ( int i = 0; i <  linearId.length; ++i ) {
            linearId[i] = res.getIdentifier("cellLinear"+(i+1), "id", con.getPackageName());
        }
        for ( int i = 0; i < tableRowsId.length; ++i ) {
            tableRowsId[i] = res.getIdentifier("tableRow"+(i+1), "id", con.getPackageName());
        }
    }
}
