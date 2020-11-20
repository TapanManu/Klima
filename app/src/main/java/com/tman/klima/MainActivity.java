package com.tman.klima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Klima is the german term for climate
    TextView tempvalue,status,description,city,updated;
    ImageView weather_icon;
    ConstraintLayout weather;
    JSONObject data;
    Handler handler;
    String cityS="Kottayam";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempvalue = findViewById(R.id.temp);
        city = findViewById(R.id.city);
        description = findViewById(R.id.Description);
        updated = findViewById(R.id.updated);
        status = findViewById(R.id.tempstatus);
        status.setText(R.string.status_update);
        weather_icon = findViewById(R.id.ivimage);
        weather = findViewById(R.id.WeatherUI);
        weather.setBackgroundColor(Color.BLUE);                     //default app color
        updateWeatherData(cityS);

        //filter out degree celsius or fahrenheit before passing
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWeatherData(cityS);
    }

    private void updateWeatherData(final String city){
        handler = new Handler();
        new Thread(){
            public void run(){
                Log.d("first","first");
                final JSONObject json = Fetch.getJSON(getApplicationContext(), city);
                if(json == null){
                    handler.post(new Runnable(){
                        public void run(){
                            Toast.makeText(getApplicationContext(),
                                    "No place to find",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable(){
                        public void run(){
                            renderWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void renderWeather(JSONObject json){
        try {
            city.setText(String.format("%s, %s", json.getString("name").toUpperCase(Locale.US), json.getJSONObject("sys").getString("country")));

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            description.setText(
                    String.format("%s\nHumidity: %s%%\nPressure: %s hPa", details.getString("description").toUpperCase(Locale.US), main.getString("humidity"), main.getString("pressure")));
            double temp = main.getDouble(getString(R.string.temperature));
            temp = temp - 273.15;
            climate_change(temp);
            tempvalue.setText(
                    String.format("%s ℃", String.format("%.2f", temp)));

            DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updated.setText(String.format("Last update: %s", updatedOn));

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    public void climate_change(double temp){
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
                description.setTextColor(Color.rgb(96, 99, 84));
                city.setTextColor(Color.rgb(96, 99, 84));
                updated.setTextColor(Color.rgb(96, 99, 84));
            }
            else if(temp>35){
                weather.setBackgroundColor(Color.rgb(250, 189, 172));
                status.setText(R.string.extreme_hot);
            }
    }
}