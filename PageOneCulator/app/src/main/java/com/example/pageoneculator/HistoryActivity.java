package com.example.pageoneculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryActivity extends AppCompatActivity {

    private List<ChartValues> list;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Log.d(RowBuilder.TAG, "HistoryActivity Start");

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        list = HistoryManager.historyToList().stream().sorted(Comparator.comparing(ChartValues::getDate).reversed()).collect(Collectors.toList());
        ListView listView = findViewById(R.id.listView);
        ReserveChartAdapter adapter = new ReserveChartAdapter(this, list, R.layout.layout_chart_list);
        listView.setAdapter(adapter);
        if (list.size() <= 0) {

            TextView title = findViewById(R.id.textTitle);
            title.setText(R.string.historyActivityErrorTitle);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clear() {
        HistoryManager.clearHistory(this);
        update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void btn_onUpdateHistory(View view) {
        update();
    }

    public void btn_onClearHistory(View view) {
        DialogFragment dialog = new ConfirmDialog("履歴を消去します、よろしいですか？", ConfirmDialog.MODE_CLEAR_HISTORY);
        dialog.show(getSupportFragmentManager(), "clear_confirm");
    }

    public void btn_onFavoriteHistory(View view) {

        Intent intent = new Intent(this, com.example.pageoneculator.FavoriteActivity.class);
        startActivity(intent);
    }

}
