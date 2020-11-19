package com.tman.klima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Klima is the german term for climate
    TextView tempvalue,status;
    ImageView weather_icon;
    ConstraintLayout weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempvalue = findViewById(R.id.temp);
        tempvalue.setText("28");
        status = findViewById(R.id.tempstatus);
        status.setText(R.string.status_update);
        weather_icon = findViewById(R.id.ivimage);
        weather = findViewById(R.id.WeatherUI);
        weather.setBackgroundColor(Color.BLUE);                     //default app color
        climate_change(Integer.parseInt(tempvalue.getText().toString().trim()));
        //filter out degree celsius or fahrenheit before passing
    }

    public void climate_change(int temp){
            if(temp<0){
                weather.setBackgroundColor(Color.rgb(73, 104, 115));
                status.setText(R.string.frozen);
            }
            else if(temp>0 && temp<=25){
                weather.setBackgroundColor(Color.rgb(135, 194, 214));
                status.setText(R.string.cool);
            }
            else if(25<temp && temp<=35){
                weather.setBackgroundColor(Color.rgb(255, 255, 120));
                status.setText(R.string.moderate);
                status.setTextColor(Color.rgb(96, 99, 84));
                tempvalue.setTextColor(Color.rgb(96, 99, 84));
            }
            else if(temp>35){
                weather.setBackgroundColor(Color.rgb(250, 189, 172));
                status.setText(R.string.extreme_hot);
            }
    }
}