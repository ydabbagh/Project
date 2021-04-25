package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView weatherSummary;
    ImageView weatherPicture;
    String city, icon;
    double temp, humidity;
    String weatherWebserviceURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        weatherSummary = (TextView) findViewById(R.id.weatherSummary);
        weatherPicture = (ImageView) findViewById(R.id.imageView);

        String newLoc = sp.getString("city2","athens");
        weatherWebserviceURL = "http://api.openweathermap.org/data/2.5/weather?q="+newLoc+"&appid=b9cad6969aeb831771bcf505cb5a5321&units=metric";
        weather(weatherWebserviceURL);

        Button firebaseDatabase = (Button) findViewById(R.id.bttn_FirebaseDatabase);
        Button firebaseDatabaseData = (Button) findViewById(R.id.bttn_FirebaseDatabaseData);
        Button localDatabase = (Button) findViewById(R.id.bttn_LocalDatabase);
        Button localDatabaseData = (Button) findViewById(R.id.bttn_LocalDatabaseData);
        Button changeWeather = (Button) findViewById(R.id.bttn_ChangeWeather);

        firebaseDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("city1", city);
                editor.putString("temp1",String.valueOf(temp));
                editor.putString("hum1",String.valueOf(humidity));
                editor.putString("icon1",icon);
                editor.commit();
                startActivity(new Intent(MainActivity.this, FirebaseDatabaseEdit.class));
            }
        });

        firebaseDatabaseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("city1", city);
                editor.putString("temp1",String.valueOf(temp));
                editor.putString("hum1",String.valueOf(humidity));
                editor.putString("icon1",icon);
                editor.commit();
                startActivity(new Intent(MainActivity.this, FirebaseDatabaseData.class));
            }
        });

        localDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("city1", city);
                editor.putString("temp1",String.valueOf(temp));
                editor.putString("hum1",String.valueOf(humidity));
                editor.putString("icon1",icon);
                editor.commit();
                startActivity(new Intent(MainActivity.this, LocalDatabase.class));
            }
        });

        localDatabaseData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("city1", city);
                editor.putString("temp1",String.valueOf(temp));
                editor.putString("hum1",String.valueOf(humidity));
                editor.putString("icon1",icon);
                editor.commit();
                startActivity(new Intent(MainActivity.this, LocalDatabaseData.class));
            }
        });

        changeWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("city1", city);
                editor.putString("temp1",String.valueOf(temp));
                editor.putString("hum1",String.valueOf(humidity));
                editor.putString("icon1",icon);
                editor.commit();
                startActivity(new Intent(MainActivity.this, ChangeWeather.class));
            }
        });
    }

    public void weather(String url) {
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("Yasmeen", response.toString());
                try {
                    city = response.getString("name");
                    JSONObject jsonmain = response.getJSONObject("main");
                    temp = jsonmain.getDouble("temp");
                    humidity = jsonmain.getDouble("humidity");
                    weatherSummary.setText(city + " | Temperature " + String.valueOf(temp) + "°C | Humidity " + String.valueOf(humidity) + "%");

                    JSONArray weatherArray = response.getJSONArray("weather");
                    chooseBackground(weatherArray);
                    icon = condition(weatherArray);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Yasmeen", "error: " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Yasmeen", error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }

    public void chooseBackground(JSONArray jArray) {
        for (int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                Log.d("Yasmeen", "jArray(i): " + oneObject.toString());

                String weatherCondition = oneObject.getString("main");
                Log.d("Yasmeen", "weather condition: " + weatherCondition);

                if (weatherCondition.equals("Clear")) {
                    weatherPicture.setImageResource(R.drawable.sun);
                } else if (weatherCondition.equals("Clouds")) {
                    weatherPicture.setImageResource(R.drawable.clouds);
                } else if (weatherCondition.equals("Rain") || weatherCondition.equals("Thunderstorm") || weatherCondition.equals("Drizzle")) {
                    weatherPicture.setImageResource(R.drawable.rain);
                } else if (weatherCondition.equals("Snow")) {
                    weatherPicture.setImageResource(R.drawable.snow);
                } else {
                    weatherPicture.setImageResource(R.drawable.wind);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Yasmeen", "jArray: " + e.toString());
            }
        }
    }

    public String condition(JSONArray jArray) {
        String condition ="";
        for (int i = 0; i < jArray.length(); i++) {

            try {
                JSONObject oneObject = jArray.getJSONObject(i);

                Log.d("Yasmeen", "jArray(i): " + oneObject.toString());

                String weatherCondition = oneObject.getString("main");
                Log.d("Yasmeen", "weather condition: " + weatherCondition);

                if (weatherCondition.equals("Clear")) {
                    condition = "clear";
                } else if (weatherCondition.equals("Clouds")) {
                    condition = "clouds";
                } else if (weatherCondition.equals("Rain") || weatherCondition.equals("Thunderstorm") || weatherCondition.equals("Drizzle")) {
                    condition = "rain";
                } else if (weatherCondition.equals("Snow")) {
                    condition = "snow";
                } else {
                    condition = "wind";
                }
            } catch (Exception e){
                e.printStackTrace();
                Log.d("Yasmeen", "jArray: " + e.toString());
            }
        }
        return condition;
    }
}