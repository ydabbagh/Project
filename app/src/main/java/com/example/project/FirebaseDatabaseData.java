package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FirebaseDatabaseData extends AppCompatActivity {
    String city_fromMain, temp_fromMain, hum_fromMain, icon_fromMain;
    DatabaseReference databaseReference;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_database_data);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        city_fromMain = sp.getString("city1","");
        temp_fromMain = sp.getString("temp1","00");
        hum_fromMain = sp.getString("hum1","00");
        icon_fromMain = sp.getString("icon1","00");

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        listView=(ListView)findViewById(R.id.listviewtxt);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(User.class).toString();
                arrayList.add(value);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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