package com.example.pageoneculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class ColorManager {
    private final static int maxNo = 8;

    private final static int[] defTheme = { R.color.cellColorEven, R.color.white, R.color.cellColorEven,  R.color.white,
            R.color.cellColorEven,  R.color.white,  R.color.cellColorEven,  R.color.white};

    private final static int[] theme1 = { R.color.cellColorBlue,  R.color.cellColorOrange,  R.color.cellColorPurple, R.color.cellColorRed,
            R.color.cellColorGreen,  R.color.cellColorYellow,  R.color.cellColorEven, R.color.cellColorYellowGreen};

    private final static int[] theme2 = { R.color.cellColorGreen, R.color.cellColorBlue,  R.color.cellColorOrange,  R.color.cellColorPurple,
            R.color.cellColorGreen, R.color.cellColorBlue,  R.color.cellColorOrange,  R.color.cellColorPurple };

    private final static int[] customTheme = new int[maxNo];

    private final static HashMap<String, int[]> themes = new HashMap<String, int[]>(){ {
        put("無効", null);
        put("デフォルト(白・水色)", defTheme);
        put("テーマ1", theme1);
        put("テーマ2", theme2);
    }
    };

    private final static HashMap<String, Integer> colors = new HashMap<String, Integer>() {{
        put("白", R.color.white);
        put("青", R.color.cellColorBlue);
        put("オレンジ", R.color.cellColorOrange);
        put("紫", R.color.cellColorPurple);
        put("緑", R.color.cellColorGreen);
        put("赤", R.color.cellColorRed);
        put("黄", R.color.cellColorYellow);
        put("黄緑", R.color.cellColorYellowGreen);
        put("水色", R.color.cellColorEven);
    }
    };


    public static int[] getTheme(Context context ) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String theme = pref.getString("colorThemeList", "デフォルト(白・水色)");

        int[] temp = themes.get(theme);
        if ( temp == null ) Log.d(RowBuilder.TAG, "temp = null");
        if ( temp != null ) return temp;


        for ( int i = 0; i < maxNo; ++i ) {
            String color = pref.getString("player_color"+(i+1), "白");
            customTheme[i] = colors.get(color);
        }

        return customTheme;
    }


}
