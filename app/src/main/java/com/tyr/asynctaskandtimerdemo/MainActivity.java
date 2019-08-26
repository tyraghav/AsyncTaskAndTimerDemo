package com.tyr.asynctaskandtimerdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import javax.net.ssl.HttpsURLConnection;

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
    EditText cityNames;
    int[] buttonColors = new int[6];
    int maxTime;
    int firstNum = 0;
    int MagicNumber = 6;
    Random rn = new Random(6);
    boolean isCounterActive = false;
    Bitmap Success;
    Bitmap Failure;
    Bitmap OnLoad;
    Bitmap Bomb;
    CountDownTimer gameTimer;

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
        cityNames = (EditText) findViewById(R.id.cityNames);
        optionsButtonViewSetter(1);
        maxTime = 30;
        timerTextViewSetter(maxTime);
        timerSetSeekBar.setMax(600);
        timerSetSeekBar.setProgress(30);
        timerSetSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                timerTextViewSetter(i);
                maxTime = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        loggerListView = (ListView)findViewById(R.id.loggerListView);
        loggerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String CitySelected = loggerListView.getItemAtPosition(i).toString();
                Log.i("Check City", "AAAA"+CitySelected);
                ClimateUpdates climate = new ClimateUpdates();
                climate.execute(new String[]{CitySelected, Integer.toString(firstNum)});
            }
        });
        loggerListView.setVisibility(View.INVISIBLE);
        getImage("ONLOAD");
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
        if(maxTime!=0) {
            getImage("BOMB");
            startButton.setVisibility(View.INVISIBLE);
            timerSetSeekBar.setVisibility(View.INVISIBLE);
            String presentTime = (String) timerTextView.getText();
            ClimateUpdates climate;
            String cNames = cityNames.getText().toString();
            cityNames.setVisibility(View.INVISIBLE);
            loggerListView.setVisibility(View.VISIBLE);
            ArrayList<String> cNm = new ArrayList<String>(Arrays.asList(cNames.split(",")));
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cNm);
            loggerListView.setAdapter(aa);
            gameTimer = new CountDownTimer(maxTime * 1000 + 70, 1000) {

                @Override
                public void onTick(long milliSeconds) {
                    MagicNumber=rn.nextInt(6);
                    timerTextViewSetter(firstNum);
                    if (firstNum != 0 && firstNum <= maxTime - 5) {
                        optionsButtonViewSetter(firstNum);
                    }
                    firstNum++;

                }

                @Override
                public void onFinish() {
                    Toast.makeText(getApplicationContext(),"FAILURE", Toast.LENGTH_SHORT).show();
                    stopTimer(0);
                }
            };
            gameTimer.start();
            isCounterActive = true;
        }else{
            isCounterActive = false;
            Toast.makeText(getApplicationContext(), "Please select valid number", Toast.LENGTH_SHORT).show();
        }
    }

    void stopTimer(int presentTime){
        if(presentTime==0) {
            getImage("FAILURE");
            gameTimer.cancel();
        }else{
            getImage("SUCCESS");
            gameTimer.cancel();;

        }
        startButton.setVisibility(View.VISIBLE);
        timerSetSeekBar.setVisibility(View.VISIBLE);
        loggerListView.setVisibility(View.INVISIBLE);
        cityNames.setVisibility(View.VISIBLE);
        isCounterActive = false;
        timerTextView.setText("00 : 00");
        maxTime = 0;
        firstNum = 0;
        timerSetSeekBar.setProgress(0);
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

    void getImage(String TypeImage){
        try {
            if(TypeImage.equalsIgnoreCase("SUCCESS")){
                if(Success==null){
                    ImageDownloader imageService = new ImageDownloader();
                    Log.i("IN GET IMAGE","SUCCESS");
                    Success = imageService.execute("").get();
                }
                changeImageView.setImageBitmap(Success);
            } else if(TypeImage.equalsIgnoreCase("FAILURE")){
                if(Failure==null){
                    ImageDownloader imageService = new ImageDownloader();
                    Log.i("IN GET IMAGE","FAILURE");
                    Failure = imageService.execute("").get();
                }
                changeImageView.setImageBitmap(Failure);
            } else if(TypeImage.equalsIgnoreCase("BOMB")){
                if(Bomb==null){
                    ImageDownloader imageService = new ImageDownloader();
                    Log.i("IN GET IMAGE","BOMB");
                    Bomb = imageService.execute("").get();
                }
                changeImageView.setImageBitmap(Bomb);
            } else {
                if(OnLoad==null){
                    ImageDownloader imageService = new ImageDownloader();
                    Log.i("IN GET IMAGE","ONLOAD");
                    OnLoad = imageService.execute("").get();
                }
                changeImageView.setImageBitmap(OnLoad);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    void onOptionClicked(View view){
        Button OptionButton = (Button)view;
        Log.i("IN CLICK",Integer.toString(MagicNumber));
        if(Integer.toString(MagicNumber).equalsIgnoreCase(OptionButton.getTag().toString())){
            stopTimer(1);
            Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
        };
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

    class ImageDownloader extends  AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream is;
                if(strings[0].substring(0,5).equalsIgnoreCase("https")){
                    HttpsURLConnection con=(HttpsURLConnection)url.openConnection();
                    con.connect();
                    is=con.getInputStream();
                    Log.i("HII","HTTPS");

                } else {
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.connect();
                    is=con.getInputStream();
                    Log.i("HII","HTTP");
                }
                Bitmap myBitmMap = BitmapFactory.decodeStream(is);
                if(myBitmMap==null){
                    Log.i("HII","NULL");
                }
                return myBitmMap;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    class ClimateUpdates extends AsyncTask<String , Void , String >{

        @Override
        protected String doInBackground(String... strings) {
            try {
                String Location = strings[0];
                URL url = new URL( "http://api.openweathermap.org/data/2.5/weather?q="+Location+"&APPID=0a18c94e863e332e5cbcf4abec48fb66");
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                InputStream is=con.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                String Result="";
                int data = 0;
                while(data!=-1){
                    data = isr.read();
                    char curr = (char)data;
                    Result+=curr;
                }
                JSONObject jObj = new JSONObject(Result);
                JSONArray Weather = (JSONArray)jObj.get("weather");
                JSONObject main = (JSONObject)jObj.get("main");
                Double temp = (Double)main.get("temp");
                JSONObject lWeather = Weather.getJSONObject(0);
                Result = new String(strings[1]+" : "+ Location +'\n' + lWeather.getString("main") + " : " + lWeather.getString("description") + "\n" + "Temperature : " + temp.toString() + 'F');
                return Result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "ERROR OCCURED", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
