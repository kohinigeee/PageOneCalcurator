package com.example.pageoneculator;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.service.restrictions.RestrictionsReceiver;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HistoryManager {

    private static HashMap<Integer, ChartValues> history = null;
    private static HashMap<Integer, ChartValues> favorite = null;
    private static IdManageQueue gameIDs = null;
    private static String path = AppValuesManager.getHistoryPath();
    private static String path2 = AppValuesManager.getFavoritePath();

    public static void readHistory(Context context) {

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(context.openFileInput(path)))) {
            history = (HashMap<Integer, ChartValues>) in.readObject();
            Toast.makeText(context, "履歴の読み込みに成功しました", Toast.LENGTH_SHORT).show();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            history = new HashMap<>();
            Toast.makeText(context, "履歴ファイルが開けません", Toast.LENGTH_SHORT).show();
        }


        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(context.openFileInput(path2)))) {
            favorite = (HashMap<Integer, ChartValues>) in.readObject();
            Toast.makeText(context, "履歴の読み込みに成功しました", Toast.LENGTH_SHORT).show();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            favorite = new HashMap<>();
            Toast.makeText(context, "履歴ファイルが開けません", Toast.LENGTH_SHORT).show();
        }

        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(context.openFileInput(AppValuesManager.getGameIdsPath())))) {
            gameIDs = (IdManageQueue) in.readObject();
            Toast.makeText(context, "IDの読み込みに成功しました", Toast.LENGTH_SHORT).show();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            gameIDs = new IdManageQueue(1000);
            Toast.makeText(context, "IDの読み込みに失敗しました", Toast.LENGTH_SHORT).show();
        }
    }

    public static void writeHistory(Context context) {

        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(AppValuesManager.getHistoryPath(), Context.MODE_PRIVATE)))) {
            out.writeObject(history);
        } catch (IOException e) {
            Log.d(RowBuilder.TAG, "Out 書き込み失敗");
        }
    }

    public static void writeFavorite(Context context) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(AppValuesManager.getFavoritePath(), Context.MODE_PRIVATE)))) {
            out.writeObject(favorite);
        } catch (IOException e) {
            Log.d(RowBuilder.TAG, "Out 書き込み失敗");
        }
    }

    public static void writeGameIDs(Context context) {
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(AppValuesManager.getGameIdsPath(), Context.MODE_PRIVATE)))) {
            out.writeObject(gameIDs);
        } catch (IOException e) {
            Log.d(RowBuilder.TAG, "gameIDs 書き込み失敗");
        }
    }

    public static void addHistory(ChartValues chartValues, Context context) {
        history.put(chartValues.getGameID(), chartValues);
        writeHistory(context);
    }

    public static void removeHistory(Integer gameID, Context context) {
        if ( gameID == -1 ) return;
        history.remove(gameID);
        writeHistory(context);
        setNextID(gameID, context);
    }

    public static void clearHistory(Context context) {
        for (Map.Entry<Integer, ChartValues> entry : history.entrySet() ) {
            gameIDs.setNext(entry.getKey());
        }
        history.clear();
        writeHistory(context);
        writeGameIDs(context);
    }

    public static void addFavorite(ChartValues chartValues, Context context) {
        favorite.put(chartValues.getGameID(), chartValues);
        writeFavorite(context);
    }

    public static void removeFavorite(Integer gameID, Context context) {
        if ( gameID == -1 ) return;
        favorite.remove(gameID);
        writeFavorite(context);
        setNextID(gameID, context);
    }


    public static void toFavoriteFromHistory(Integer gameID, Context context) { //履歴からお気に入りに保存する際の関数
        if ( gameID == -1 ) return;
        ChartValues chartValues = history.get(gameID);
        history.remove(gameID);
        writeHistory(context);
        addFavorite(chartValues, context);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ChartValues makeNewChart(int[] colorTheme, String[] names, String gameName, Context context) {
        ChartValues chartValues = new ChartValues(colorTheme, names, gameName, getNextID(context));
        addHistory(chartValues, context);
        return chartValues;
    }


    private static Integer getNextID(Context context) {
        Integer next = gameIDs.getNext();
        writeGameIDs(context);
        return next;
    }

    private static void setNextID(Integer x, Context context) {
        gameIDs.setNext(x);
        writeGameIDs(context);
    }

    public static ArrayList<ChartValues> historyToList() {
        ArrayList<ChartValues> list = new ArrayList<>();

        for (Map.Entry<Integer, ChartValues> entry : history.entrySet()) {
            list.add(entry.getValue());
        }

        return list;
    }

    public static ArrayList<ChartValues> favoriteToList() {
        ArrayList<ChartValues> list = new ArrayList<>();

        for (Map.Entry<Integer, ChartValues> entry : favorite.entrySet()) {
            list.add(entry.getValue());
        }

        return list;
    }

};