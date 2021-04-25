package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class FirebaseDatabaseEdit extends AppCompatActivity {
    String city_fromMain, temp_fromMain, hum_fromMain, icon_fromMain;
    EditText input_userID, input_fname, input_lname, input_email, input_phone;
    Firebase firebase = new Firebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_database);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        city_fromMain = sp.getString("city1","");
        temp_fromMain = sp.getString("temp1","00");
        hum_fromMain = sp.getString("hum1","00");
        icon_fromMain = sp.getString("icon1","00");

        Button insert = (Button) findViewById(R.id.bttn_insert);
        Button update = (Button) findViewById(R.id.bttn_updateValue);
        Button delete = (Button) findViewById(R.id.bttn_delete);
        Button find = (Button) findViewById(R.id.bttn_find);

        input_userID = (EditText) findViewById(R.id.inputID);
        input_fname = (EditText) findViewById(R.id.inputFName);
        input_lname = (EditText) findViewById(R.id.inputLName);
        input_email = (EditText) findViewById(R.id.inputEmail);
        input_phone = (EditText) findViewById(R.id.inputPhone);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_userID_text = input_userID.getText().toString();
                String input_fname_text = input_fname.getText().toString();
                String input_lname_text = input_lname.getText().toString();
                String input_phone_text = input_phone.getText().toString();
                String input_email_text = input_email.getText().toString();
                firebase.newUser(input_userID_text,input_fname_text,input_lname_text,input_phone_text, input_email_text);
                input_userID.setText("");
                input_email.setText("");
                input_phone.setText("");
                input_lname.setText("");
                input_fname.setText("");
                Toast.makeText(FirebaseDatabaseEdit.this, "User has been added successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseDatabaseEdit.this, UpdateFirebaseData.class));
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_userID_text = input_userID.getText().toString();
                firebase.findUser(input_userID_text);firebase.findUser(input_userID_text);
                String [] results = firebase.findUser(input_userID_text);
                Log.d("Yasmeen", "FIND USER ** CLASS **: " + results[0] + " " + results[1]);
                AlertDialog.Builder builder = new AlertDialog.Builder(FirebaseDatabaseEdit.this);
                builder.setCancelable(true);
                builder.setTitle("User with ID = " + results[0]);
                builder.setMessage("First name: " + results[1] +"\nLast Name: "+results[2]+"\nEmail: " + results[3] +"\nPhone Number: " +results[4]);
                builder.show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_userID_text = input_userID.getText().toString();
                firebase.deleteUser(input_userID_text);
                Toast.makeText(FirebaseDatabaseEdit.this, "User has been deleted successfully.", Toast.LENGTH_SHORT).show();
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