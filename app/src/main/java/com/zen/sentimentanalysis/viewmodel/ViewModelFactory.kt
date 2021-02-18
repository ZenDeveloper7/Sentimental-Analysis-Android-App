package com.zen.sentimentanalysis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zen.sentimentanalysis.repo.MainRepo

class ViewModelFactory(
    private val mainRepo: MainRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SentimentalViewModel(mainRepo) as T
    }
}