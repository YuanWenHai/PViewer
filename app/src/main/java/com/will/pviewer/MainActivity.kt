package com.will.pviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.will.pviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
    }
}