package com.zen.sentimentanalysis.listeners

import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import com.zen.sentimentanalysis.utils.APIResource
import retrofit2.Response

interface GenericListener {
    fun showResult(response: APIResource<out Response<SentimentalAnalysisReport>>?)
    fun showDialog(value: Boolean)
}