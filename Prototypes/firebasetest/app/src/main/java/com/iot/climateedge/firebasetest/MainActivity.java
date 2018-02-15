package com.iot.climateedge.firebasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.*;
import java.lang.*;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class User {

    public String date_of_birth;
    public String full_name;

    public User(String date_of_birth, String full_name) {
        this.date_of_birth = date_of_birth;
        this.full_name = full_name;
        // ...
    }

}


public class MainActivity extends AppCompatActivity {

    private Button mSendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("condition");
        final DatabaseReference usersRef = myRef.child("users");
        final Map<String, User> users = new HashMap<>();
        users.put("alanisawesome", new User("June 23, 1912", "Alan Turing"));
        users.put("gracehop", new User("December 9, 1906", "Grace Hopper"));

        mSendData = (Button) findViewById(R.id.sendData);


        mSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersRef.setValue(users);
            }
        });
    }
}
