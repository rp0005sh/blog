package com.sample.test.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView;
import android.content.SharedPreferences;

class MainActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView textView;
    @Override
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("SaveState", MODE_PRIVATE);
        textView = findViewById(R.id.textView);
        // 保存データ読み込みを行ってViewに設定
        textView.setVisibility(sharedPreferences.getInt("visibility", View.VISIBLE));
    }

    public void clickButton(View view) { 
        int value = (textView.getVisibility() == View.VISIBLE) ? View.INVISIBLE : View.VISIBLE;
        textView.setVisibility(value);
        // 状態を保存する
        sharedPreferences.edit().putInt("visibility", value).apply();
    }
}
