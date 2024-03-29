package com.example.loginandsignup.helpers;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginandsignup.R;

public class AndroidToast {
    public static void showToast(Context context, String message, View layout) {

        // Set toast message
        TextView text = layout.findViewById(R.id.text_toast);
        text.setText(message);

        // Create and show the toast
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();

    }
}
