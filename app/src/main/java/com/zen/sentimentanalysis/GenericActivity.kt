package com.zen.sentimentanalysis

import android.app.Dialog
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zen.sentimentanalysis.listeners.GenericListener
import com.zen.sentimentanalysis.models.SentimentalAnalysisReport
import com.zen.sentimentanalysis.repo.MainRepo
import com.zen.sentimentanalysis.utils.APIResource
import com.zen.sentimentanalysis.viewmodel.SentimentalViewModel
import com.zen.sentimentanalysis.viewmodel.ViewModelFactory
import java.util.*

open class GenericActivity : AppCompatActivity() {
    lateinit var sentimentalViewModel: SentimentalViewModel
    lateinit var dialog: Dialog
    lateinit var bottomDialog: BottomSheetDialog
    lateinit var generatedReportModel: SentimentalAnalysisReport

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun initReportBottomDialog() {
        bottomDialog = BottomSheetDialog(this)
        bottomDialog.setContentView(R.layout.bottom_layout)
    }

    fun initAnalyzeDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.loading_layout)
        dialog.setCanceledOnTouchOutside(false)
    }

    fun initViewModel() {
        sentimentalViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(MainRepo())
        ).get(SentimentalViewModel::class.java)
    }

    fun getAnalyzedReport(selectedText: String, listener: GenericListener) {
        sentimentalViewModel.getAnalysis(selectedText)
            .observe(this, { response ->
                when (response) {
                    is APIResource.Loading -> {
                        listener.showDialog(true)
                    }
                    is APIResource.Success -> {
                        listener.showDialog(false)
                        listener.showResult(response)
                        generatedReportModel = response.data!!.body()!!
                    }
                    is APIResource.Failure -> {
                        listener.showDialog(false)
                        showMessage("Failed to Analyze")
                    }
                }
            })
    }


    fun setPieChart(value: Float, chart: PieChart) {
        val valueOne: Float = value
        val valueTwo: Float = "1.0".toFloat() - valueOne
        val colors = ArrayList<Int>()
        val entries: List<PieEntry> = listOf(
            PieEntry(valueOne, ""),
            PieEntry(valueTwo, ""),
        )

        colors.add(Color.rgb(23, 193, 243))
        colors.add(Color.rgb(255, 255, 255))

        val dataSet = PieDataSet(entries, "Generated Report")

        dataSet.colors = colors
        dataSet.sliceSpace = 2f
        dataSet.selectionShift = 5f
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.4f
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)
        chart.data = data

        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
                Log.e("TAG", "onNothingSelected: ")
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Log.e("TAG", "onValueSelected: ${e.toString()}")
            }

        })

        chart.highlightValues(null)
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f
        chart.setExtraOffsets(20f, 0f, 20f, 0f)
        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)
        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)
        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f
        chart.setDrawCenterText(true)
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.animateY(1400, Easing.EaseInOutQuad)


        val l: Legend = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false
    }
}