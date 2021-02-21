package com.sample.test.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Date", MODE_PRIVATE);

        // 各取得処理
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault());

        // Date
        try {
            Date date = format.parse(sharedPreferences.getString("Date", null));
            Log.d(TAG, "Date = " + date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }


        // Calendar
        try {
            Calendar cal = new GregorianCalendar();
            Date date = format.parse(sharedPreferences.getString("Calendar", null));
            if (date != null) cal.setTime(date);
            Log.d(TAG, "Calendar = " + cal);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }

        // LocalDate
        try {
            LocalDate localDate = LocalDate.parse(sharedPreferences.getString("LocalDate", null));
            Log.d(TAG, "LocalDate = " + localDate);
        }catch(NullPointerException e) {
            e.printStackTrace();
        }

    }

    public void onClickButton(View view) {
        // データ取得
        Calendar calc = Calendar.getInstance();
        Date date = new Date();
        LocalDate localDate = LocalDate.now();

        // 保存
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault());
        sharedPreferences.edit()
                .putString("Date", format.format(date))
                .putString("Calendar", format.format(calc.getTime()))
                .putString("LocalDate", localDate.toString())
                .apply();
    }
}
