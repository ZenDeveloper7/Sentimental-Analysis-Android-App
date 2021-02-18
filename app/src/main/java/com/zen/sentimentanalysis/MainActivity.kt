package com.zen.sentimentanalysis

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import com.zen.sentimentanalysis.repo.MainRepo
import com.zen.sentimentanalysis.utils.APIResource
import com.zen.sentimentanalysis.viewmodel.SentimentalViewModel
import com.zen.sentimentanalysis.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response


class MainActivity : GenericActivity() {

    private lateinit var sentimentalViewModel: SentimentalViewModel
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)

        if (!selectedText.isNullOrBlank()) {
            input.text = selectedText
            setViewModel()
            setDialog()
            sentimentalViewModel.getAnalysis(selectedText.toString(), "tweet")
                .observe(this, { response ->
                    when (response) {
                        is APIResource.Loading -> {
                            showDialog(true)
                        }
                        is APIResource.Success -> {
                            showDialog(false)
                            showResult(response)
                        }
                        is APIResource.Failure -> {
                            showDialog(false)
                            showError()
                        }
                    }
                })
        }
    }

    private fun showResult(response: APIResource<out Response<SentimentalAnalysisReport>>?) {
        val polarity = response?.data!!.body()?.polarity
        report.text = polarity
    }

    private fun setDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading_layout)
    }

    private fun showError() {
        showMessage("API Called Failure")
    }


    private fun showDialog(value: Boolean) {
        if (value)
            dialog.show()
        else
            dialog.dismiss()
    }

    private fun setViewModel() {
        sentimentalViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(MainRepo())
        ).get(SentimentalViewModel::class.java)
    }
}