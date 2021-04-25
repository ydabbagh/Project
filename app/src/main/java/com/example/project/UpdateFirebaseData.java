package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class UpdateFirebaseData extends AppCompatActivity {
    EditText input_updatevalue, input_updateUser;
    Firebase firebase = new Firebase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_firebase_data);

        Button update = (Button) findViewById(R.id.bttn_updateValue);
        input_updateUser = (EditText) findViewById(R.id.inputuserIDtoUpdate);
        input_updatevalue = (EditText) findViewById(R.id.inputUpdatedValue);
        RadioButton changeFirstName = (RadioButton)findViewById(R.id.RBFirstName);
        RadioButton changeLastName = (RadioButton)findViewById(R.id.RBLastName);
        RadioButton changeEmail = (RadioButton)findViewById(R.id.RBEmail);
        RadioButton changePhone= (RadioButton)findViewById(R.id.RBPhone);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newValue = input_updatevalue.getText().toString();
                String userIDToUpdate = input_updateUser.getText().toString();
                if(userIDToUpdate.isEmpty() || userIDToUpdate.isEmpty()){
                    Toast.makeText(UpdateFirebaseData.this, "Fill all fields.", Toast.LENGTH_SHORT).show();
                }
                if(!(changeFirstName.isChecked()) &&!(changeEmail.isChecked())&& !(changeLastName.isChecked())&& !(changePhone.isChecked())){
                    Toast.makeText(UpdateFirebaseData.this, "Choose a value to change.", Toast.LENGTH_SHORT).show();
                }
                if(changeFirstName.isChecked()){
                    firebase.updateUser(userIDToUpdate,1,newValue);
                    startActivity(new Intent(UpdateFirebaseData.this, FirebaseDatabaseEdit.class));
                    Toast.makeText(UpdateFirebaseData.this, "User has been updated successfully.", Toast.LENGTH_SHORT).show();
                }
                if(changeLastName.isChecked()){
                    firebase.updateUser(userIDToUpdate,2,newValue);
                    startActivity(new Intent(UpdateFirebaseData.this, FirebaseDatabaseEdit.class));
                    Toast.makeText(UpdateFirebaseData.this, "User has been updated successfully.", Toast.LENGTH_SHORT).show();
                }
                if(changePhone.isChecked()){
                    firebase.updateUser(userIDToUpdate,3,newValue);
                    startActivity(new Intent(UpdateFirebaseData.this, FirebaseDatabaseEdit.class));
                    Toast.makeText(UpdateFirebaseData.this, "User has been updated successfully.", Toast.LENGTH_SHORT).show();
                }
                if(changeEmail.isChecked()){
                    firebase.updateUser(userIDToUpdate,4,newValue);
                    startActivity(new Intent(UpdateFirebaseData.this, FirebaseDatabaseEdit.class));
                    Toast.makeText(UpdateFirebaseData.this, "User has been updated successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}