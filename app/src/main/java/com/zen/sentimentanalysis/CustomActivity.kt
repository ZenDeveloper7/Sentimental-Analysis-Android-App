package com.zen.sentimentanalysis

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import com.zen.sentimentanalysis.listeners.GenericListener
import com.zen.sentimentanalysis.models.Polarity
import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import com.zen.sentimentanalysis.utils.APIResource
import com.zen.sentimentanalysis.utils.Utils
import kotlinx.android.synthetic.main.activity_custom.*
import kotlinx.android.synthetic.main.bottom_layout.*
import kotlinx.android.synthetic.main.header_layout.view.*
import retrofit2.Response
import java.util.*

class CustomActivity : GenericActivity(), GenericListener {

    private lateinit var customText: String
    private val TAG = CustomActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom)

        //initialize
        initReportBottomDialog()
        setUpGreeting()
        initViewModel()
        initAnalyzeDialog()

        analyzeCustom.setOnClickListener {
            customText = customEditBox.text.toString()
            if (customText.isEmpty()) {
                showMessage("No Text Detected!")
            } else {
                getAnalyzedReport(customText, this)
            }
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
        customHeaderLayout.greeting.text = greetingText
    }

    override fun showResult(response: APIResource<out Response<SentimentalAnalysisReport>>?) {
        val polarity = response?.data!!.body()?.polarity!!.capitalize(Locale.ROOT)
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
            response.data.body()!!.polarity_confidence.toFloat(),
            bottomDialog.polarityConfidence
        )
        setPieChart(
            response.data.body()!!.subjectivity_confidence.toFloat(),
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