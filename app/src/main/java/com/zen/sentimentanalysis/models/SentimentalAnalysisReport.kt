package com.zen.sentimentanalysis.models

data class SentimentalAnalysisReport(
    val polarity: String,
    val polarity_confidence: Double,
    val subjectivity: String,
    val subjectivity_confidence: Double,
    val text: String
)