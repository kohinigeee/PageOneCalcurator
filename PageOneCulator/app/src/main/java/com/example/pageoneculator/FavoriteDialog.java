package com.example.pageoneculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

public class FavoriteDialog extends DialogFragment {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog onCreateDialog(Bundle savedInstanceState ) {
        LinearLayout layout = (LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.dialog_favorite,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        Bundle bundle = getArguments();
        ChartValues item = (ChartValues) bundle.getSerializable(KeyStringsManager.keyChartValues.getKey());
        EditText edit = layout.findViewById(R.id.editGameName);

        return builder.setTitle("お気に入り登録")
                .setMessage("保存するゲーム名を入力してください")
                .setView(layout)
                .setIcon(R.drawable.icon_star_32_yellow)
                .setPositiveButton("保存", (dialog, which)->{
                    String name = edit.getText().toString();
                    if ( name.equals(""))
                        name = AppValuesManager.getDefaultGameName();
                    item.setGameName(name);
                    HistoryManager.toFavoriteFromHistory(item.getGameID(), getActivity());
                    ((HistoryActivity)getActivity()).update();
                    Toast.makeText(getActivity(), item.getGameName()+"を保存しました", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("キャンセル", (dialog, which)->{ })
                .create();
    }
}
