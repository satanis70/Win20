package com.example.win20

import com.example.win20.model.QuestionsModel
import retrofit2.Call
import retrofit2.http.GET

interface QuizApi {
    @GET("quizquestion.json")
    fun getData():Call<QuestionsModel>
}