package com.example.pageoneculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity   {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(RowBuilder.TAG, "MainStart");
        ChartCellsManager.setContext(this);
        HistoryManager.readHistory(this);
    }


    public void btn_onClickNew (View view ) {

        Log.d(RowBuilder.TAG, "Start_NewClick");

        DialogFragment dialog = new MainDialog();
        dialog.setArguments(new Bundle());
        Bundle arg = new Bundle();

        arg.putIntArray("colorTheme", ColorManager.getTheme(this));
        dialog.setArguments(arg);
        dialog.show(this.getSupportFragmentManager(), "main_dialog");

    }

    public void btn_onSetting (View view ) {
        Intent i = new Intent(this, com.example.pageoneculator.MyConfigActivity.class);
        startActivity(i);
    }

    public void btn_onClear ( View view ) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear().commit();
        Toast.makeText(this, "設定をクリアしました", Toast.LENGTH_SHORT).show();
    }

    public void btn_onHistory ( View view ) {
        Intent intent = new Intent( this, com.example.pageoneculator.HistoryActivity.class);
        startActivity(intent);
    }

    public void btn_onResult ( View view ) {
        DialogFragment dialog = new ResultFragment();
        dialog.show(getSupportFragmentManager(), "result_fragment");
    }
}
