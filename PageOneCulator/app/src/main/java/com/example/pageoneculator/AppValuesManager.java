package com.example.pageoneculator;

public final class AppValuesManager {

    private static final int maxPlayer = 8;
    private static int initRow = 4;
    private static final int maxRow = 20;
    private static final int valuesVersion = 1;
    private static final String defaultGameName = "NameLess";
    private static final String historyPath = "history.dat";
    private static final String favoritePath = "favorite.dat";
    private static  final String gameIdsPath = "gameIds.dat";

    public static int getMaxPlayer () { return maxPlayer; }

    public static int getInitRow() { return  initRow; }

    public static int getMaxRow() { return maxRow; }

    public static int getValuesVersion() { return valuesVersion; }

    public static void setInitRow(int x ) { initRow = x; }

    public static String getDefaultGameName() { return defaultGameName; }

    public static String getHistoryPath() { return historyPath; }

    public static String getFavoritePath() { return favoritePath; }

    public static String getGameIdsPath() { return gameIdsPath; }

    public static String[] getDefaultNames( int playerNo ) {
        String[] names = new String[playerNo];
        for ( int i = 0; i < playerNo; ++i ) {
            names[i] = "Player"+(i+1);
        }

        return names;
    }
}
