package com.example.pageoneculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

public class ResultFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.layout_result_fragment, null);
        TableLayout rankTable = layout.findViewById(R.id.rankTable);
        int whiteID = getResources().getColor(R.color.white);
        float textSize = 23.5f;

        TableRow rankRow = new TableRow(getActivity());
        LinearLayout.LayoutParams rankRowParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.MATCH_PARENT);
        rankRow.setLayoutParams(rankRowParam);
        rankRow.setBackgroundColor(whiteID);
        rankRow.setPadding(0,10,0,0);

        TableRow.LayoutParams cellLayout = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1);

        TableRow.LayoutParams cellLayout2 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);

        ImageView rank = new ImageView(getActivity());
        rank.setLayoutParams(cellLayout2);
        rank.setImageResource(R.drawable.icon_crown_color_32);

        TextView name = new TextView(getActivity());
        name.setLayoutParams(cellLayout);
        name.setTextSize(textSize);
        name.setText("Player1");

        TextView score = new TextView(getActivity());
        TableRow.LayoutParams scoreParam = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        score.setLayoutParams(cellLayout2);
        score.setText("200pt");
        score.setTextSize(textSize);

        ImageView win = new ImageView(getActivity());
        win.setLayoutParams(cellLayout);
        win.setImageResource(R.drawable.icon_small);

        TextView winNo = new TextView(getActivity());
        winNo.setLayoutParams(cellLayout);
        winNo.setText("0");
        winNo.setTextSize(textSize);
        winNo.setGravity(Gravity.CENTER);

        ImageView dobon = new ImageView(getActivity());
        dobon.setLayoutParams(cellLayout);
        dobon.setImageResource(R.drawable.icon_scul_32);

        TextView dobonNo = new TextView(getActivity());
        dobonNo.setLayoutParams(cellLayout);
        dobonNo.setText("0");
        dobonNo.setTextSize(textSize);
//        dobonNo.setGravity(Gravity.CENTER);

        rankRow.addView(rank);
        rankRow.addView(name);
        rankRow.addView(score);
        rankRow.addView(win);
        rankRow.addView(winNo);
        rankRow.addView(dobon);
        rankRow.addView(dobonNo);

        rankTable.addView(rankRow);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.setView(layout)
                .create();
    }
}
