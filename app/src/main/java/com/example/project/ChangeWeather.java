package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ChangeWeather extends AppCompatActivity {
    String city_fromMain, temp_fromMain, hum_fromMain, icon_fromMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_weather);

        Spinner newLocation = (Spinner) findViewById(R.id.spinner);
        Button changeLocation = (Button) findViewById(R.id.bttn_ChangeLocation);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        city_fromMain = sp.getString("city1","");
        temp_fromMain = sp.getString("temp1","00");
        hum_fromMain = sp.getString("hum1","00");
        icon_fromMain = sp.getString("icon1","00");

        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                String selectedLocation = newLocation.getSelectedItem().toString();
                editor.putString("city2", selectedLocation);
                editor.commit();
                startActivity(new Intent(ChangeWeather.this, MainActivity.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.weather, menu);
        if(icon_fromMain.equals(" clear")){
            menu.getItem(0).setIcon(R.drawable.sun);
        }
        if(icon_fromMain.equals("wind")){
            menu.getItem(0).setIcon(R.drawable.wind);
        }
        if(icon_fromMain.equals("clouds")){
            menu.getItem(0).setIcon(R.drawable.clouds);
        }
        if(icon_fromMain.equals("rain")){
            menu.getItem(0).setIcon(R.drawable.rain);
        }
        if(icon_fromMain.equals("snow")){
            menu.getItem(0).setIcon(R.drawable.snow);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch(item.getItemId()){
            case R.id.weather_menu:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Weather Summary in " + city_fromMain);
                builder.setMessage("Temperature "+ temp_fromMain +"°C\nHumidity "+hum_fromMain+"%");
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}