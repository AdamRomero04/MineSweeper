package com.example.gridlayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        boolean savedResult = getIntent().getBooleanExtra("outcome", false);
        int time = getIntent().getIntExtra("time", 0);

        TextView buttonTV = (TextView) findViewById(R.id.restart);
        buttonTV.setOnClickListener(this::onClickTV);

        TextView clockStatement = (TextView) findViewById(R.id.time);
        clockStatement.setText("Used " + time + " seconds");

        TextView resultStatement = (TextView) findViewById(R.id.result);
        if(savedResult){
            resultStatement.setText("You Won!");
        }
        else{
            resultStatement.setText("You Lost.");
        }
    }

    private void onClickTV(View view){
        Intent intent = new Intent(Result.this, MainActivity.class);
        startActivity(intent);
    }

}
