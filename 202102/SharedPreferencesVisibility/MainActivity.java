package com.sample.test.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val sharedPreferences by lazy{ getSharedPreferences("SaveState", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        // 保存データ読み込みを行ってViewに設定
        textView.visibility = sharedPreferences.getInt("visibility", View.VISIBLE)
    }

    fun clickButton(view: View) {
        val value = if (textView.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        textView4.visibility = value
        // 状態を保存する
        sharedPreferences.edit().putInt("visibility", value).apply()
    }
}
