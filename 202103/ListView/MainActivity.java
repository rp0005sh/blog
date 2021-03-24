package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        ((ListView)findViewById(R.id.listView)).setAdapter(mAdapter);
    }

    /** ボタンが押された */
    public void onClick(View v) {
        //押下時のタイムスタンプを追加していく
        mAdapter.add(LocalTime.now().toString());
    }

}
