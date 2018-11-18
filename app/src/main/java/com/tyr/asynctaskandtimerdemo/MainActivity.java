package com.tyr.asynctaskandtimerdemo;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;

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
    int maxTime = 0;
    Random randomSix;
    boolean isCounterActive = false;

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
        timerSetSeekBar = (SeekBar)findViewById(R.id.timerSetSeekBar);
        loggerListView = (ListView)findViewById(R.id.loggerListView);
        optionsButtonViewSetter(1);
        timerTextViewSetter(maxTime);
        timerSetSeekBar.setMax(600);
        timerSetSeekBar.setProgress(30);
        maxTime = 30;
        timerSetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timerTextViewSetter(i);
                maxTime=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        /*final Handler handler = new Handler();
        Runnable run=new Runnable() {
            int i=0;
            @Override
            public void run() {
                i++;
                Toast.makeText(getApplicationContext(), Integer.toString(i), Toast.LENGTH_SHORT).show();
                handler.postDelayed(this,1000);
            }
        };
        run.run();*/
    }

    void startButtonClicked(View view){
        startButton.setVisibility(View.INVISIBLE);
        String presentTime = (String)timerTextView.getText();

        CountDownTimer gameTimer = new CountDownTimer(maxTime*1000+70,1000) {
            int firstNum = 0;

            @Override
            public void onTick(long milliSeconds) {
                //Toast.makeText(getApplicationContext(), Long.toString(oneSecond), Toast.LENGTH_SHORT).show();
                timerTextViewSetter(firstNum);
                if(firstNum!=0 && firstNum<=maxTime-5){
                    optionsButtonViewSetter(firstNum);
                }
                firstNum++;
            }

            @Override
            public void onFinish() {
                isCounterActive = false;
                timerTextView.setText("00 : 00");
            }
        };
        gameTimer.start();
        isCounterActive = true;
    }
    void optionsButtonViewSetter(int firstNum){
        optionButton1.setText(Integer.toString(firstNum));
        optionButton1.setBackgroundResource(buttonColors[(firstNum)%6]);
        optionButton2.setText(Integer.toString(firstNum+1));
        optionButton2.setBackgroundResource(buttonColors[(firstNum+1)%6]);
        optionButton3.setText(Integer.toString(firstNum+2));
        optionButton3.setBackgroundResource(buttonColors[(firstNum+2)%6]);
        optionButton4.setText(Integer.toString(firstNum+3));
        optionButton4.setBackgroundResource(buttonColors[(firstNum+3)%6]);
        optionButton5.setText(Integer.toString(firstNum+4));
        optionButton5.setBackgroundResource(buttonColors[(firstNum+4)%6]);
        optionButton6.setText(Integer.toString(firstNum+5));
        optionButton6.setBackgroundResource(buttonColors[(firstNum+5)%6]);
    }
    void timerTextViewSetter(int presentSecond){
        int sec=presentSecond;
        if(isCounterActive) {
            sec = maxTime - presentSecond;
        }
        int minutes = sec/60;
        int seconds = sec%60;
        String minute = Integer.toString(minutes);
        String second = Integer.toString(seconds);
        if(minutes<10){
            minute = "0" + minutes;
        }
        if(seconds<10){
            second = "0" + seconds;
        }
        timerTextView.setText(minute+" : "+second);
    }
}
