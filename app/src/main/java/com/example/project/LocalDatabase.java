package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LocalDatabase extends AppCompatActivity {
    String city_fromMain, temp_fromMain, hum_fromMain, icon_fromMain;
    EditText input_userID, input_fname, input_lname, input_email, input_phone, input_userIDFirebase;
    Firebase firebase = new Firebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        city_fromMain = sp.getString("city1","");
        temp_fromMain = sp.getString("temp1","00");
        hum_fromMain = sp.getString("hum1","00");
        icon_fromMain = sp.getString("icon1","00");

        final DatabaseHelper database = new DatabaseHelper(this);
        input_userID = (EditText) findViewById(R.id.inputID);
        input_fname = (EditText) findViewById(R.id.inputFName);
        input_lname = (EditText) findViewById(R.id.inputLName);
        input_email = (EditText) findViewById(R.id.inputEmail);
        input_phone = (EditText) findViewById(R.id.inputPhone);
        input_userIDFirebase = (EditText) findViewById(R.id.inputUserIDFirebase);

        Button add = (Button) findViewById(R.id.bttn_insert);
        Button delete = (Button) findViewById(R.id.bttn_delete);
        Button find = (Button) findViewById(R.id.bttn_find);
        Button update = (Button) findViewById(R.id.bttn_updateValue);
        Button fetch = (Button) findViewById(R.id.bttn_fetchandcombine);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_val = input_userID.getText().toString();
                String fname_val = input_fname.getText().toString();
                String lname_val = input_lname.getText().toString();
                String phone_val = input_phone.getText().toString();
                String email_val = input_email.getText().toString();
                try{
                    database.addUser(id_val,fname_val,lname_val,phone_val,email_val);
                    Toast.makeText(LocalDatabase.this,"Record " + id_val + " successfully added", Toast.LENGTH_SHORT).show();
                    input_userID.setText("");
                    input_fname.setText("");
                    input_lname.setText("");
                    input_phone.setText("");
                    input_email.setText("");
                    Log.d("Yasmeen", "Add in Local Database Success");
                }
                catch(Exception e){
                    Log.d("Yasmeen", "Add in Local Database Fail");
                    Toast.makeText(LocalDatabase.this, "User ID already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_val = input_userID.getText().toString();
                try {
                    database.deleteUser(id_val);
                    Toast.makeText(LocalDatabase.this,"Record " + id_val + " successfully deleted", Toast.LENGTH_SHORT).show();
                    Log.d("Yasmeen", "Delete Success");
                }
                catch (Exception e){
                    Log.d("Yasmeen", "Delete FAIL");
                    Toast.makeText(LocalDatabase.this, "ID does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_val = input_userID.getText().toString();
                String fname_val = input_fname.getText().toString();
                String lname_val = input_lname.getText().toString();
                String phone_val = input_phone.getText().toString();
                String email_val = input_email.getText().toString();
                try {
                    if (id_val.isEmpty()) {
                        Toast.makeText(LocalDatabase.this, "ID must be filled.", Toast.LENGTH_SHORT).show();
                    } else if (email_val.isEmpty() && phone_val.isEmpty() && lname_val.isEmpty() && fname_val.isEmpty()) {
                        Toast.makeText(LocalDatabase.this, "Please insert your updated value in the correct field(s)", Toast.LENGTH_LONG).show();
                    } else {
                        if (!(fname_val.isEmpty())) {
                            database.updateUserfName(id_val, fname_val);
                            Toast.makeText(LocalDatabase.this, "Record " + id_val + " successfully updated", Toast.LENGTH_SHORT).show();
                        }
                        if (!(lname_val.isEmpty())) {
                            database.updateUserlName(id_val, lname_val);
                            Toast.makeText(LocalDatabase.this, "Record " + id_val + " successfully updated", Toast.LENGTH_SHORT).show();
                        }
                        if (!(phone_val.isEmpty())) {
                            database.updateUserPhone(id_val, phone_val);
                            Toast.makeText(LocalDatabase.this, "Record " + id_val + " successfully updated", Toast.LENGTH_SHORT).show();
                        }
                        if (!(email_val.isEmpty())) {
                            database.updateUserEmail(id_val, email_val);
                            Toast.makeText(LocalDatabase.this, "Record " + id_val + " successfully updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(LocalDatabase.this, "ID does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_val = input_userID.getText().toString();
                Cursor cursor = database.findUser(id_val);
                if (id_val.isEmpty()) {
                    Toast.makeText(LocalDatabase.this, "ID must be filled.", Toast.LENGTH_SHORT).show();
                }
                try{
                    String id_find = cursor.getString(0);
                    String fname_find = cursor.getString(1);
                    String lname_find = cursor.getString(2);
                    String phone_find = cursor.getString(3);
                    String email_find = cursor.getString(4);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LocalDatabase.this);
                    builder.setCancelable(true);
                    builder.setTitle("User " + fname_find + " " + lname_find + " Info:");
                    builder.setMessage("ID: " + id_find + " \n" +
                            "Name: " + fname_find + " \n" +
                            "Surname: " + lname_find + " \n" +
                            "Phone: " + phone_find + " \n" +
                            "Email: " + email_find + " \n");
                    builder.show();
                    Log.d("Yasmeen", "Find Success");
                }
                catch(Exception e){
                    Toast.makeText(LocalDatabase.this, "ID does not exist.", Toast.LENGTH_SHORT).show();
                    Log.d("Yasmeen", "Find FAIL");
                }
            }
        });

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_userID_text = input_userIDFirebase.getText().toString();
                String[] results = firebase.findUser(input_userID_text);
                database.addUser(results[0], results[1], results[2], results[4], results[3]);
                Toast.makeText(LocalDatabase.this, "User added to local database successfuly.", Toast.LENGTH_SHORT).show();
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