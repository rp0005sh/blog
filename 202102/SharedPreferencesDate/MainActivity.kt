package com.sample.test.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private val sharedPreferences by lazy{ getSharedPreferences("Date", MODE_PRIVATE) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 各取得処理
        val format = SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault())

        // Date
        val date = sharedPreferences.getString("Date", null)?.let{ format.parse(it)}
        Log.d(TAG, "Date = $date");

        // Calendar
        val calc = sharedPreferences.getString("Calendar", null)?.let{format.parse(it)}
            ?.let { GregorianCalendar().apply{ time = it }}
        Log.d(TAG, "Calender = $calc");

        // LocalDate
        val localDate = sharedPreferences.getString("LocalDate", null)?.let{LocalDate.parse(it)}
        Log.d(TAG, "LocalDate = $localDate");

    }

    fun onClickButton(view: View) {
        // データ取得
        val calc = Calendar.getInstance()
        val date = Date()
        val localDate = LocalDate.now()

        // 保存
        val format = SimpleDateFormat("yyyyMMddHHmmssSSSS", Locale.getDefault())
        sharedPreferences.edit() {
            putString("Calendar", format.format(date))
            putString("Calendar", format.format(calc.time))
            putString("LocalDate", localDate.toString())
        }
    }
}
