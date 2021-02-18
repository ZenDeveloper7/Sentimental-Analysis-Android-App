package com.zen.sentimentanalysis.repo

import com.zen.sentimentanalysis.retrofit.RetrofitClient

class MainRepo {
    suspend fun getSentimentalAnalysis(text: String, mode: String) =
        RetrofitClient.api.getSentimentAnalysis(text, mode)
}