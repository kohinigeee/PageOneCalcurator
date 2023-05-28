package com.example.pageoneculator;

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

public class FavoriteActivity extends AppCompatActivity {

    private List<ChartValues> list;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Log.d(RowBuilder.TAG, "FavoriteActivity Start");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onResume() {
        super.onResume();
        update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        list = HistoryManager.favoriteToList().stream().sorted(Comparator.comparing(ChartValues::getDate).reversed()).collect(Collectors.toList());

        ListView listView = findViewById(R.id.listView);
        ReserveChartAdapter adapter = new ReserveChartAdapter(this, list, R.layout.layout_favorite_list);
        listView.setAdapter(adapter);
        if (list.size() <= 0) {

            TextView title = findViewById(R.id.textTitle);
            title.setText(R.string.favoriteActivityErrorTitle);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void btn_onUpdateFavorite(View view) {
        update();
    }

    public void btn_onBack( View view ) {
        finish();
    }

}
