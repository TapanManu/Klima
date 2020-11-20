package com.tman.klima;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Fetch {
    private static final String OPEN_WEATHER_MAP_API =
            "https://api.openweathermap.org/data/2.5/weather?q=%s&APPID=2bf6f5a1e70425ac39984485bbd585bb";

    public static JSONObject getJSON(Context context, String city){
        try {

            URL url = new URL(String.format(OPEN_WEATHER_MAP_API, city));
            Log.d("success1","hi");
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();
            Log.d("success2","hi");
            connection.addRequestProperty("x-api-key",
                    "2bf6f5a1e70425ac39984485bbd585bb");
            Log.d("success3",String.valueOf(connection));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            Log.d("success4","hi");
            StringBuilder json = new StringBuilder(1024);
            String tmp;
            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            Log.d("data:","hi");
            return null;
        }
    }
}
