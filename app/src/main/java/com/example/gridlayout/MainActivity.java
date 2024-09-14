package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int clock = 0;
    private static final int COLUMN_COUNT = 10;
    private static final int ROW_COUNT = 12;
    private boolean running = true;
    private Game game;
    private String clickState = "Pick";
    private int currFlags = 4;

    // save the TextViews of all cells in an array, so later on,
    // when a TextView is clicked, we know which cell it is
    private ArrayList<TextView> cell_tvs;
    private HashMap<TextView, int[]> tvMap;
    private HashMap<List<Integer>, TextView> coordMap;

    private int dpToPixel(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        game = new Game();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView bottomtv = (TextView) findViewById(R.id.bottom);
        bottomtv.setOnClickListener(this::onClickBottomTV);

        cell_tvs = new ArrayList<TextView>();
        tvMap = new HashMap<TextView, int[]>();
        coordMap = new HashMap<List<Integer>, TextView>();

        GridLayout grid = (GridLayout) findViewById(R.id.activity_main_gridLayout01);

        // Method (3): add four dynamically created cells with LayoutInflater
        LayoutInflater li = LayoutInflater.from(this);
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 10; j++) {
                TextView tv = (TextView) li.inflate(R.layout.custom_cell_layout, grid, false);
                tv.setTextColor(ContextCompat.getColor(this, R.color.dark_green));
                tv.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_green));
                tv.setOnClickListener(this::onClickTV);
                tv.setText(String.valueOf(game.bombFreqs[i][j]));
                GridLayout.LayoutParams lp = (GridLayout.LayoutParams) tv.getLayoutParams();
                lp.rowSpec = GridLayout.spec(i);
                lp.columnSpec = GridLayout.spec(j);

                grid.addView(tv, lp);

                cell_tvs.add(tv);
                tvMap.put(tv, new int[]{i, j});
                coordMap.put(Arrays.asList(i, j), tv);
            }
        }

        if (savedInstanceState != null) {
            clock = savedInstanceState.getInt("clock");
            running = savedInstanceState.getBoolean("running");
        }
        runTimer();




    }

    public void onClickBottomTV(View view){
        TextView bottomTextView = (TextView) view;
        if (clickState == "Pick"){
            clickState = "Flag";
            bottomTextView.setText(getString(R.string.flag));
        }
        else{
            clickState = "Pick";
            bottomTextView.setText(getString(R.string.pick));
        }
    }

    public void onClickTV(View view){
        TextView tv = (TextView) view;
        int[] data = tvMap.get(tv);
        if (clickState == "Flag"){
            int[] location = tvMap.get(tv);
            String currentText = tv.getText().toString();
            if (currentText.equals(getString(R.string.flag))) {
                tv.setText(" ");
                if (game.revealed[location[0]][location[1]]){
                    int bombFrequency = game.bombFreqs[location[0]][location[1]];
                    tv.setText(String.valueOf(bombFrequency));
                }
                currFlags += 1;
                TextView numView = (TextView) findViewById(R.id.textView01);
                numView.setText(String.valueOf(currFlags));
            }
            else if (game.revealed[location[0]][location[1]]){
                return;
            }
            else {
                if (currFlags == 0){
                    return;
                }
                tv.setText(getString(R.string.flag));
                currFlags -= 1;
                TextView numView = (TextView) findViewById(R.id.textView01);
                numView.setText(String.valueOf(currFlags));
            }
        }

        else {
            if (game.bombs[data[0]][data[1]]) {
                //end game
            }
            else {
                game.reveal(data[0], data[1], coordMap);
            }
        }
    }
    public void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.clock);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                timeView.setText(String.valueOf(clock));
                if (running) {
                    clock++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}