package com.zen.sentimentanalysis.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.zen.sentimentanalysis.repo.MainRepo
import com.zen.sentimentanalysis.utils.APIResource
import kotlinx.coroutines.Dispatchers

class SentimentalViewModel(private val mainRepo: MainRepo) : ViewModel() {

    fun getAnalysis(text: String, mode: String) = liveData(Dispatchers.IO) {
        emit(APIResource.Loading())
        try {
            emit(APIResource.Success(data = mainRepo.getSentimentalAnalysis(text, mode)))
        } catch (exception: Exception) {
            Log.e("TAG", "getAnalysis: $exception")
            emit(exception.message?.let { APIResource.Failure(data = null, message = it) })
        }
    }

}