package com.tyr.asynctaskandtimerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView changeImageView;
    TextView timerTextView;
    Button startButton;
    Button optionButton1;
    Button optionButton2;
    Button optionButton3;
    Button optionButton4;
    Button optionButton5;
    Button optionButton6;
    SeekBar timerSetSeekBar;
    ListView loggerListView;
    int[] buttonColors = new int[6];
    //{ "" , "" , "" , "" , "" , "#"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeImageView = (ImageView)findViewById(R.id.changeImageView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        startButton = (Button)findViewById(R.id.startButton);
        optionButton1 = (Button)findViewById(R.id.optionButton1);
        optionButton2 = (Button)findViewById(R.id.optionButton2);
        optionButton3 = (Button)findViewById(R.id.optionButton3);
        optionButton4 = (Button)findViewById(R.id.optionButton4);
        optionButton5 = (Button)findViewById(R.id.optionButton5);
        optionButton6 = (Button)findViewById(R.id.optionButton6);
        buttonColors[0]=R.color.buttonColor1;
        buttonColors[1]=R.color.buttonColor2;
        buttonColors[2]=R.color.buttonColor3;
        buttonColors[3]=R.color.buttonColor4;
        buttonColors[4]=R.color.buttonColor5;
        buttonColors[5]=R.color.buttonColor6;
        optionButton1.setBackgroundResource(buttonColors[0]);
        optionButton2.setBackgroundResource(buttonColors[1]);
        optionButton3.setBackgroundResource(buttonColors[2]);
        optionButton4.setBackgroundResource(buttonColors[3]);
        optionButton5.setBackgroundResource(buttonColors[4]);
        optionButton6.setBackgroundResource(buttonColors[5]);
        timerSetSeekBar = (SeekBar)findViewById(R.id.timerSetSeekBar);
        loggerListView = (ListView)findViewById(R.id.loggerListView);
    }
}
