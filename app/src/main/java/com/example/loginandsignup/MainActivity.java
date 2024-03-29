package com.example.loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.loginandsignup.helpers.AndroidToast;
import com.example.loginandsignup.helpers.DatabaseHelper;
import com.example.loginandsignup.helpers.User;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    TextView usernametextview,gender,age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra("uname");
        User user = null;

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        user = databaseHelper.getUserByUsername(username);

        //initializing
        usernametextview = findViewById(R.id.username_textview);
        gender = findViewById(R.id.gender_textview);
        age = findViewById(R.id.age_textview);

        usernametextview.setText(user.getUsername());
        gender.setText(user.getGender());
        age.setText(String.valueOf(user.getYearOfBirth()));

    }
    public void gotoLogin(View view) {
        // Inflate custom layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout,
                findViewById(R.id.custom_toast_layout_root));

        AndroidToast.showToast(getApplicationContext(),"Logout Successfully!",layout);
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}