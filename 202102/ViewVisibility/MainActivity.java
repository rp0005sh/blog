package com.sample.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        View v = findViewById(R.id.textView);
        switch (v.getVisibility()) {
            case View.GONE:
                v.setVisibility(View.VISIBLE);
                Snackbar.make(v, "View.VISIBLE", 2 * 1000).show();
                break;
            case View.VISIBLE:
                v.setVisibility(View.INVISIBLE);
                Snackbar.make(v, "View.INVISIBLE", 2 * 1000).show();
                break;
            case View.INVISIBLE:
                v.setVisibility(View.GONE);
                Snackbar.make(v, "View.GONE", 2 * 1000).show();
                break;
        }
    }
}
