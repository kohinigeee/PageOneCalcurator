package com.example.pageoneculator;

import android.os.Build;
import android.widget.TableLayout;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ChartValues implements Serializable {

    private static final long serializeUID = 10;

    private int[] colorTheme;
    private String[] names;
    private ChartCalculator cal;
    private LocalDateTime date;
    private String gameName;
    private Integer gameID;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ChartValues(int[] colorTheme, String[] names, String gameName, Integer gameId,  ChartActivity chart ) {
        this.colorTheme = colorTheme;
        this.names = names;
        this.cal = new ChartCalculator( chart, names);
        this.gameName = gameName;
        this.date = LocalDateTime.now();
        this.gameID = gameId;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ChartValues(int[] colorTheme, String[] names, String gameName, Integer gameID ) {
       this(colorTheme, names,  gameName , gameID, null);
    }

    public void setChart( ChartActivity chart ) {
        this.cal.setChart(chart);
    }

    public void setGameName ( String gameName ) { this.gameName = gameName; }

    public int[] getColorTheme() { return colorTheme; }

    public String[] getNames() { return names; }

    public ChartCalculator getCal() { return cal; }

    public LocalDateTime getDate() { return date; }

    public String getGameName() { return gameName; }

    public int getPlayerNo() { return cal.getPlayerNo(); }

    public int getRowCnt() { return cal.getRowCnt(); }

    public boolean isSetChart() { return cal != null; }

    public Integer getGameID() { return gameID; }

}
