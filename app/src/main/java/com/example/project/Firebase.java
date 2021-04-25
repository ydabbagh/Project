package com.example.project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Firebase extends AppCompatActivity {
    FirebaseDatabase mDatabase;
    DatabaseReference myRef;
    String result_fname, result_lname, result_email, result_phone;
    String [] array_result;

    public Firebase(){
        FirebaseApp.initializeApp(this);
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("users");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void newUser(String userId, String firstName, String lastName, String phoneNumber, String emailAddress) {
        User user = new User(userId, firstName, lastName, phoneNumber, emailAddress);
        myRef.child(userId).setValue(user);
    }

    public void updateUser(String userID, int key2update, String newValue) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String userid = ds.child("userId").getValue().toString();

                    if(userid.equals(userID)){
                        result_fname = ds.child("firstName").getValue().toString();
                        result_lname = ds.child("lastName").getValue().toString();
                        result_email = ds.child("emailAddress").getValue().toString();
                        result_phone= ds.child("phoneNumber").getValue().toString();
                    }
                }
                switch(key2update) {
                    case (1):
                        newUser(userID, newValue, result_lname, result_phone, result_email);
                        break;
                    case (2):
                        newUser(userID, result_fname, newValue, result_phone, result_email);
                        break;
                    case (3):
                        newUser(userID, result_fname, result_lname, newValue, result_email);
                        break;
                    case (4):
                        newUser(userID, result_fname, result_lname, result_phone, newValue);
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Yasmeen", "DB Error " + error.toString());
            }
        });
    }

    public String[] findUser(String userID) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String userid = ds.child("userId").getValue().toString();
                        if (userid.equals(userID)) {
                            result_fname = ds.child("firstName").getValue().toString();
                            result_lname = ds.child("lastName").getValue().toString();
                            result_email = ds.child("emailAddress").getValue().toString();
                            result_phone = ds.child("phoneNumber").getValue().toString();
                            Log.d("Yasmeen", "FIND USER: " + userID + " " + result_fname);
                        }
                    }
                }
                catch(Exception e){
                    Log.d("Yasmeen", "DB Error: " + e.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Yasmeen", "DB Error " + error.toString());
            }
        });
        array_result = new String[]{userID, result_fname, result_lname, result_email,result_phone};
        return array_result;
    }

    public void deleteUser(String userID) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    String userid = ds.child("userId").getValue().toString();

                    if (userid.equals(userID)) {
                        ds.getRef().removeValue();
                        Log.d("Yasmeen", "DELETED USER: " + snapshot);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Yasmeen", "DB Error " + error.toString());
            }
        });
    }
}
