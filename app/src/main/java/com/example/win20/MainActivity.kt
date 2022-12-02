package com.example.win20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout = findViewById<ConstraintLayout>(R.id.constraint_main)
        SetLoadImage.upload(layout, this)
        val startButton = findViewById<AppCompatButton>(R.id.button_start)
        startButton.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }
}