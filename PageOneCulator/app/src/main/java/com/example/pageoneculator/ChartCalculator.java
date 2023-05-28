package com.example.pageoneculator;

import android.content.Intent;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public final class ChartCalculator implements Serializable {

    private static final long serialVersionUID = 1L;

    transient  private ChartActivity chart;
    private int rowCnt;
    private int playerNo;
    private ArrayList<PlayerScores> playersList;

    public ChartCalculator(ChartActivity chart, String[] names) {

        Log.d(RowBuilder.TAG, "ChartCalculator constructor_start");

        this.chart = chart;
        this.rowCnt = 0;
        this.playerNo = names.length;
        this.playersList = new ArrayList<>();
        for (String name : names) {
            PlayerScores player = new PlayerScores(name);
            playersList.add(player);
        }

        Log.d(RowBuilder.TAG, "ChartCalculator constructor_end");
    }


    public int getPlayerNo() {
        return playerNo;
    }

    public int getRowCnt() {
        return rowCnt;
    }

    public ArrayList<PlayerScores> getPlayersList() {
        return playersList;
    }

    void setChart( ChartActivity chart ) {
        this.chart = chart;
    }

    public void addRow() {
        for (PlayerScores player : playersList) {
            player.addCell();
        }
        ++rowCnt;
    }

    public void minRow() {
        for (PlayerScores player : playersList) {
            player.minCell();
        }
        --rowCnt;
    }

    public void calRow(int rowIndex) { //rowIndex行目のスコアを計算する(rosIndexは1 Origin) 固定なrowまでしかスコア計算をしないようにする。
        Log.d(RowBuilder.TAG, "calRow");

        if (rowIndex < 0 || rowIndex > rowCnt) return;

        Log.d(RowBuilder.TAG, "calRow start");

        TableRow row = chart.findViewById(ChartCellsManager.getTableRowIdAt(rowIndex - 1));

        Log.d(RowBuilder.TAG, "calRow a");
        ArrayList<Integer> winPlayers = new ArrayList<>();
        Log.d(RowBuilder.TAG, "calRow b");

        int sum = 0;
        int winScore;

        Log.d(RowBuilder.TAG, "calRow_0");

        for (int i = 0; i < playerNo; ++i) {
            int linearId = ChartCellsManager.getLinearIdAt(i);
            LinearLayout linear = row.findViewById(linearId);
            CheckBox checkWin = linear.findViewById(ChartCellsManager.getWinId());
            CheckBox checkDobon = linear.findViewById(ChartCellsManager.getDobonId());
            TextView scoreText = linear.findViewById(ChartCellsManager.getScoreTextId());
            EditText edit = linear.findViewById(ChartCellsManager.getEditScoreId());
            Log.d(RowBuilder.TAG, "calRow_for_1");
            if (linear == null) {
                Log.d(RowBuilder.TAG, "linear = null");
            }
            if (checkWin == null) {
                Log.d(RowBuilder.TAG, "checkBox = null");
            }
            if (edit == null) {
                Log.d(RowBuilder.TAG, "edit = null");
            }

            if (checkWin.isChecked()) {
                winPlayers.add(i);
                playersList.get(i).addWin();
            } else {
                try {
                    int tmp = Integer.parseInt(edit.getText().toString());
                    if (tmp > 0) {
                        tmp = -tmp;
                        edit.setText("" + tmp);
                    }
                    playersList.get(i).setTurnScore(rowIndex, tmp);
                    scoreText.setText("/" + playersList.get(i).getTurnTotal(rowIndex - 1));
                    sum += -tmp;
                } catch (NumberFormatException e) {
                }
            }

            if (checkDobon.isChecked()) {
                playersList.get(i).addDobon();
            }
        }

        Log.d(RowBuilder.TAG, "calRow_1");

        if (winPlayers.size() == 0) {
            return;
        } else {
            winScore = sum / winPlayers.size();
            for (int i : winPlayers) {
                int linearId = ChartCellsManager.getLinearIdAt(i);
                LinearLayout linear = row.findViewById(linearId);
                TextView scoreText = linear.findViewById(ChartCellsManager.getScoreTextId());
                EditText edit = linear.findViewById(ChartCellsManager.getEditScoreId());
                playersList.get(i).setTurnScore(rowIndex, winScore);
                edit.setText("" + winScore);
                scoreText.setText("/" + playersList.get(i).getTurnTotal(rowIndex - 1));

            }

            Log.d(RowBuilder.TAG, "calRow_2");
        }
    }

    public int calculate() {

        Log.d(RowBuilder.TAG, "calculate Start");

        int checkRow = rowCnt;

        for (int i = 0; i < playerNo; ++i) {
            playersList.get(i).initValues();
            playersList.get(i).initTurnScore();
        }

        Log.d(RowBuilder.TAG, "calculate1");

        for (int i = 0; i < rowCnt; ++i) {
            TableRow row = chart.findViewById(ChartCellsManager.getTableRowIdAt(i));
            CheckBox rowFocusable = row.findViewById(ChartCellsManager.getFocusableCheck());
            if (!rowFocusable.isChecked()) {
                checkRow = i;
                break;
            }
            this.calRow(i + 1);
        }

        Log.d(RowBuilder.TAG, "calculate2");

        for (int i = 0; i < playerNo; ++i) {
            TextView totalText = chart.findViewById(ChartCellsManager.getTotalIdAt(i));
            PlayerScores player = playersList.get(i);
            totalText.setText("Total　" + player.getTotal() + "\nあがり　" + player.getWinCnt() + "\nドボン　" + player.getDobonCnt());
        }

        Log.d(RowBuilder.TAG, "calculate3");

        return checkRow;
    }

    @Override
    public String toString() {
        return "ChartCalculator{" +
                "chart=" + chart +
                ", rowCnt=" + rowCnt +
                ", playerNo=" + playerNo +
                ", playersList=" + playersList +
                '}';
    }
}

