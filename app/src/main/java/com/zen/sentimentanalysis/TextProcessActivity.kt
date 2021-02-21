package com.zen.sentimentanalysis

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zen.sentimentanalysis.listeners.TextProcessListener
import com.zen.sentimentanalysis.models.Polarity
import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import com.zen.sentimentanalysis.utils.APIResource
import com.zen.sentimentanalysis.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_layout.*
import kotlinx.android.synthetic.main.header_layout.view.*
import retrofit2.Response
import java.util.*


class TextProcessActivity : GenericActivity(), TextProcessListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)

        if (!selectedText.isNullOrBlank()) {
            input.text = selectedText
            initReportBottomDialog()
            setUpGreeting()
            initViewModel()
            initAnalyzeDialog()

            getAnalyzedReport(selectedText.toString(), this)
        } else {
            showMessage("No Text Selected!")
        }

        analyze.setOnClickListener {
            if (!selectedText.isNullOrBlank())
                getAnalyzedReport(selectedText.toString(), this)
            else
                showMessage("No Text Selected!")
        }

        bottomDialog.copy.setOnClickListener {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(
                "text", Utils.getStringFormatReport(generatedReportModel)
            )
            clipboard.setPrimaryClip(clip)
            showMessage("Report Copied!")
        }
    }

    private fun setUpGreeting() {
        val greetingText = Utils.getGreeting()
        headerLayout.greeting.text = greetingText
    }

    override fun showResult(response: APIResource<out Response<SentimentalAnalysisReport>>?) {
        val generatedReportModel = response?.data!!.body()!!
        val polarity = generatedReportModel.polarity.capitalize(Locale.ROOT)
        bottomDialog.report.text = polarity
        when (polarity) {
            Polarity.NEGATIVE -> {
                bottomDialog.report.setTextColor(resources.getColor(R.color.negativeColor))
            }
            Polarity.POSITIVE -> {
                bottomDialog.report.setTextColor(resources.getColor(R.color.positiveColor))
            }
            Polarity.NEUTRAL -> {
                bottomDialog.report.setTextColor(resources.getColor(R.color.neutralColor))
            }
        }
        setPieChart(
            generatedReportModel.polarity_confidence.toFloat(),
            bottomDialog.polarityConfidence
        )
        setPieChart(
            generatedReportModel.subjectivity_confidence.toFloat(),
            bottomDialog.subjectivityConfidence
        )
        bottomDialog.show()
    }

    override fun showDialog(value: Boolean) {
        if (value)
            dialog.show()
        else
            dialog.dismiss()
    }

}