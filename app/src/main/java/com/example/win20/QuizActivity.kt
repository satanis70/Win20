package com.example.win20

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.win20.model.Quizquestion
import com.onesignal.OneSignal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class QuizActivity : AppCompatActivity() {

    private val questionArray = ArrayList<Quizquestion>()
    private var currentPosition = 0
    private lateinit var buttonNext: AppCompatButton
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
        val layout = findViewById<ConstraintLayout>(R.id.constraint_quiz)
        SetLoadImage.upload(layout, this)
        buttonNext = findViewById<AppCompatButton>(R.id.button_next)
        responseData()
        buttonNext.setOnClickListener {
            if (currentPosition==questionArray.size){
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.apply {
                    setMessage("Congratulations! You scored: $score")
                    setNegativeButton("Exit") { _, _ ->
                        startActivity(Intent(this@QuizActivity, MainActivity::class.java))
                        finish()
                    }
                }.create().show()
            } else {
                responseData()
            }
        }

    }

    private fun responseData() {
        questionArray.clear()
        CoroutineScope(Dispatchers.IO).launch {
            val quizApi = Retrofit.Builder()
                .baseUrl("http://49.12.202.175/win20/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QuizApi::class.java)
            val call = quizApi.getData().awaitResponse()
            if (call.isSuccessful){
                questionArray.addAll(call.body()!!.quizquestion)
            }
            launch(Dispatchers.Main) {
                showQuiz(position = currentPosition)
                currentPosition+=1
            }
        }
    }

    private fun showQuiz(position: Int){
        buttonNext.isEnabled = false
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val buttonOpt1 = findViewById<AppCompatButton>(R.id.button_option1)
        val buttonOpt2 = findViewById<AppCompatButton>(R.id.button_option2)
        val buttonOpt3 = findViewById<AppCompatButton>(R.id.button_option3)
        val buttonOpt4 = findViewById<AppCompatButton>(R.id.button_option4)
        buttonOpt1.setBackgroundResource(R.drawable.button_round)
        buttonOpt2.setBackgroundResource(R.drawable.button_round)
        buttonOpt3.setBackgroundResource(R.drawable.button_round)
        buttonOpt4.setBackgroundResource(R.drawable.button_round)
        buttonOpt1.isEnabled = true
        buttonOpt2.isEnabled = true
        buttonOpt3.isEnabled = true
        buttonOpt4.isEnabled = true
        tvQuestion.text = questionArray[position].question
        buttonOpt1.text = questionArray[position].answer1.name
        buttonOpt2.text = questionArray[position].answer2.name
        buttonOpt3.text = questionArray[position].answer3.name
        buttonOpt4.text = questionArray[position].answer4.name
        buttonOpt1.setOnClickListener {
            if (questionArray[position].answer1.trueorfalse=="true"){
                buttonOpt1.setBackgroundResource(R.drawable.button_green)
                score+=1
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true

            } else {
                buttonOpt1.setBackgroundResource(R.drawable.button_red)
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true
            }
        }
        buttonOpt2.setOnClickListener {
            if (questionArray[position].answer2.trueorfalse=="true"){
                buttonOpt2.setBackgroundResource(R.drawable.button_green)
                score+=1
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true

            } else {
                buttonOpt2.setBackgroundResource(R.drawable.button_red)
                score+=1
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true
            }
        }
        buttonOpt3.setOnClickListener {
            if (questionArray[position].answer3.trueorfalse=="true"){
                score+=1
                buttonOpt3.setBackgroundResource(R.drawable.button_green)
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true

            } else {
                buttonOpt3.setBackgroundResource(R.drawable.button_red)
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true
            }
        }
        buttonOpt4.setOnClickListener {
            if (questionArray[position].answer4.trueorfalse=="true"){
                score+=1
                buttonOpt4.setBackgroundResource(R.drawable.button_green)
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true

            } else {
                buttonOpt4.setBackgroundResource(R.drawable.button_red)
                buttonOpt1.isEnabled = false
                buttonOpt2.isEnabled = false
                buttonOpt3.isEnabled = false
                buttonOpt4.isEnabled = false
                buttonNext.isEnabled = true
            }
        }
    }


}