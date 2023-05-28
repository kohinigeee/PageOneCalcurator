package com.example.pageoneculator;

public enum KeyStringsManager {

    keyChartValues("chartValues_key"),
    keyGameID("gameID_key"),
    keyPlayerNo("playerNo_key");

    private String key;

     KeyStringsManager(String key ) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
