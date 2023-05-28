package com.example.pageoneculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ScoreCardFragment extends DialogFragment {

    private int scoreSum = 0;
    private static int[] editIds = {R.id.editNumber1, R.id.editNumber2, R.id.editNumber3,R.id.editNumber4, R.id.editNumber5,
            R.id.editNumber6, R.id.editNumber7, R.id.editNumber8, R.id.editNumber9, R.id.editNumber10};
    LinearLayout layout;
    private AlertDialog dialog = null;
    public String TAG = "LOG_VER1";

    private static EditText findEditFromRowId ( Activity activity, Bundle bundle ) {
        return (activity.findViewById(bundle.getInt("tableRowId"))).findViewById(bundle.getInt("layoutId")).findViewById(bundle.getInt("editId"));
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Log.d(TAG, "createCardDialog");

        layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_score_card_mode, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Button btn = layout.findViewById(R.id.ScoreOkButton);
        Bundle bundle = requireArguments();

        Log.d(TAG, "Log2_1");

        btn.setOnClickListener((view)->{
            for ( int i = 0; i < 10; ++i ) {
                EditText editText = layout.findViewById(editIds[i]);
                int num;
                try {
                    num = Integer.parseInt(editText.getText().toString());
                } catch ( NumberFormatException e ) {
                    num = 0;
                }
                if ( num < 0 ) num = 0;
                if ( i+1 == 2 ) {
                    scoreSum += num*(20);
                } else if ( i+1 == 8 ) {
                    scoreSum += num*(50);
                } else
                    scoreSum += num*(i+1);
            }

            scoreSum = ( scoreSum%10 < 5 ) ? 10*(scoreSum/10) : 10*(scoreSum/10+1);
            CheckBox ch = layout.findViewById(R.id.isDb);
            if( ch.isChecked())
                scoreSum *= 2;

            Bundle arg = getArguments();
            EditText editText = findEditFromRowId(getActivity(), arg);

            Log.d(TAG,"Log2_2");

            editText.setText(String.valueOf(scoreSum));
            Log.d(TAG, "Log2_3");
            dialog.dismiss();

            Log.d(TAG, "Log2_4");
        });


       dialog = builder.setTitle("スコア入力")
                .setView(layout)
                .create();

       return dialog;
    }


}
