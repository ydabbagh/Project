package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LocalDatabaseData extends AppCompatActivity {
    String city_fromMain, temp_fromMain, hum_fromMain, icon_fromMain;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    final DatabaseHelper database = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database_data);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        city_fromMain = sp.getString("city1","");
        temp_fromMain = sp.getString("temp1","00");
        hum_fromMain = sp.getString("hum1","00");
        icon_fromMain = sp.getString("icon1","00");

        final DatabaseHelper database = new DatabaseHelper(this);
        listView=(ListView)findViewById(R.id.localdatabaselistviewtxt);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);

        Cursor cursor = database.viewUsers();
        while(cursor.moveToNext()){
            String x = "\nID: " + cursor.getString(0) + " \n" +
            "Name: " + cursor.getString(1) + " \n"+
            "Surname: " + cursor.getString(2) + " \n"+
            "Phone: " + cursor.getString(3) + " \n"+
            "Personal ID: " + cursor.getString(4) +"\n";
            arrayList.add(x);
        }
        Log.d("Yasmeen", "List Shown");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = arrayAdapter.getItem(position);
                String [] result = s.split(" ",-1);
                Toast.makeText(LocalDatabaseData.this, result[3] + " " + result[5], Toast.LENGTH_SHORT).show();
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