package com.example.pageoneculator;

import java.io.Serializable;
import java.util.ArrayList;

public final class PlayerScores implements Serializable {

    private String name;
    private int dobonCnt;
    private int winCnt;
    private int total;
    private ArrayList<Integer> turnScore;
    private ArrayList<Integer> totalScore;
    private ArrayList<Boolean> turnDobon;
    private ArrayList<Boolean> turnWin;

    private static final long serialVersionUID = 2L;

    public PlayerScores(String name ) {
        this.name = name;
        dobonCnt = winCnt = total = 0;
        turnScore = new ArrayList<>();
        totalScore = new ArrayList<>();
        turnDobon = new ArrayList<>();
        turnWin = new ArrayList<>();
    }

    public String getName() { return name; }

    public int getListSize() { return turnScore.size(); }

    public int getDobonCnt() { return dobonCnt; }

    public int getWinCnt() { return winCnt; }

    public int getTotal() { return total; }

    public int getTurnScore( int turn ) {
        return turnScore.get(turn);
    }

    public int getTurnTotal( int turn ) {
        return totalScore.get(turn);
    }

    public void setTurnDobon( int turn, boolean flag ) {
        turnDobon.set(turn, flag);
    }

    public void setTurnWin ( int turn , boolean flag ) {
        turnWin.set(turn, flag);
    }

    public boolean getTurnDobon(int turn ) { return turnDobon.get(turn);}

    public boolean getTurnWin(int turn ) { return turnWin.get(turn); }

    public void setDobonCnt( int dobonCnt ) { this.dobonCnt = dobonCnt; }

    public void setWinCnt(int winCnt) { this.winCnt =winCnt; }

    public void setTurnScore( int turn, int score ) { // turnは1 Origin
        turnScore.set(turn-1, score);
        calTotals();
    }

    public void initDobon() { dobonCnt = 0; }

    public void initWin() { winCnt = 0; }

    public void initTotal() { total = 0; }

    public void initValues() {
        initDobon();
        initWin();
        initTotal();
    }

    public void initTurnScore() {
        for ( int i = 0; i < turnScore.size(); ++i )
            turnScore.set(i,0);
    }

    public void addDobon() { ++dobonCnt; }

    public void addWin() { ++winCnt; }

    public void addCell() {
        turnScore.add(0);
        totalScore.add(total);
        turnWin.add(false);
        turnDobon.add(false);
    }

    public void minCell() {
        if ( turnScore.size() > 0 ) {
            turnScore.remove(turnScore.size()-1);
            totalScore.remove(totalScore.size()-1);
            turnWin.remove(turnWin.size()-1);
            turnDobon.remove(turnDobon.size()-1);
        }
    }

    public void calTotals() {
        if ( turnScore.size() > 0 ) {
            int sum = 0;
            for ( int i = 0; i < turnScore.size(); ++i ) {
                sum += turnScore.get(i);
                totalScore.set(i, sum);
            }
            total = sum;
        }
    }

    @Override
    public String toString() {
        return "PlayerScores{" +
                "name='" + name + '\'' +
                ", dobonCnt=" + dobonCnt +
                ", winCnt=" + winCnt +
                ", total=" + total +
                ", turnScore=" + turnScore +
                ", totalScore=" + totalScore +
                '}';
    }
}
