package com.example.win20

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        setImageForUploading()
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var currentProgress = 0
        progressBar.max = 3

        val context = this

        object : CountDownTimer(3000, 1000){
            override fun onTick(p0: Long) {
                currentProgress+=1
                progressBar.progress = currentProgress
            }
            override fun onFinish() {
                startActivity(Intent(context, MainActivity::class.java))
                finish()
            }
        }.start()
    }

    private fun setImageForUploading (){
        val layout = findViewById<ConstraintLayout>(R.id.constraint_splash)
        SetLoadImage.upload(layout, this)
    }
}