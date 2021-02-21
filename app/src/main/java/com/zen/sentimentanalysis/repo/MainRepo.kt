package com.zen.sentimentanalysis.repo

import com.zen.sentimentanalysis.retrofit.RetrofitClient

class MainRepo {
    suspend fun getSentimentalAnalysis(text: String) =
        RetrofitClient.api.getSentimentAnalysis(text)
}