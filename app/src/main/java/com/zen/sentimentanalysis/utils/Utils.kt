package com.zen.sentimentanalysis.utils

import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import java.util.*

object Utils {
    fun getGreeting(): String {
        val date = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = date

        return when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 12..16 -> {
                "Good Afternoon"
            }
            in 17..20 -> {
                "Good Evening"
            }
            in 21..23 -> {
                "Good Night"
            }
            else -> {
                "Good Morning"
            }
        }
    }

    fun getStringFormatReport(model: SentimentalAnalysisReport): String {
        return "Polarity - ${model.polarity}" +
                "\nPolarity Confidence - ${model.polarity_confidence}" +
                "\nSubjectivity - ${model.subjectivity}" +
                "\nSubjectivity Confidence - ${model.subjectivity_confidence}" +
                "\nRequested Text - ${model.text}"
    }
}