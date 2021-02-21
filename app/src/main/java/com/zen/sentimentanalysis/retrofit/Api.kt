package com.zen.sentimentanalysis.retrofit

import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("sentiment")
    suspend fun getSentimentAnalysis(
        @Query("text") text: String
    ): Response<SentimentalAnalysisReport>


}