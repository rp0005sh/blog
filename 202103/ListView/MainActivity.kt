package com.example.myapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    // 引数に渡すListはミュータブル(mutableListOf)であること。
    // listOf()でイミュータブルのリストを渡すと、データ追加できなくなる。
    private val adapter by lazy { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ListView>(R.id.listView).adapter = adapter
    }

    /** ボタンが押された */
    fun onClick(v: View) {
        //押下時のタイムスタンプを追加していく
        adapter.add(LocalTime.now().toString())
    }
}
