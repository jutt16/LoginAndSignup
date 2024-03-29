package com.example.loginandsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandsignup.helpers.AndroidToast;
import com.example.loginandsignup.helpers.DatabaseHelper;
import com.example.loginandsignup.helpers.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Spinner yearSpinner;
    EditText full_name,user_name,password,re_password;
    RadioGroup gender;
    Spinner yearOfBirth;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing
        full_name = findViewById(R.id.full_name_edittext);
        gender = findViewById(R.id.radio_group_gender);
        yearOfBirth = findViewById(R.id.year_spinner);
        user_name = findViewById(R.id.username_edittext);
        password = findViewById(R.id.password_edittext);
        re_password = findViewById(R.id.re_password_edittext);

        // Toggle visibility icon for password EditText
        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (password.getRight() - password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Clicked on the drawable
                        togglePasswordVisibility(password);
                        return true;
                    }
                }
                return false;
            }
        });

        // Toggle visibility icon for re-password EditText
        re_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (re_password.getRight() - re_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Clicked on the drawable
                        togglePasswordVisibility(re_password);
                        return true;
                    }
                }
                return false;
            }
        });

        //databaseHelper Object
        databaseHelper = new DatabaseHelper(this);

        List<String> yearsList = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Populate the list with years, e.g., from current year to 20 years in the past
        for (int i = currentYear; i >= currentYear - 50; i--) {
            yearsList.add(String.valueOf(i));
        }

        //creating array adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adapter for spinner
        yearSpinner = findViewById(R.id.year_spinner);
        yearSpinner.setAdapter(adapter);

    }

    public void register(View view) {
        try {
            // Get user input
            String fullName = full_name.getText().toString();

            // Get the ID of the selected radio button
            int selectedId = gender.getCheckedRadioButtonId();

            // Find the selected radio button
            RadioButton selectedRadioButton = findViewById(selectedId);

            String gender=null;
            // Check if a radio button is selected
            if (selectedRadioButton != null) {
                // Get the text of the selected radio button
                gender = selectedRadioButton.getText().toString();

                // Now you have the value of the selected radio button (gender)
                // You can use it as needed
                Toast.makeText(RegisterActivity.this, "Selected gender: " + gender, Toast.LENGTH_SHORT).show();
            } else {
                // Inflate custom layout
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_layout,
                        findViewById(R.id.custom_toast_layout_root));

                AndroidToast.showToast(getApplicationContext(),"Please select a gender",layout);
            }

            int yearOfBirth=0;
            yearOfBirth = Integer.parseInt(yearSpinner.getSelectedItem().toString());
            String username = user_name.getText().toString();
            String pass = password.getText().toString();

            // Create new user object
            User user;
            long result=-1;
            //Validation
            if(!pass.equals(re_password.getText().toString())) {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_layout,
                        findViewById(R.id.custom_toast_layout_root));

                AndroidToast.showToast(getApplicationContext(),pass+"password and re password should be same!"+re_password.getText().toString(),layout);
            } else {
                if(!fullName.isEmpty() || !gender.isEmpty() || yearOfBirth!=0 || !username.isEmpty() || !pass.isEmpty()) {
                    user = new User(fullName, gender, yearOfBirth, username, pass);
                    // Insert user into database
                    result = databaseHelper.addUser(user);
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_layout,
                            findViewById(R.id.custom_toast_layout_root));

                    AndroidToast.showToast(getApplicationContext()," please fill all fields!",layout);
                }
            }

            if (result != -1) {
                // Inflate custom layout
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_layout,
                        findViewById(R.id.custom_toast_layout_root));

                AndroidToast.showToast(getApplicationContext(),"Registration successful",layout);
                // Navigate to main activity or login activity
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            } else {
                // Inflate custom layout
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.custom_toast_layout,
                        findViewById(R.id.custom_toast_layout_root));

                AndroidToast.showToast(getApplicationContext(),"Registration failed",layout);
            }
        } catch (Exception e) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_toast_layout,
                    findViewById(R.id.custom_toast_layout_root));

            AndroidToast.showToast(getApplicationContext(),e.toString(),layout);
        }
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void clear(View view) {
        full_name.setText("");
        user_name.setText("");
        password.setText("");
        re_password.setText("");
        // Assuming radioGroup is the reference to your RadioGroup
        gender.clearCheck();
        // Assuming spinner is the reference to your Spinner
        yearSpinner.setSelection(0); // Set the selection to the first position

        recreate();
    }


    // Method to toggle password visibility
    private void togglePasswordVisibility(EditText editText) {
        if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_off_24, 0);
        } else {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.baseline_visibility_24, 0);
        }
    }
}