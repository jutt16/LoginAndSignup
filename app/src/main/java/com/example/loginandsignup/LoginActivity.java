package com.example.loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.loginandsignup.helpers.AndroidToast;
import com.example.loginandsignup.helpers.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText username, passwordEditText;
    Drawable eyeIcon;
    boolean isPasswordVisible;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        eyeIcon = getResources().getDrawable(R.drawable.baseline_visibility_24);
        isPasswordVisible = false;

        // Set the click listener for the eye icon
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Check if the touch event is on the drawable end
                if (event.getAction() == MotionEvent.ACTION_UP &&
                        event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[2].getBounds().width())) {

                    // Toggle the visibility of the password
                    if (isPasswordVisible) {
                        // Hide the password
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        eyeIcon = getResources().getDrawable(R.drawable.baseline_visibility_24);
                    } else {
                        // Show the password
                        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        eyeIcon = getResources().getDrawable(R.drawable.baseline_visibility_off_24);
                    }
                    isPasswordVisible = !isPasswordVisible;

                    // Update the drawable end with the new icon
                    passwordEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, eyeIcon, null);

                    return true;
                }
                return false;
            }
        });
    }

    public void gotoMain(View view) {
        // Get username and password from EditText fields
        String user_name = username.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check login credentials
        if (databaseHelper.checkLogin(user_name, password)) {
            // Login successful
            // Inflate custom layout
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_layout,
                    findViewById(R.id.custom_toast_layout_root));

            AndroidToast.showToast(getApplicationContext(),"Login Successfully!",layout);
            // Navigate to next activity or perform other actions
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("uname",user_name);
            startActivity(intent);

        } else {
            // Login failed
            // Inflate custom layout
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_layout,
                    findViewById(R.id.custom_toast_layout_root));

            AndroidToast.showToast(getApplicationContext(),"Login Failed!",layout);
            // Show error message or handle appropriately
        }
    }
    public void gotoSignUP(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);

        startActivity(intent);
    }
}