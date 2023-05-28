package com.example.pageoneculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReserveChartAdapter extends BaseAdapter {

    private Context context;
    private List<ChartValues> data;
    private int resource;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public ReserveChartAdapter(Context context, List<ChartValues> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i).getGameID();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChartValues item = data.get(position);
        Activity activity = (Activity) context;
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("y/MM/dd(eee) HH:mm");
        StringBuilder namesBuilder = new StringBuilder("");
        StringBuilder scoresBuilder = new StringBuilder("");

        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(resource, null);
        }

        ((TextView) convertView.findViewById(R.id.gameName)).setText(item.getGameName());
        ((TextView) convertView.findViewById(R.id.textDate)).setText("作成日: " + (item.getDate().format(fmt)));
        ((TextView) convertView.findViewById(R.id.textPlayerNo)).setText(String.valueOf(item.getPlayerNo()));
        ((TextView) convertView.findViewById(R.id.textTurnNo)).setText(String.valueOf(item.getRowCnt()));

        ((ImageButton) convertView.findViewById(R.id.openBtn)).setOnClickListener(view -> {

            Intent intent = new Intent((Activity)context, com.example.pageoneculator.ContinueChartActivity.class);
            intent.putExtra(KeyStringsManager.keyChartValues.getKey(), item);
            if (resource == R.layout.layout_favorite_list) {
                intent.putExtra("position", position);
                intent.putExtra("mode", ChartActivity.FROM_FAVORITE);
            }

            data.remove(position);
            context.startActivity(intent);
        });


        for (int i = 0; i < item.getPlayerNo(); ++i) {
            namesBuilder.append(((item.getNames())[i]));
            namesBuilder.append(System.lineSeparator());
            scoresBuilder.append((item.getCal().getPlayersList().get(i).getTotal()) + "pt");
            scoresBuilder.append(System.lineSeparator());
        }

        ((TextView) convertView.findViewById(R.id.textTopPlayers)).setText(namesBuilder.substring(0, namesBuilder.length() - 1));
        ((TextView) convertView.findViewById(R.id.textTopScores)).setText(scoresBuilder.substring(0, scoresBuilder.length() - 1));

        switch (resource) {
            case R.layout.layout_chart_list:
                ((TextView) convertView.findViewById(R.id.gameName)).setText("RecentlyGame" + (position + 1));
                ((ImageButton) convertView.findViewById(R.id.favoriteBtn)).setOnClickListener((view) -> {
                    DialogFragment dialog = new FavoriteDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KeyStringsManager.keyChartValues.getKey(), item);
                    dialog.setArguments(bundle);
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "favoriteDialog");
                });
                break;
            case R.layout.layout_favorite_list:
                ((TextView) convertView.findViewById(R.id.gameName)).setText(item.getGameName());
                ((ImageButton) convertView.findViewById(R.id.imageTrash)).setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(KeyStringsManager.keyGameID.getKey(), item.getGameID());
                    DialogFragment dialog = new ConfirmDialog(item.getGameName() + " を削除します\nよろしいですか？", ConfirmDialog.MODE_TRASH_FAVORITE);
                    dialog.setArguments(bundle);
                    dialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "trash_favorite");
                });
                break;
        }
        return convertView;
    }


}
